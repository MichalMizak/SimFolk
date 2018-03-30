package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import jdk.nashorn.internal.ir.annotations.Immutable;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;

@Immutable
public class VectorAlgorithmConfiguration extends AlgorithmConfiguration {

    private Long id;

    private TermScheme termScheme;
    /**
     * In case term scheme is more complex
     */
    private Integer termDimension;

    private TermWeightType termWeightType;
    private VectorInclusion vectorInclusion;
    private TermComparisonAlgorithm termComparisonAlgorithm;
    private TermGroupMatchingStrategy termGroupMatchingStrategy;
    private TermGroupMergingStrategy termGroupMergingStrategy;
    private Tolerance tolerance;
    private VectorComparisonAlgorithm vectorComparisonAlgorithm;


    public VectorAlgorithmConfiguration(Long id, TermScheme termScheme, Integer termDimension, TermWeightType termWeightType,
                                        TermComparisonAlgorithm termComparisonAlgorithm, TermGroupMatchingStrategy termGroupMatchingStrategy,
                                        TermGroupMergingStrategy termGroupMergingStrategy, VectorInclusion vectorInclusion, VectorComparisonAlgorithm vectorComparisonAlgorithm,
                                        Tolerance tolerance) {
        this.id = id;
        this.termScheme = termScheme;
        this.termDimension = termDimension;
        this.termWeightType = termWeightType;
        this.termComparisonAlgorithm = termComparisonAlgorithm;
        this.termGroupMatchingStrategy = termGroupMatchingStrategy;
        this.termGroupMergingStrategy = termGroupMergingStrategy;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TermGroupMatchingStrategy getTermGroupMatchingStrategy() {
        return termGroupMatchingStrategy;
    }

    public TermGroupMergingStrategy getTermGroupMergingStrategy() {
        return termGroupMergingStrategy;
    }
    //</editor-fold>


}
