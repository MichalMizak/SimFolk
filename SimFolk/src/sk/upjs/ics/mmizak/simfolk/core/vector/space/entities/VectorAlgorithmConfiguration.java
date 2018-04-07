package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import jdk.nashorn.internal.ir.annotations.Immutable;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

@Immutable
public class VectorAlgorithmConfiguration extends AlgorithmConfiguration {

    private Long id;

    /**
     * N-gram...
     */
    private TermScheme termScheme;
    private Integer termDimension;

    /**
     * LOG_TF, IDF, LOG_TF-IDF
     */
    private TermWeightType termWeightType;

    /**
     * NAIVE, LEVENSHTEIN_DISTANCE
     */
    private TermComparisonAlgorithm termComparisonAlgorithm;
    private Tolerance tolerance;

    private TermGroupMatchingStrategy termGroupMatchingStrategy;
    private TermGroupMergingStrategy termGroupMergingStrategy;

    /**
     * A, B, INTERSECTION, UNIFICATION, ALL
     */
    private VectorInclusion vectorInclusion;

    /**
     * SIMPLE_MATCHING, COS_COEFFICIENT, DICE'S_COEFICIENT, JACCARD'S_COEFFICIENT...
     */
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
