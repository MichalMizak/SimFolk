package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

public class MusicAlgorithmResult extends LyricAlgorithmResult {

    private MelodySong melodySong;

    public MelodySong getMelodySong() {
        return melodySong;
    }

    public void setMelodySong(MelodySong melodySong) {
        this.melodySong = melodySong;
    }
}
