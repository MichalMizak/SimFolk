package sk.upjs.ics.mmizak.simfolk.algorithm.utilities.interfaces;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.term.schemes.Term;

import java.util.List;

public interface ITermBuilder {

    List<Term> buildNGrams(String lyrics, int n);

    List<Term> buildUnGrams(String lyrics);

    List<Term> buildBiGrams(String lyrics);

    List<Term> buildTriGrams(String lyrics);

}
