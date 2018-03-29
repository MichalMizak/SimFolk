package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermScheme;

public interface ITermBuilder {

    List<Term> buildNGrams(String lyrics, int n);

    List<Term> buildUnGrams(String lyrics, TermScheme termScheme);

    List<Term> buildBiGrams(String lyrics, TermScheme termScheme);

    List<Term> buildTriGrams(String lyrics, TermScheme termScheme);

    List<Term> buildTerms(TermScheme termScheme, String lyrics);

    /**
     * Builds terms from lyrics according to the term scheme with a fixed
     * tokenized term count or a one that needs to be otherwise specified
     *
     * @param termScheme
     * @param n
     * @param lyrics
     * @return
     */
    List<Term> buildTerms(TermScheme termScheme, Integer n, String lyrics);
}
