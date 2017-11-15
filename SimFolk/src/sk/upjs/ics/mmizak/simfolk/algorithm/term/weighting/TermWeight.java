package sk.upjs.ics.mmizak.simfolk.algorithm.term.weighting;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.term.schemes.Term;

import java.util.List;

public class TermWeight {

    private double weight;

    public TermWeight(Term term, List<Term> originSong, List<List<Term>> allSongs) {
        weight = calculateTermWeight(term, originSong, allSongs);
    }

    public TermWeight(double weight) {
        setWeight(weight);
    }

    // TODO: implement
    private double calculateTermWeight(Term term, List<Term> originSong, List<List<Term>> allSongs) {
        return 0;
    };

    public double getWeight() {
        return weight;
    }

    protected void setWeight(double weight) {
        this.weight = weight;
    }

    public enum Type {
        tf, idf, tfidf
    }
}
