package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

import java.util.ArrayList;
import java.util.List;

/**
 * A vector of weighted term groups
 */
public class WeightedVector {

    private Integer songId;

    private List<WeightedTermGroup> vector;

    public WeightedVector(Integer songId, List<WeightedTermGroup> vector) {
        this.vector = vector;
        this.songId = songId;
    }

    public List<WeightedTermGroup> getVector() {
        return vector;
    }

    public List<Double> getWeightValueVector() {
        List<Double> result = new ArrayList<>();

        for (WeightedTermGroup term : vector) {
            result.add(term.getTermWeight());
        }

        return result;
    }

    public TermWeightType getTermWeightType() {
        if (vector.isEmpty()) {
            return null;
        }
        return vector.get(0).getTermWeightType();
    }

    public Integer getSongId() {
        return songId;
    }

    @Override
    public String toString() {
        return "WeightedVector{" +
                "songId=" + songId +
                ", vector=" + vector +
                '}';
    }
}
