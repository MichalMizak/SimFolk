package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public class SongBuilder {
    private Long id;
    private String title;
    private String lyrics;
    private String cleanLyrics;
    private String songStyle;
    private List<String> attributes;
    private String region;
    private String source;

    public SongBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public SongBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SongBuilder setLyrics(String lyrics) {
        this.lyrics = lyrics;
        return this;
    }

    public SongBuilder setCleanLyrics(String cleanLyrics) {
        this.cleanLyrics = cleanLyrics;
        return this;
    }

    public SongBuilder setSongStyle(String songStyle) {
        this.songStyle = songStyle;
        return this;
    }

    public SongBuilder setAttributes(List<String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public SongBuilder setRegion(String region) {
        this.region = region;
        return this;
    }

    public SongBuilder setSource(String source) {
        this.source = source;
        return this;
    }

    public Song createSong() {
        return new Song(id, title, lyrics, cleanLyrics, songStyle, attributes, region, source);
    }
}