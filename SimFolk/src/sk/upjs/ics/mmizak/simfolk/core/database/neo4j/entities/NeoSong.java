package sk.upjs.ics.mmizak.simfolk.core.database.neo4j.entities;

import org.neo4j.ogm.annotation.*;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.List;

@NodeEntity
public class NeoSong {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private Long songId;

    @Property(name = "xmlPath")
    private String musicXMLFileTitle;

    @Property(name = "name")
    private String title;

    @Relationship(type = "IS_SIMILAR", direction = Relationship.UNDIRECTED)
    private List<NeoSong> similarity;

    public NeoSong(Long songId, String musicXMLFileTitle, String title, List<NeoSong> similarity) {
        this.songId = songId;
        this.musicXMLFileTitle = musicXMLFileTitle;
        this.title = title;
        this.similarity = similarity;
    }

    public NeoSong(Long songId, String musicXMLFileTitle, String title) {
        this.songId = songId;
        this.musicXMLFileTitle = musicXMLFileTitle;
        this.title = title;
    }

    public NeoSong(MelodySong melodySongA) {
        this(melodySongA.getId(), melodySongA.getMusicXML().toString(), melodySongA.getTitle());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMusicXMLFileTitle() {
        return musicXMLFileTitle;
    }

    public void setMusicXMLFileTitle(String musicXMLFileTitle) {
        this.musicXMLFileTitle = musicXMLFileTitle;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public List<NeoSong> getSimilarity() {
        return similarity;
    }

    public void setSimilarity(List<NeoSong> similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "NeoSong{" +
                "id=" + id +
                ", songId=" + songId +
                ", musicXMLFileTitle='" + musicXMLFileTitle + '\'' +
                ", similarity=" + similarity +
                '}';
    }
}
