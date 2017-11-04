package sk.upjs.ics.mmizak.simfolk.containers;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public abstract class NGram {
    private String lyricsFragment;

    public NGram(String lyricsFragment) {
        this.lyricsFragment = lyricsFragment;
        if (!validateLyricsFragment()) {
            throw new RuntimeException("Illegal String format of lyric fragment");
        }
    }

    protected abstract boolean validateLyricsFragment();

    public String getLyricsFragment() {
        return lyricsFragment;
    }



}
