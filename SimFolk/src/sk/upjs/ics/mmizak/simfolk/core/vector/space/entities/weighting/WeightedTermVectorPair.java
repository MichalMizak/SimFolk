package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

public class WeightedTermVectorPair {
    private WeightedTermVector a;
    private WeightedTermVector b;

    public WeightedTermVectorPair(WeightedTermVector a, WeightedTermVector b) {
        this.a = a;
        this.b = b;
    }

    public WeightedTermVector getA() {
        return a;
    }

    public void setA(WeightedTermVector a) {
        this.a = a;
    }

    public WeightedTermVector getB() {
        return b;
    }

    public void setB(WeightedTermVector b) {
        this.b = b;
    }
}
