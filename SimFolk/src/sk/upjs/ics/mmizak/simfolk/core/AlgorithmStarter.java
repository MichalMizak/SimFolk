package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.database.access.DaoFactory;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ISongDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations.DummyVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ILyricCleaner;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations.LyricCleaner;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.parsing.Parser;

import java.util.List;


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


        Parser parser = new Parser();
        IAlgorithmComputer algorithmComputer = new VectorAlgorithmComputer();
        List<Song> viktor = parser.parseViktor();

        for (int i = 0; i < viktor.size()/2; i++) {

            Song songToCompare = viktor.get(i);

            // TODO: For progress send an object to the algorithm computer

            VectorAlgorithmResult result = algorithmComputer.computeSimilarityAndSave(vectorAlgorithmConfiguration, songToCompare);

            System.out.println(result.toString());
        }

        // TODO: Handle result
    }
}
