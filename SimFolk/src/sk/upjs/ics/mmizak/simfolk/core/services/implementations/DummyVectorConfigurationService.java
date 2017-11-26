package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.services.IConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.List;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class DummyVectorConfigurationService implements IConfigurationService {

    @Override
    public AlgorithmConfiguration generateRandomConfiguration() {
        return new VectorAlgorithmConfiguration(TermScheme.UNGRAM,
                null,
                TermWeightType.TFIDF,
                VectorComparationAlgorithm.COS,
                VectorInclusion.AB,
                0);
    }

    @Override
    public List<AlgorithmConfiguration> loadAllConfigurations() {

        List<AlgorithmConfiguration> result = new ArrayList<>();

        result.add(new VectorAlgorithmConfiguration(TermScheme.UNGRAM,
                null,
                TermWeightType.TFIDF,
                VectorComparationAlgorithm.COS,
                VectorInclusion.AB,
                0));

        result.add(new VectorAlgorithmConfiguration(TermScheme.TRIGRAM,
                null,
                TermWeightType.TFIDF,
                VectorComparationAlgorithm.COS,
                VectorInclusion.AB,
                0));

        result.add(new VectorAlgorithmConfiguration(TermScheme.BIGRAM,
                null,
                TermWeightType.TFIDF,
                VectorComparationAlgorithm.COS,
                VectorInclusion.A,
                0.5));

        return result;
    }
}
