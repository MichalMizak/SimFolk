package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.services.implementations.DummyVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;


/**
 * Basic starter of algorithm without UI.
 * Starter generates/ chooses the algorithm configurations, gets songs from somewhere
 * (for now directly from code) and runs the algorithm with or without saving
 * and handles the VectorAlgorithmResult
 */
public class AlgorithmStarter {


    public static void main(String[] args) throws Exception {

        DummyVectorAlgorithmConfigurationService dummyVectorConfigurationGenerator = new DummyVectorAlgorithmConfigurationService();

        AlgorithmConfiguration vectorAlgorithmConfiguration =
                dummyVectorConfigurationGenerator.generateRandomConfiguration();

        Song songToCompare = new Song();

        IAlgorithmComputer algorithmComputer = new VectorAlgorithmComputer();

        // TODO: For progress send an object to the algorithm computer

        VectorAlgorithmResult result = algorithmComputer.computeSimilarity(vectorAlgorithmConfiguration, songToCompare);

        // TODO: Handle result
    }
}
