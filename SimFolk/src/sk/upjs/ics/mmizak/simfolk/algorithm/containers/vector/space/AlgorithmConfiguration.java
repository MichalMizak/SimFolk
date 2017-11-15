package sk.upjs.ics.mmizak.simfolk.algorithm.containers.vector.space;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.term.schemes.Term;
import sk.upjs.ics.mmizak.simfolk.algorithm.term.weighting.TermWeight;

public class AlgorithmConfiguration {

    private Term.Scheme termScheme;
    private TermWeight.Type termWeightType;

    private double tolerance;
    private VectorComparationAlgorithm vectorComparationAlgorithm;
    private VectorInclusion vectorInclusion;

    public enum VectorInclusion {
        a, b, ab, all
    }

    public enum VectorComparationAlgorithm {
        cos
    }
}
