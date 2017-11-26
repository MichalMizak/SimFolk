package sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

public interface ITermBuilder {

    List<Term> buildNGrams(String lyrics, int n);

    List<Term> buildUnGrams(String lyrics);

    List<Term> buildBiGrams(String lyrics);

    List<Term> buildTriGrams(String lyrics);

    List<Term> buildTerms(AlgorithmConfiguration.TermScheme termScheme, String lyrics);

    /**
        Pseudo-generic generation of term shemes with fixed
        tokenized term count or need to be otherwise specified by a number
     */
    List<Term> buildTerms(AlgorithmConfiguration.TermScheme termScheme, Integer n, String lyrics);
}
