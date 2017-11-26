package sk.upjs.ics.mmizak.simfolk.core.services;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;

import java.util.List;

public interface IConfigurationService {
    AlgorithmConfiguration generateRandomConfiguration();
    List<AlgorithmConfiguration> loadAllConfigurations();
}
