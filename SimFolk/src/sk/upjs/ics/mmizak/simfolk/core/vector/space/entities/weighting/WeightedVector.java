package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import java.util.ArrayList;
import java.util.List;

/**
 * A vector of weighted term groups
 */
public class WeightedVector {

    private Long songId;

    private List<WeightedTermGroup> vector;

    public WeightedVector(List<WeightedTermGroup> vector, Long songId) {
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

    public Long getSongId() {
        return songId;
    }
}
