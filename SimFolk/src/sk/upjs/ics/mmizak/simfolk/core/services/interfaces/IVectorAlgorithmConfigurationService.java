package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

public interface IVectorAlgorithmConfigurationService {
    VectorAlgorithmConfiguration generateRandomConfiguration();
    List<VectorAlgorithmConfiguration> loadAllConfigurations();
}
