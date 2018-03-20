package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;

import java.util.List;

public interface IAlgorithmConfigurationService {
    AlgorithmConfiguration generateRandomConfiguration();
    List<AlgorithmConfiguration> loadAllConfigurations();
}
