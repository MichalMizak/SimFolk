package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.IAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class DummyVectorAlgorithmConfigurationService implements IAlgorithmConfigurationService {

    @Override
    public AlgorithmConfiguration generateRandomConfiguration() {
        return new VectorAlgorithmConfiguration(TermScheme.UNGRAM,
                null,
                TermWeightType.TFIDF,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.AB,
                Tolerance.NONE);
    }

    @Override
    public List<AlgorithmConfiguration> loadAllConfigurations() {

        List<AlgorithmConfiguration> result = new ArrayList<>();

        result.add(new VectorAlgorithmConfiguration(TermScheme.UNGRAM,
                null,
                TermWeightType.TF_NAIVE,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.AB,
                Tolerance.HIGH));

        result.add(new VectorAlgorithmConfiguration(TermScheme.TRIGRAM,
                null,
                TermWeightType.TF,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.AB,
                Tolerance.NONE));

        result.add(new VectorAlgorithmConfiguration(TermScheme.BIGRAM,
                null,
                TermWeightType.TFIDF,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.A,
                Tolerance.MEDIUM));

        return result;
    }
}
