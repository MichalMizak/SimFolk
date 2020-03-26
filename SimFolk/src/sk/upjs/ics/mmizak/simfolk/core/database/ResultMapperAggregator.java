package sk.upjs.ics.mmizak.simfolk.core.database;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultMapperAggregator {

    private Map<VectorAlgorithmConfiguration, List<MusicAlgorithmResult>> partialResults = new HashMap<>();

    public void addResult(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, List<MusicAlgorithmResult> musicAlgorithmResults) {
        List<MusicAlgorithmResult> filteredResults = filterResults(musicAlgorithmResults);
        partialResults.put(vectorAlgorithmConfiguration, filteredResults);

        System.out.println("Processed configurations: " + partialResults.size());

        System.out.println(vectorAlgorithmConfiguration);
        filteredResults.forEach(result -> {
            if (!result.getSongToSimilarityPercentage().isEmpty()) {
                System.out.println("Melody:" + result.getMelodySong().getId());
                System.out.println(result.getSongToSimilarityPercentage().toString());
            }
        });
//        partialResults.forEach((key, value) -> {
//                    System.out.println(key);
//                    value.forEach(result -> {
//                        if (!result.getSongToSimilarityPercentage().isEmpty())
//                            System.out.println(result.getSongToSimilarityPercentage().toString());
//                    }
//                }));
    }

    private List<MusicAlgorithmResult> filterResults(List<MusicAlgorithmResult> musicAlgorithmResults) {
        musicAlgorithmResults.forEach(result ->
        {
            Map<Long, Double> newMap = new HashMap<>();

            for (Map.Entry<Long, Double> entry : result.getSongToSimilarityPercentage().entrySet()) {
                if (matchesfilter(result, entry))
                    newMap.put(entry.getKey(), entry.getValue());
            }

            result.setSongToSimilarityPercentage(newMap);
        });


        return musicAlgorithmResults;
    }

    private boolean matchesfilter(MusicAlgorithmResult result, Map.Entry<Long, Double> longDoubleEntry) {
        return !longDoubleEntry.getKey().equals(result.getMelodySong().getId()) && longDoubleEntry.getValue() > 40;
    }

    public void aggregateResult() {
        // aggregateResult();

        Map<Long, Double> trimmedMelodyToSimilarityPercentage = new HashMap<>();
//
//        for (Map.Entry<Long, Double> idToSimilarity : melodyToSimilarityPercentage.entrySet()) {
//            // write all non-zero results, null-safe equals for ID (self-similarity)
//            if (idToSimilarity.getValue() != 0 && Long.compare(idToSimilarity.getKey(), vectorA.getSongId()) != 0) {
//                trimmedMelodyToSimilarityPercentage.put(idToSimilarity.getKey(), idToSimilarity.getValue());
//            }
//        }
    }
}
