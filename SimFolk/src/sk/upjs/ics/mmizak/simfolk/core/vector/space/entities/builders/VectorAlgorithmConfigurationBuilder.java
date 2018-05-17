package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

public class VectorAlgorithmConfigurationBuilder {
    private Long id;
    private AlgorithmConfiguration.TermScheme termScheme;
    private Integer termDimension;
    private TermWeightType termWeightType;
    private AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm;
    private AlgorithmConfiguration.TermGroupMatchingStrategy termGroupMatchingStrategy;
    private AlgorithmConfiguration.TermGroupMergingStrategy termGroupMergingStrategy;
    private AlgorithmConfiguration.VectorInclusion vectorInclusion;
    private AlgorithmConfiguration.VectorComparisonAlgorithm vectorComparisonAlgorithm;
    private AlgorithmConfiguration.Tolerance tolerance;

    public VectorAlgorithmConfigurationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermScheme(AlgorithmConfiguration.TermScheme termScheme) {
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

    public VectorAlgorithmConfigurationBuilder setTermComparisonAlgorithm(AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm) {
        this.termComparisonAlgorithm = termComparisonAlgorithm;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermGroupMatchingStrategy(AlgorithmConfiguration.TermGroupMatchingStrategy termGroupMatchingStrategy) {
        this.termGroupMatchingStrategy = termGroupMatchingStrategy;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTermGroupMergingStrategy(AlgorithmConfiguration.TermGroupMergingStrategy termGroupMergingStrategy) {
        this.termGroupMergingStrategy = termGroupMergingStrategy;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setVectorInclusion(AlgorithmConfiguration.VectorInclusion vectorInclusion) {
        this.vectorInclusion = vectorInclusion;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setVectorComparisonAlgorithm(AlgorithmConfiguration.VectorComparisonAlgorithm vectorComparisonAlgorithm) {
        this.vectorComparisonAlgorithm = vectorComparisonAlgorithm;
        return this;
    }

    public VectorAlgorithmConfigurationBuilder setTolerance(AlgorithmConfiguration.Tolerance tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    public VectorAlgorithmConfiguration createVectorAlgorithmConfiguration() {
        return new VectorAlgorithmConfiguration(id, termScheme, termDimension, termWeightType, termComparisonAlgorithm, termGroupMatchingStrategy, termGroupMergingStrategy, vectorInclusion, vectorComparisonAlgorithm, tolerance);
    }
}