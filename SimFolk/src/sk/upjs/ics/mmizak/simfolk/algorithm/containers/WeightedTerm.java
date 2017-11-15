package sk.upjs.ics.mmizak.simfolk.algorithm.containers;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.term.schemes.Term;
import sk.upjs.ics.mmizak.simfolk.algorithm.term.weighting.TermWeight;

public class WeightedTerm {

    private Term term;
    private TermWeight termWeight;

    public WeightedTerm(Term term, TermWeight termWeight) {
        this.term = term;
        this.termWeight = termWeight;
    }

    public Term getTerm() {
        return term;
    }

    public TermWeight getTermWeight() {
        return termWeight;
    }
}
