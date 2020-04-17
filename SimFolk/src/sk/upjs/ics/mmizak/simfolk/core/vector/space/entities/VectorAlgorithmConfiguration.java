package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import jdk.nashorn.internal.ir.annotations.Immutable;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

@Immutable
public class VectorAlgorithmConfiguration implements AlgorithmConfiguration {

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

    /**
     * NAIVE, LEVENSHTEIN_DISTANCE
     */
    private TermComparisonAlgorithm termComparisonAlgorithm;


    private MusicStringFormat musicStringFormat;


    /**
     * Not to be used, use VectorAlgorithmConfigurationBuilder
     *
     * @param id
     * @param termScheme
     * @param termDimension
     * @param termWeightType
     * @param termComparisonAlgorithm
     * @param termGroupMatchingStrategy
     * @param termGroupMergingStrategy
     * @param vectorInclusion
     * @param vectorComparisonAlgorithm
     * @param tolerance
     * @param musicStringFormat
     */
    public VectorAlgorithmConfiguration(Long id, TermScheme termScheme, Integer termDimension, TermWeightType termWeightType,
                                        TermComparisonAlgorithm termComparisonAlgorithm, TermGroupMatchingStrategy termGroupMatchingStrategy,
                                        TermGroupMergingStrategy termGroupMergingStrategy, VectorInclusion vectorInclusion, VectorComparisonAlgorithm vectorComparisonAlgorithm,
                                        Tolerance tolerance, MusicStringFormat musicStringFormat) {
        this.termScheme = termScheme;
        this.termDimension = termDimension;
        this.termComparisonAlgorithm = termComparisonAlgorithm;
        this.musicStringFormat = musicStringFormat;

        setId(id);
        setTermWeightType(termWeightType);
        setTermGroupMatchingStrategy(termGroupMatchingStrategy);
        setTermGroupMergingStrategy(termGroupMergingStrategy);
        setVectorComparisonAlgorithm(vectorComparisonAlgorithm);
        setVectorInclusion(vectorInclusion);
        setTolerance(tolerance);

    }

    //<editor-fold desc="Getters and Setters">
    public TermWeightType getTermWeightType() {
        return termWeightType;
    }

    protected void setTermWeightType(TermWeightType termWeightType) {
        this.termWeightType = termWeightType;
    }

    public Tolerance getTolerance() {
        return tolerance;
    }

    protected void setTolerance(Tolerance tolerance) {
        this.tolerance = tolerance;
    }

    public TermGroupMatchingStrategy getTermGroupMatchingStrategy() {
        return termGroupMatchingStrategy;
    }

    protected void setTermGroupMatchingStrategy(TermGroupMatchingStrategy termGroupMatchingStrategy) {
        this.termGroupMatchingStrategy = termGroupMatchingStrategy;
    }

    public TermGroupMergingStrategy getTermGroupMergingStrategy() {
        return termGroupMergingStrategy;
    }

    protected void setTermGroupMergingStrategy(TermGroupMergingStrategy termGroupMergingStrategy) {
        this.termGroupMergingStrategy = termGroupMergingStrategy;
    }

    public VectorInclusion getVectorInclusion() {
        return vectorInclusion;
    }

    protected void setVectorInclusion(VectorInclusion vectorInclusion) {
        this.vectorInclusion = vectorInclusion;
    }

    public VectorComparisonAlgorithm getVectorComparisonAlgorithm() {
        return vectorComparisonAlgorithm;
    }

    public void setVectorComparisonAlgorithm(VectorComparisonAlgorithm vectorComparisonAlgorithm) {
        this.vectorComparisonAlgorithm = vectorComparisonAlgorithm;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }
    //</editor-fold>

    //<editor-fold desc="Getters and setters">
    public TermScheme getTermScheme() {
        return termScheme;
    }

    public Integer getTermDimension() {
        return termDimension;
    }

    public TermComparisonAlgorithm getTermComparisonAlgorithm() {
        return termComparisonAlgorithm;
    }

    public MusicStringFormat getMusicStringFormat() {
        return musicStringFormat;
    }

    //</editor-fold>


    @Override
    public String toString() {
        return "VectorAlgorithmConfiguration{" +
                "musicStringFormat: " + musicStringFormat +
                ", termScheme: " + termScheme +
                ", termDimension: " + termDimension +
                ", termWeightType: " + termWeightType +
                "\n" +
                ", tolerance: " + tolerance +
                ", termGroupMatchingStrategy: " + termGroupMatchingStrategy +
                ", termGroupMergingStrategy: " + termGroupMergingStrategy +
                ", vectorInclusion: " + vectorInclusion +
                "\n" +
                ", vectorComparisonAlgorithm: " + vectorComparisonAlgorithm +
                ", termComparisonAlgorithm: " + termComparisonAlgorithm +
                '}';
    }
}
