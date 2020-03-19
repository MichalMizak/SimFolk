package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.IVectorAlgorithmConfigurationService;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders.VectorAlgorithmConfigurationBuilder;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

public class DummyVectorAlgorithmConfigurationService implements IVectorAlgorithmConfigurationService {

    @Override
    public VectorAlgorithmConfiguration generateRandomConfiguration() {
        return new VectorAlgorithmConfigurationBuilder()
                .setId(1L)
                .setTermScheme(TermScheme.MEASURE_NGRAM)
                .setTermDimension(4)
                .setTermWeightType(new TermWeightType(null, TF.AUGMENTED_TF, IDF.IDF))
                .setTermComparisonAlgorithm(TermComparisonAlgorithm.LEVENSHTEIN_DISTANCE)
                .setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ONE)
                .setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY)
                .setVectorInclusion(VectorInclusion.UNIFICATION)
                .setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS)
                .setTolerance(Tolerance.LOW)
                .setMusicStringFormat(MusicStringFormat.ABSOLUTE)
                .createVectorAlgorithmConfiguration();
    }

    @Override
    public List<VectorAlgorithmConfiguration> loadAllConfigurations() {

        List<VectorAlgorithmConfiguration> result = new ArrayList<>();

        result.add(new VectorAlgorithmConfigurationBuilder()
                .setId(null).setTermScheme(TermScheme.UNGRAM)
                .setTermDimension(null)
                .setTermWeightType(new TermWeightType(null, TF.AUGMENTED_TF, IDF.NONE))
                .setTermComparisonAlgorithm(TermComparisonAlgorithm.NAIVE)
                .setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ONE)
                .setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY)
                .setVectorInclusion(VectorInclusion.INTERSECTION)
                .setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS)
                .setTolerance(Tolerance.HIGH)
                .createVectorAlgorithmConfiguration());

        result.add(new VectorAlgorithmConfigurationBuilder()
                .setId(null).setTermScheme(TermScheme.TRIGRAM)
                .setTermDimension(null)
                .setTermWeightType(new TermWeightType(null, TF.TF_NAIVE, IDF.IDF))
                .setTermComparisonAlgorithm(TermComparisonAlgorithm.NAIVE)
                .setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ONE)
                .setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY)
                .setVectorInclusion(VectorInclusion.INTERSECTION)
                .setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS)
                .setTolerance(Tolerance.NONE)
                .createVectorAlgorithmConfiguration());

        result.add(new VectorAlgorithmConfigurationBuilder()
                .setId(null)
                .setTermScheme(TermScheme.BIGRAM)
                .setTermDimension(null)
                .setTermWeightType(new TermWeightType(null, TF.LOG_TF, IDF.IDF))
                .setTermComparisonAlgorithm(TermComparisonAlgorithm.NAIVE)
                .setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ONE)
                .setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY)
                .setVectorInclusion(VectorInclusion.A)
                .setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS)
                .setTolerance(Tolerance.MEDIUM)
                .createVectorAlgorithmConfiguration());

        return result;
    }
}