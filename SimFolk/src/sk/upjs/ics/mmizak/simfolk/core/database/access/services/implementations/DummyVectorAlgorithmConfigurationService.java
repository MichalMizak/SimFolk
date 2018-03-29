package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.IAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class DummyVectorAlgorithmConfigurationService implements IAlgorithmConfigurationService {

    @Override
    public AlgorithmConfiguration generateRandomConfiguration() {
        return new VectorAlgorithmConfiguration(1, TermScheme.UNGRAM,
                null,
                TermWeightType.TF_NAIVE,
                TermComparisonAlgorithm.NAIVE,
                VectorComparisonAlgorithm.COS,
                VectorInclusion.A,
                Tolerance.NONE);
    }

    @Override
    public List<AlgorithmConfiguration> loadAllConfigurations() {

        List<AlgorithmConfiguration> result = new ArrayList<>();

        result.add(new VectorAlgorithmConfiguration(null, TermScheme.UNGRAM,
                null,
                TermWeightType.TF_NAIVE,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.AB,
                Tolerance.HIGH));

        result.add(new VectorAlgorithmConfiguration(null, TermScheme.TRIGRAM,
                null,
                TermWeightType.TF,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.AB,
                Tolerance.NONE));

        result.add(new VectorAlgorithmConfiguration(null, TermScheme.BIGRAM,
                null,
                TermWeightType.TFIDF,
                TermComparisonAlgorithm.NAIVE, VectorComparisonAlgorithm.COS,
                VectorInclusion.A,
                Tolerance.MEDIUM));

        return result;
    }
}
