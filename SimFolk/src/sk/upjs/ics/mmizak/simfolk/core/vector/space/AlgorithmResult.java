package sk.upjs.ics.mmizak.simfolk.core.vector.space;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Algorithm;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorSong;

import java.util.Map;

public class AlgorithmResult {

    private VectorSong vectorSong;

    // TODO: All
    private Map<Long, Double> songToSimilarityPercentage;

    private Algorithm algorithm;
}
