package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

public class VectorSong {

    private Long songId;

    private WeightedVector termVector;

    public VectorSong(WeightedVector termVector) {
        this.songId = termVector.getSongId();
        this.termVector =  termVector;
    }

    public Long getSongId() {
        return songId;
    }

    public WeightedVector getTermVector() {
        return termVector;
    }

    @Override
    public String toString() {
        return "VectorSong{" +
                "songId=" + songId +
                ", termVector=" + termVector +
                '}';
    }
}
