package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class VectorAlgorithmConfigurationBuilder {
    private Long id;
    private Integer termDimension;
    private TermWeightType termWeightType;

    private TermScheme termScheme;
    private TermComparisonAlgorithm termComparisonAlgorithm;
    private TermGroupMatchingStrategy termGroupMatchingStrategy;
    private TermGroupMergingStrategy termGroupMergingStrategy;
    private VectorInclusion vectorInclusion;
    private VectorComparisonAlgorithm vectorComparisonAlgorithm;
    private Tolerance tolerance;

    private MusicStringFormat musicStringFormat;


    public VectorAlgorithmConfigurationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermScheme(TermScheme termScheme) {
        this.termScheme = termScheme;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermDimension(Integer termDimension) {
        this.termDimension = termDimension;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermWeightType(TermWeightType termWeightType) {
        this.termWeightType = termWeightType;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermComparisonAlgorithm(TermComparisonAlgorithm termComparisonAlgorithm) {
        this.termComparisonAlgorithm = termComparisonAlgorithm;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermGroupMatchingStrategy(TermGroupMatchingStrategy termGroupMatchingStrategy) {
        this.termGroupMatchingStrategy = termGroupMatchingStrategy;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermGroupMergingStrategy(TermGroupMergingStrategy termGroupMergingStrategy) {
        this.termGroupMergingStrategy = termGroupMergingStrategy;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setVectorInclusion(VectorInclusion vectorInclusion) {
        this.vectorInclusion = vectorInclusion;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setVectorComparisonAlgorithm(VectorComparisonAlgorithm vectorComparisonAlgorithm) {
        this.vectorComparisonAlgorithm = vectorComparisonAlgorithm;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTolerance(Tolerance tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setMusicStringFormat(MusicStringFormat musicStringFormat) {
        this.musicStringFormat = musicStringFormat;
        return this;
    }

    public VectorAlgorithmConfiguration createVectorAlgorithmConfiguration() {
        return new VectorAlgorithmConfiguration(id, termScheme, termDimension, termWeightType, termComparisonAlgorithm,
                termGroupMatchingStrategy, termGroupMergingStrategy, vectorInclusion, vectorComparisonAlgorithm, tolerance, musicStringFormat);
    }
}