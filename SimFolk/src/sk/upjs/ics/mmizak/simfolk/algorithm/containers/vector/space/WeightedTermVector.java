package sk.upjs.ics.mmizak.simfolk.algorithm.containers.vector.space;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.WeightedTerm;

import java.util.List;

public class WeightedTermVector {

    private List<WeightedTerm> vector;

    public WeightedTermVector(List<WeightedTerm> vector) {
        this.vector = vector;
    }

    public List<WeightedTerm> getVector() {
        return vector;
    }
}
