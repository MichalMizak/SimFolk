package sk.upjs.ics.mmizak.simfolk.core.database.neo4j.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.SimpleMusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MelodyResultAggregatorService implements Runnable {

    private static final int LEAST_PERCENTAGE_SAVED = 50;

    private List<MusicAlgorithmResult> latestResults;
    private VectorAlgorithmConfiguration latestConfiguration;

    private MelodySimilarityService melodySimilarityService;

    private int configurationsProcessed = 0;
    private double totalConfigurationEvaluationValue = 0;

    private double[][] aggregatedResults;

    @Autowired
    public MelodyResultAggregatorService(MelodySimilarityService melodySimilarityService) {
        this.melodySimilarityService = melodySimilarityService;
    }

    public void addResult(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, List<MusicAlgorithmResult> musicAlgorithmResults) {
        latestResults = musicAlgorithmResults;
        latestConfiguration = vectorAlgorithmConfiguration;
    }

    private List<MusicAlgorithmResult> filterResults(List<MusicAlgorithmResult> musicAlgorithmResults) {
        // interate all results for one song
        musicAlgorithmResults.forEach(result ->
        {
            // iterate all inclusions of the result

            Map<AlgorithmConfiguration.VectorInclusion, Map<MelodySong, Double>> newMap = new HashMap<>();

            result.getInclusionToSongToSimilarityPercentage().forEach(((vectorInclusion, melodySongDoubleMap) -> {

                Map<MelodySong, Double> newMapForOneInclusion = new HashMap<>();
                for (Map.Entry<MelodySong, Double> entry : melodySongDoubleMap.entrySet()) {
                    if (matchesfilter(result, entry))
                        newMapForOneInclusion.put(entry.getKey(), entry.getValue());
                }

                newMap.put(vectorInclusion, newMapForOneInclusion);
            }));

            result.setInclusionToSongToSimilarityPercentage(newMap);
        });


        return musicAlgorithmResults;
    }

    private boolean matchesfilter(MusicAlgorithmResult result, Map.Entry<MelodySong, Double> songToSimilarityEntry) {
        return !songToSimilarityEntry.getKey().equals(result.getMelodySong()) && songToSimilarityEntry.getValue() > LEAST_PERCENTAGE_SAVED;
    }

    private void aggregateResult() {

        Map<AlgorithmConfiguration.VectorInclusion, List<SimpleMusicAlgorithmResult>> simpleResults = getSimpleResults(latestResults, latestConfiguration);

        simpleResults.forEach(((vectorInclusion, simpleMusicAlgorithmResults) -> {

            // evaluate configuration
            double configurationEvaluationValue = evaluateConfiguration(latestConfiguration, vectorInclusion);

            double oldTotalConfigurationEvaluationValue = totalConfigurationEvaluationValue;
            totalConfigurationEvaluationValue += configurationEvaluationValue;

            // if no valid configurations were calculated. This should not happen, but what if.
            if (totalConfigurationEvaluationValue == 0)
                return;

            // aggregate all results, not ony the filtered ones
            simpleMusicAlgorithmResults.forEach(result -> {
                Long idA = result.getMelodySong().getId();

                result.getSongToSimilarityPercentage().forEach((
                        (melodySong, similarity) -> {
                            int x = idA.intValue();
                            int y = melodySong.getId().intValue();

                            // TODO: careful for the song count

                            // for each present song pair re-evaluate the similarity percentage taking count of processed configs into account
                            double beforeAverage = (aggregatedResults[x][y] * oldTotalConfigurationEvaluationValue)
                                    // normalize the similarity percentages by the evaluation factor
                                    + (similarity * configurationEvaluationValue);

                            double newSimilarity;

                            newSimilarity = beforeAverage / (totalConfigurationEvaluationValue);

                            newSimilarity = ((int) (newSimilarity * 1000)) / 1000.0;

                            // 2D array for the percentage map, map value both ways
                            aggregatedResults[x][y] = newSimilarity;
                            aggregatedResults[y][x] = newSimilarity;
                        }));
            });

            melodySimilarityService.saveAggregatedResults(aggregatedResults);

        }));
//
//        for (Map.Entry<Long, Double> idToSimilarity : melodyToSimilarityPercentage.entrySet()) {
//            // write all non-zero results, null-safe equals for ID (self-similarity)
//            if (idToSimilarity.getValue() != 0 && Long.compare(idToSimilarity.getKey(), vectorA.getSongId()) != 0) {
//                trimmedMelodyToSimilarityPercentage.put(idToSimilarity.getKey(), idToSimilarity.getValue());
//            }
//        }
    }

    private double evaluateConfiguration(VectorAlgorithmConfiguration latestConfiguration, AlgorithmConfiguration.VectorInclusion vectorInclusion) {

        boolean isIntersection = vectorInclusion == AlgorithmConfiguration.VectorInclusion.INTERSECTION;
        boolean isMeasure = latestConfiguration.getTermScheme() == AlgorithmConfiguration.TermScheme.MEASURE;
        boolean isRhythm = latestConfiguration.getMusicStringFormat() == AlgorithmConfiguration.MusicStringFormat.RHYTHM;
        boolean isContour = latestConfiguration.getMusicStringFormat() == AlgorithmConfiguration.MusicStringFormat.CONTOUR;

        if ((isIntersection && isMeasure) || (isIntersection && isRhythm) || (isIntersection && isContour))
            return 0.25;

        if (isIntersection || (isMeasure && isRhythm) || (isMeasure && isContour)) // tieto boli hore
            return 0.5;

        if (isMeasure ) // || isRhythm || isContour
            return 0.75;

        else
            return 1;
    }

    @Override
    public void run() {
        //  allResults.put(latestConfiguration, latestResults);

        // all results are processed but only filtered results are saved
        List<MusicAlgorithmResult> filteredResults = filterResults(latestResults);

        Map<AlgorithmConfiguration.VectorInclusion, List<SimpleMusicAlgorithmResult>> simpleResultMap =
                getSimpleResults(filteredResults, latestConfiguration);

        simpleResultMap.forEach(((vectorInclusion, simpleMusicAlgorithmResults) ->
                melodySimilarityService.save(latestConfiguration, simpleMusicAlgorithmResults, vectorInclusion)));

        aggregateResult();

        configurationsProcessed++;

        System.out.println("Processed configurations: " + configurationsProcessed);

        //        filteredResults.forEach(result -> {
//            if (!result.getSongToSimilarityPercentage().isEmpty()) {
//                System.out.println("Melody:" + result.getMelodySong().getId());
//                System.out.println(result.getSongToSimilarityPercentage().toString());
//            }
//        });
    }

    private Map<AlgorithmConfiguration.VectorInclusion, List<SimpleMusicAlgorithmResult>> getSimpleResults(List<MusicAlgorithmResult> inclusionResults,
                                                                                                           VectorAlgorithmConfiguration vectorConfig) {

        Map<AlgorithmConfiguration.VectorInclusion, List<SimpleMusicAlgorithmResult>> simpleResults = new HashMap<>();

        // init all simple lists
        for (AlgorithmConfiguration.VectorInclusion vectorInclusion : vectorConfig.getVectorInclusion()) {
            simpleResults.put(vectorInclusion, new ArrayList<>());
        }

        inclusionResults.forEach(inclusionResult -> {
            inclusionResult.getInclusionToSongToSimilarityPercentage().forEach(((vectorInclusion, melodySongDoubleMap) -> {
                simpleResults.get(vectorInclusion).add(new SimpleMusicAlgorithmResult(inclusionResult, melodySongDoubleMap));
            }));
        });

        return simpleResults;
    }

    public void init(List<MelodySong> melodySongs) {
        melodySimilarityService.init(melodySongs);
        // the index 0 is not used
        aggregatedResults = new double[melodySongs.size() + 1][melodySongs.size() + 1];
    }
}
