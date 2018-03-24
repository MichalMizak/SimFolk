package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

public class VectorSong {

    private Integer songId;

    private WeightedVector termVector;

    public VectorSong(WeightedVector termVector) {
        this.songId = termVector.getSongId();
        this.termVector =  termVector;
    }

    public Integer getSongId() {
        return songId;
    }

    public WeightedVector getTermVector() {
        return termVector;
    }
}
