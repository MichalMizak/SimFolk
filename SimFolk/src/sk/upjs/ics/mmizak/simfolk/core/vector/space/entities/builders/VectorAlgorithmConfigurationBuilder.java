package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class VectorAlgorithmConfigurationBuilder {
    private Long id;
    private Integer termDimension;
    private TermWeightType termWeightType;

    private TermScheme termScheme;
    private TermComparisonAlgorithm termComparisonAlgorithm;
    private TermGroupMatchingStrategy termGroupMatchingStrategy;
    private TermGroupMergingStrategy termGroupMergingStrategy;
    private List<VectorInclusion> vectorInclusion;
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

    public VectorAlgorithmConfigurationBuilder setVectorInclusion(List<VectorInclusion> vectorInclusion) {
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

    public VectorAlgorithmConfigurationBuilder getBuilderClone() {
        return new VectorAlgorithmConfigurationBuilder()
                .setId(id)
                .setTermScheme(termScheme)
                .setTermDimension(termDimension)
                .setTermWeightType(termWeightType)
                .setTermComparisonAlgorithm(termComparisonAlgorithm)
                .setTermGroupMatchingStrategy(termGroupMatchingStrategy)
                .setTermGroupMergingStrategy(termGroupMergingStrategy)
                .setVectorInclusion(vectorInclusion)
                .setVectorComparisonAlgorithm(vectorComparisonAlgorithm)
                .setTolerance(tolerance)
                .setMusicStringFormat(musicStringFormat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VectorAlgorithmConfigurationBuilder that = (VectorAlgorithmConfigurationBuilder) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (termDimension != null ? !termDimension.equals(that.termDimension) : that.termDimension != null)
            return false;
        if (termWeightType != null ? !termWeightType.equals(that.termWeightType) : that.termWeightType != null)
            return false;
        if (termScheme != that.termScheme) return false;
        if (termComparisonAlgorithm != that.termComparisonAlgorithm) return false;
        if (termGroupMatchingStrategy != that.termGroupMatchingStrategy) return false;
        if (termGroupMergingStrategy != that.termGroupMergingStrategy) return false;
        if (vectorInclusion != null ? !vectorInclusion.equals(that.vectorInclusion) : that.vectorInclusion != null)
            return false;
        if (vectorComparisonAlgorithm != that.vectorComparisonAlgorithm) return false;
        if (tolerance != that.tolerance) return false;
        return musicStringFormat == that.musicStringFormat;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (termDimension != null ? termDimension.hashCode() : 0);
        result = 31 * result + (termWeightType != null ? termWeightType.hashCode() : 0);
        result = 31 * result + (termScheme != null ? termScheme.hashCode() : 0);
        result = 31 * result + (termComparisonAlgorithm != null ? termComparisonAlgorithm.hashCode() : 0);
        result = 31 * result + (termGroupMatchingStrategy != null ? termGroupMatchingStrategy.hashCode() : 0);
        result = 31 * result + (termGroupMergingStrategy != null ? termGroupMergingStrategy.hashCode() : 0);
        result = 31 * result + (vectorInclusion != null ? vectorInclusion.hashCode() : 0);
        result = 31 * result + (vectorComparisonAlgorithm != null ? vectorComparisonAlgorithm.hashCode() : 0);
        result = 31 * result + (tolerance != null ? tolerance.hashCode() : 0);
        result = 31 * result + (musicStringFormat != null ? musicStringFormat.hashCode() : 0);
        return result;
    }
}