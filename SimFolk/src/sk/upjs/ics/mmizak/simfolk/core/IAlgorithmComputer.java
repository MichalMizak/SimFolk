package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public interface IAlgorithmComputer {


    /**
     * Quickly runs the algorithm on one song without affecting the database.
     * Note that without the reset, the weights are not 100% precise.
     * @param algorithmConfiguration
     * @param song
     * @return
     * @throws Exception
     */
    VectorAlgorithmResult computeSimilarity(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception;

    List<VectorAlgorithmResult> computeSimilarity(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception;

    /**
     * Saves and runs the deeper version of the algorithm on one song
     * @param algorithmConfiguration
     * @param song
     * @return
     * @throws Exception
     */
    VectorAlgorithmResult computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception;

    List<VectorAlgorithmResult> computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception;
}
