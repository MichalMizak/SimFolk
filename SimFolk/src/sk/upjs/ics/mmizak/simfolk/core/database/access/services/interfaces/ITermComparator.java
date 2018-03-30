package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

public interface ITermComparator {

    // determines whether the terms are equal or similar within the tolerance
    boolean compare(Term t1, Term t2, double tolerance, TermComparisonAlgorithm termComparisonAlgorithm);

}
