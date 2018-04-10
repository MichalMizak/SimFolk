package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

public interface IVectorAlgorithmConfigurationService {
    VectorAlgorithmConfiguration generateRandomConfiguration();
    List<VectorAlgorithmConfiguration> loadAllConfigurations();
}
