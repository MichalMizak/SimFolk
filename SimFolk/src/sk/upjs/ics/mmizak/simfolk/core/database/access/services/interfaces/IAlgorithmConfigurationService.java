package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;

import java.util.List;

public interface IAlgorithmConfigurationService {
    AlgorithmConfiguration generateRandomConfiguration();
    List<AlgorithmConfiguration> loadAllConfigurations();
}
