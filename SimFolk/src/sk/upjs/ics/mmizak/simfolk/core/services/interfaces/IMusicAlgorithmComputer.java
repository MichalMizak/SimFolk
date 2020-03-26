package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.List;

public interface IMusicAlgorithmComputer {
    List<MusicAlgorithmResult> computeMusicSimilarity(VectorAlgorithmConfiguration vectorConfig, List<MelodySong> melodySongs);

    List<MusicAlgorithmResult> computeMusicSimilarityAndSave(VectorAlgorithmConfiguration vectorConfig, List<MelodySong> melodySongs);
}
