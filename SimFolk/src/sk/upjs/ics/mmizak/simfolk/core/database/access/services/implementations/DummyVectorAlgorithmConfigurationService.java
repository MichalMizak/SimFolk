package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.IAlgorithmConfigurationService;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders.VectorAlgorithmConfigurationBuilder;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

public class DummyVectorAlgorithmConfigurationService implements IAlgorithmConfigurationService {

    @Override
    public AlgorithmConfiguration generateRandomConfiguration() {
        return new VectorAlgorithmConfigurationBuilder()
                .setId(1L)
                .setTermScheme(TermScheme.BIGRAM)
                .setTermDimension(null)
                .setTermWeightType(new TermWeightType(null, TF.TF_NAIVE, IDF.IDF))
                .setTermComparisonAlgorithm(TermComparisonAlgorithm.NAIVE)
                .setTermGroupMatchingStrategy(TermGroupMatchingStrategy.MATCH_ALL)
                .setTermGroupMergingStrategy(TermGroupMergingStrategy.MERGE_ANY)
                .setVectorInclusion(VectorInclusion.B)
                .setVectorComparisonAlgorithm(VectorComparisonAlgorithm.COS)
                .setTolerance(Tolerance.NONE)
                .createVectorAlgorithmConfiguration();
    }

    @Override
    public List<AlgorithmConfiguration> loadAllConfigurations() {

        List<AlgorithmConfiguration> result = new ArrayList<>();

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
