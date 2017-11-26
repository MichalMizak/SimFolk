package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.utilities.WeightedTermAlphabeticalComparator;

import java.util.ArrayList;
import java.util.List;

public class WeightedTermVector {

    private List<WeightedTerm> vector;

    public WeightedTermVector(List<WeightedTerm> vector) {
        this.vector = vector;
    }

    public List<WeightedTerm> getVector() {
        return vector;
    }

    public List<Double> getWeightValueVector() {
        List<Double> result = new ArrayList<>();

        for (WeightedTerm term : vector) {
            result.add(term.getTermWeightValue());
        }

        return result;
    }

    public void sortAlphabetically() {
        vector.sort(new WeightedTermAlphabeticalComparator());
    }
}
