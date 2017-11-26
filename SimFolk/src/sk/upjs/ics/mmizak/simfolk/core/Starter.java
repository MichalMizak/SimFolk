package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmRunner;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.DummyVectorConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Starter {

    public static void main(String[] args) throws Exception {
        /*IAlgorithmRunner algorithmRunner = new VectorAlgorithmRunner();

        DummyVectorConfigurationService dummyVectorConfigurationGenerator = new DummyVectorConfigurationService();

        AlgorithmConfiguration vectorAlgorithmConfiguration =
                dummyVectorConfigurationGenerator.generateRandomConfiguration();*/


        // algorithmRunner.computeSimilarityAndSave(vectorAlgorithmConfiguration, null);


        List<Integer> fotky = Arrays.asList(3531, 3533, 3548, 3558, 3563,
                3566, 3576, 3580, 3590, 3601,
                3606, 3631, 3635, 3636,
                3527, 3538, 3543, 3551, 3579,
                3586, 3598, 3600, 3621, 3542, 3581,
                3603, 3607, 3610, 3611, 3623, 3627);

        Collections.sort(fotky);

        List<Integer> fotkyOtaznik = Arrays.asList(3562, 3569, 3570, 3571, 3574, 3597, 3587, 3632);

        Collections.sort(fotkyOtaznik);


        System.out.println(fotky.size());
        System.out.println(fotky);
        System.out.println(fotkyOtaznik);
    }
}
