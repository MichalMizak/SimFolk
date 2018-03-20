package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;


/**
 * Takes two terms, takes the lyrics fragment string and determines whether they are equal or not
 * One for equal, 0 for unequal.
 */
public class TermComparator implements ITermComparator {

    @Override
    public boolean compare(Term t1, Term t2, double tolerance, TermComparisonAlgorithm termComparisonAlgorithm) {

        switch (termComparisonAlgorithm) {
            case NAIVE:
                return naiveTermComparison(t1, t2, tolerance);
        }

        return false;
    }

    private boolean naiveTermComparison(Term t1, Term t2, double tolerance) {
        return t1.getLyricsFragment().equals(t2.getLyricsFragment());
    }

}
