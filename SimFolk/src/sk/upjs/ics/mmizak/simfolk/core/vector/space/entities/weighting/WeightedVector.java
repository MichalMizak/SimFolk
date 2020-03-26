package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.utilities.TermGroupIdComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * A vector of weighted term groups
 */
public class WeightedVector {

    private Long songId;

    private List<WeightedTermGroup> vector;

    public WeightedVector(Long songId, List<WeightedTermGroup> vector) {
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

    public Long getSongId() {
        return songId;
    }

    public String pairAtIndexToString(int index) {
        WeightedTermGroup weightedTermGroup = vector.get(index);
        StringBuilder sb = new StringBuilder();

        sb.append("\\utrzok{");
        sb.append(weightedTermGroup.getTerms().get(0).getLyricsFragment());

        for (int i = 1; i < weightedTermGroup.getTerms().size(); i++) {
            Term term = weightedTermGroup.getTerms().get(i);
            sb.append(", " + term.getLyricsFragment());
        }

        sb.append("}");
        return sb.toString() + " & " + Math.round(vector.get(index).getTermWeight() * 100) / 100.0;
    }


    @Override
    public String toString() {
        return "WeightedVector{" +
                "songId=" + songId +
                ", vector=" + vector +
                '}';
    }

    /**
     * Sort arrays by group id
     */
    public void sort() {
        vector.sort(new TermGroupIdComparator());
    }
}
