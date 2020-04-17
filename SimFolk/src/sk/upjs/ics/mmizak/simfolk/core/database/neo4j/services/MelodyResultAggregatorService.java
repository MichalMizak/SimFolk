package sk.upjs.ics.mmizak.simfolk.core.database.neo4j.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MelodyResultAggregatorService implements Runnable {

    private Map<VectorAlgorithmConfiguration, List<MusicAlgorithmResult>> allResults = new HashMap<>();

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
        musicAlgorithmResults.forEach(result ->
        {
            Map<MelodySong, Double> newMap = new HashMap<>();

            for (Map.Entry<MelodySong, Double> entry : result.getSongToSimilarityPercentage().entrySet()) {
                if (matchesfilter(result, entry))
                    newMap.put(entry.getKey(), entry.getValue());
            }

            result.setSongToSimilarityPercentage(newMap);
        });


        return musicAlgorithmResults;
    }

    private boolean matchesfilter(MusicAlgorithmResult result, Map.Entry<MelodySong, Double> songToSimilarityEntry) {
        return !songToSimilarityEntry.getKey().equals(result.getMelodySong()) && songToSimilarityEntry.getValue() > 40;
    }

    private void aggregateResult() {
        // evaluate configuration

        double configurationEvaluationValue = evaluateConfiguration(latestConfiguration);


        double oldTotalConfigurationEvaluationValue = totalConfigurationEvaluationValue;
        totalConfigurationEvaluationValue += configurationEvaluationValue;

        // if no valid configurations were calculated. This should not happen, but what if.
        if (totalConfigurationEvaluationValue == 0)
            return;

        latestResults.forEach(result -> {
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

                        // 2D array for the percentage map, map value both ways
                        aggregatedResults[x][y] = newSimilarity;
                        aggregatedResults[y][x] = newSimilarity;
                    }));
        });

        melodySimilarityService.saveAggregatedResults(aggregatedResults, totalConfigurationEvaluationValue, configurationsProcessed);
//
//        for (Map.Entry<Long, Double> idToSimilarity : melodyToSimilarityPercentage.entrySet()) {
//            // write all non-zero results, null-safe equals for ID (self-similarity)
//            if (idToSimilarity.getValue() != 0 && Long.compare(idToSimilarity.getKey(), vectorA.getSongId()) != 0) {
//                trimmedMelodyToSimilarityPercentage.put(idToSimilarity.getKey(), idToSimilarity.getValue());
//            }
//        }
    }

    private double evaluateConfiguration(VectorAlgorithmConfiguration latestConfiguration) {
        if (latestConfiguration.getVectorInclusion() == AlgorithmConfiguration.VectorInclusion.INTERSECTION &&
                latestConfiguration.getMusicStringFormat() == AlgorithmConfiguration.MusicStringFormat.RHYTHM)
            return 0.2;
        if (latestConfiguration.getTolerance() == AlgorithmConfiguration.Tolerance.HIGH
                || latestConfiguration.getVectorInclusion() == AlgorithmConfiguration.VectorInclusion.INTERSECTION)
            return 0.5;
        if (latestConfiguration.getTolerance() == AlgorithmConfiguration.Tolerance.MEDIUM
                || latestConfiguration.getMusicStringFormat() == AlgorithmConfiguration.MusicStringFormat.RHYTHM)
            return 0.8;
        if (latestConfiguration.getTolerance() == AlgorithmConfiguration.Tolerance.LOW)
            return 0.9;
        else
            return 1;
    }

    @Override
    public void run() {
        List<MusicAlgorithmResult> filteredResults = filterResults(latestResults);
        allResults.put(latestConfiguration, filteredResults);

        melodySimilarityService.save(latestConfiguration, filteredResults);

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

    public void init(List<MelodySong> melodySongs) {
        melodySimilarityService.init(melodySongs);
        // the index 0 is not used
        aggregatedResults = new double[melodySongs.size() + 1][melodySongs.size() + 1];
    }
}
