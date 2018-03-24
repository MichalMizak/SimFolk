package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class Term {

    private Long id;

    private String lyricsFragment;
    private int wordCount;
    private List<String> tokenizedLyricsFragment;

    private TermScheme termScheme;

    public Term(Long id, String lyricsFragment, List<String> tokenizedLyricsFragment, TermScheme termScheme) {
        this.id = id;
        this.lyricsFragment = lyricsFragment;
        this.wordCount = tokenizedLyricsFragment.size();
        this.tokenizedLyricsFragment = tokenizedLyricsFragment;
        this.termScheme = termScheme;
    }

    //<editor-fold desc="Getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLyricsFragment() {
        return lyricsFragment;
    }

    public int getWordCount() {
        return wordCount;
    }

    public List<String> getTokenizedLyricsFragment() {
        return tokenizedLyricsFragment;
    }

    public TermScheme getTermScheme() {
        return termScheme;
    }
    //</editor-fold>
}
