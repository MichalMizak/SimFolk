package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import java.util.List;

public class Song {

    private Long id;

    private String title;

    private String cleanLyrics;

    private String lyrics;

    private String songStyle;

    private List<String> attributes;

    private String region;

    private String source;

    public Song() {
        // do nothing
    }

    public Song(Long id, String title, String lyrics, String cleanLyrics, String songStyle, List<String> attributes, String region, String source) {
        this.id = id;
        this.title = title;
        this.cleanLyrics = cleanLyrics;
        this.lyrics = lyrics;
        this.songStyle = songStyle;
        this.attributes = attributes;
        this.region = region;
        this.source = source;
    }

    //<editor-fold desc="Getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCleanLyrics() {
        return cleanLyrics;
    }

    public void setCleanLyrics(String cleanLyrics) {
        this.cleanLyrics = cleanLyrics;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getSongStyle() {
        return songStyle;
    }

    public void setSongStyle(String songStyle) {
        this.songStyle = songStyle;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cleanLyrics='" + cleanLyrics + '\'' +
                ", songStyle='" + songStyle + '\'' +
                ", attributes=" + attributes +
                ", region='" + region + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}

