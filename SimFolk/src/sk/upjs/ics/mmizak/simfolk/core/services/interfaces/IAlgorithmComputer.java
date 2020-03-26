package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.LyricAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.List;

public interface IAlgorithmComputer {


    /**
     * Quickly runs the algorithm on one song without affecting the database.
     * Note that without saving, the weights are not 100% precise.
     *
     * @param vectorConfig
     * @param song
     * @return
     * @throws Exception
     */
    LyricAlgorithmResult computeSimilarity(VectorAlgorithmConfiguration vectorConfig, Song song);

    List<LyricAlgorithmResult> computeSimilarity(VectorAlgorithmConfiguration vectorConfig, List<Song> songs);

    /**
     * Saves and runs the deeper version of the algorithm on one song
     *
     * @param vectorConfig
     * @param song
     * @return
     * @throws Exception
     */
    LyricAlgorithmResult computeSimilarityAndSave(VectorAlgorithmConfiguration vectorConfig, Song song);

    List<LyricAlgorithmResult> computeSimilarityAndSave(VectorAlgorithmConfiguration vectorConfig, List<Song> songs);
}
