package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class VectorAlgorithmConfiguration extends AlgorithmConfiguration {

    private Integer id;

    private TermScheme termScheme;
    /**
     * In case term scheme is more complex
     */
    private Integer termDimension;

    private TermWeightType termWeightType;
    private VectorInclusion vectorInclusion;
    private TermComparisonAlgorithm termComparisonAlgorithm;
    private Tolerance tolerance;
    private VectorComparisonAlgorithm vectorComparisonAlgorithm;

    public VectorAlgorithmConfiguration(Integer id, TermScheme termScheme, Integer termDimension, TermWeightType termWeightType,
                                        TermComparisonAlgorithm termComparisonAlgorithm, VectorComparisonAlgorithm vectorComparisonAlgorithm,
                                        VectorInclusion vectorInclusion, Tolerance tolerance) {
        this.id = id;
        this.termScheme = termScheme;
        this.termDimension = termDimension;
        this.termWeightType = termWeightType;
        this.termComparisonAlgorithm = termComparisonAlgorithm;
        this.vectorComparisonAlgorithm = vectorComparisonAlgorithm;
        this.vectorInclusion = vectorInclusion;
        this.tolerance = tolerance;
    }

    //<editor-fold desc="Getters and setters">
    public TermScheme getTermScheme() {
        return termScheme;
    }

    public Integer getTermDimension() {
        return termDimension;
    }

    public TermWeightType getTermWeightType() {
        return termWeightType;
    }

    public VectorComparisonAlgorithm getVectorComparisonAlgorithm() {
        return vectorComparisonAlgorithm;
    }

    public VectorInclusion getVectorInclusion() {
        return vectorInclusion;
    }

    public Tolerance getTolerance() {
        return tolerance;
    }

    public TermComparisonAlgorithm getTermComparisonAlgorithm() {
        return termComparisonAlgorithm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    //</editor-fold>
}
