package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

public class WeightedVectorPair {
    private WeightedVector a;
    private WeightedVector b;

    public WeightedVectorPair(WeightedVector a, WeightedVector b) {
        this.a = a;
        this.b = b;
    }

    public WeightedVector getA() {
        return a;
    }

    public void setA(WeightedVector a) {
        this.a = a;
    }

    public WeightedVector getB() {
        return b;
    }

    public void setB(WeightedVector b) {
        this.b = b;
    }
}
