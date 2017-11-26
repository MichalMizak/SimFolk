package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

public class VectorAlgorithmConfiguration extends AlgorithmConfiguration {

    private TermScheme termScheme;
    /**
     * In case term scheme is more complex
     */
    private Integer termDimension;

    private TermWeightType termWeightType;
    private VectorComparationAlgorithm vectorComparationAlgorithm;
    private VectorInclusion vectorInclusion;

    private double tolerance;

    public VectorAlgorithmConfiguration(TermScheme termScheme, Integer termDimension, TermWeightType termWeightType, VectorComparationAlgorithm vectorComparationAlgorithm, VectorInclusion vectorInclusion, double tolerance) {
        this.termScheme = termScheme;
        this.termDimension = termDimension;
        this.termWeightType = termWeightType;
        this.vectorComparationAlgorithm = vectorComparationAlgorithm;
        this.vectorInclusion = vectorInclusion;
        this.tolerance = tolerance;
    }

    public TermScheme getTermScheme() {
        return termScheme;
    }

    public Integer getTermDimension() {
        return termDimension;
    }

    public TermWeightType getTermWeightType() {
        return termWeightType;
    }

    public VectorComparationAlgorithm getVectorComparationAlgorithm() {
        return vectorComparationAlgorithm;
    }

    public VectorInclusion getVectorInclusion() {
        return vectorInclusion;
    }

    public double getTolerance() {
        return tolerance;
    }
}
