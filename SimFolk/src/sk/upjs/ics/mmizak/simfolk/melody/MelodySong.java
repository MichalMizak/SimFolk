package sk.upjs.ics.mmizak.simfolk.melody;

import org.audiveris.proxymusic.ScorePartwise;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.io.File;
import java.util.List;

// TODO: extends Song
public class MelodySong {

    private Long id;

    private List<ScorePartwise.Part.Measure> melodyInMeasures;

    private File musicXML;

    public MelodySong(List<ScorePartwise.Part.Measure> melodyInMeasures, File musicXML) {
        this.melodyInMeasures = melodyInMeasures;
        this.musicXML = musicXML;
    }

    public File getMusicXML() {
        return musicXML;
    }

    public void setMusicXML(File musicXML) {
        this.musicXML = musicXML;
    }

    public List<ScorePartwise.Part.Measure> getMelodyInMeasures() {
        return melodyInMeasures;
    }

    public void setMelodyInMeasures(List<ScorePartwise.Part.Measure> melodyInMeasures) {
        this.melodyInMeasures = melodyInMeasures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
