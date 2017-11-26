package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVector;
import sk.upjs.ics.mmizak.simfolk.core.utilities.LyricCleaner;
import sk.upjs.ics.mmizak.simfolk.core.utilities.UtilityFactory;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermBuilder;

public class VectorSong {

    private Long songId;

    private WeightedTermVector termVector;

    public VectorSong(long songId, WeightedTermVector termVector) {
        this.songId = songId;
        this.termVector =  termVector;
    }

    public Long getSongId() {
        return songId;
    }

    public WeightedTermVector getTermVector() {
        return termVector;
    }
}
