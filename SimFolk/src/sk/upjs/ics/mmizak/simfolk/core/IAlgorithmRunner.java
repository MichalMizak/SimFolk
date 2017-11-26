package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public interface IAlgorithmRunner {

    AlgorithmResult computeSimilarity(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception;

    List<AlgorithmResult> computeSimilarity(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception;

    AlgorithmResult computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception;

    List<AlgorithmResult> computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception;
}
