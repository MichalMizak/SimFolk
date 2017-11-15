package sk.upjs.ics.mmizak.simfolk.algorithm.containers.vector.space;

import java.util.Map;

public class AlgorithmResult {

    private Long songId;

    // TODO: Statistics
    private Map<Long, Double> songToSimilarityPercentage;

    private Algorithm algorithm;
}
