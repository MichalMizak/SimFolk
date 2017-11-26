package sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

public class WeightedTerm {

    private Term term;
    private TermWeight termWeight;

    public WeightedTerm(Term term, TermWeight termWeight) {
        this.term = term;
        this.termWeight = termWeight;
    }

    // Term Weight methods
    public TermWeight getTermWeight() {
        return termWeight;
    }

    public double getTermWeightValue() { return termWeight.getWeight(); }


    // Term methods

    public Term getTerm() {
        return term;
    }

    public String getLyricsFragment() {
        return term.getLyricsFragment();
    }

    public int getWordCount() {
        return term.getWordCount();
    }

    public List<String> getTokenizedLyricsFragment() {
        return term.getTokenizedLyricsFragment();
    }



}
