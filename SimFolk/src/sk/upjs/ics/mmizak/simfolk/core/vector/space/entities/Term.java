package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class Term {

    private Integer id;

    private String lyricsFragment;
    private int wordCount;
    private List<String> tokenizedLyricsFragment;



    private TermScheme termScheme;

    public Term(Integer id, String lyricsFragment, List<String> tokenizedLyricsFragment, TermScheme termScheme) {
        this.id = id;
        this.lyricsFragment = lyricsFragment;
        this.wordCount = tokenizedLyricsFragment.size();
        this.tokenizedLyricsFragment = tokenizedLyricsFragment;
        this.termScheme = termScheme;
    }

    //<editor-fold desc="Getters and setters">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setTokenizedLyricsFragment(List<String> tokenizedLyricsFragment) {
        this.tokenizedLyricsFragment = tokenizedLyricsFragment;
        wordCount = tokenizedLyricsFragment.size();
    }

    public TermScheme getTermScheme() {
        return termScheme;
    }
    //</editor-fold>


    @Override
    public String   toString() {
        return "Term{" +
                "id=" + id +
                ", lyricsFragment='" + lyricsFragment + '\'' +
                ", wordCount=" + wordCount +
                ", tokenizedLyricsFragment=" + tokenizedLyricsFragment +
                ", termScheme=" + termScheme +
                '}';
    }
}
