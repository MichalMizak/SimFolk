package sk.upjs.ics.mmizak.simfolk.algorithm.containers;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.vector.space.WeightedTermVector;
import sk.upjs.ics.mmizak.simfolk.algorithm.utilities.LyricCleaner;
import sk.upjs.ics.mmizak.simfolk.algorithm.utilities.UtilityFactory;
import sk.upjs.ics.mmizak.simfolk.algorithm.utilities.interfaces.ITermBuilder;

public class SongStats {

    private long songId;

    private WeightedTermVector termVector;

    public SongStats(long songId, String lyrics, WeightedTermVector termVector) {
        this.songId = songId;

        lyrics = LyricCleaner.clean(lyrics);

        ITermBuilder termBuilder = UtilityFactory.getNGramBuilder();

        this.termVector =  termVector;

    }

    public long getSongId() {
        return songId;
    }

}
