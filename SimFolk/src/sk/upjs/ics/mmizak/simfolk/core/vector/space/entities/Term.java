package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities;

import java.util.List;

public class Term {

    private Long id;

    private String lyricsFragment;
    private int wordCount;
    private List<String> tokenizedLyricsFragment;

    public Term(Long id, String lyricsFragment, List<String> tokenizedLyricsFragment) {
        this.id = id;
        this.lyricsFragment = lyricsFragment;
        this.wordCount = tokenizedLyricsFragment.size();
        this.tokenizedLyricsFragment = tokenizedLyricsFragment;
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
}
