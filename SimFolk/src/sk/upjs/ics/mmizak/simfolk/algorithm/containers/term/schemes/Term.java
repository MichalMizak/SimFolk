package sk.upjs.ics.mmizak.simfolk.algorithm.containers.term.schemes;

import java.util.List;

public class Term {

    private String lyricsFragment;
    private int wordCount;
    private List<String> tokenizedLyricsFragment;

    public Term(String lyricsFragment, List<String> tokenizedLyricsFragment) {
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


    public enum Scheme {
         NGram
    }
}
