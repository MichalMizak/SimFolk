package sk.upjs.ics.mmizak.simfolk.algorithm.containers;

import java.util.List;

public class Song {

    private Long id;

    private String title;

    private String lyrics;

    private String songStyle;

    private List<String> attributes;

    private String region;

    private String source;

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

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
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

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", songStyle='" + songStyle + '\'' +
                ", attributes=" + attributes +
                ", region='" + region + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}

