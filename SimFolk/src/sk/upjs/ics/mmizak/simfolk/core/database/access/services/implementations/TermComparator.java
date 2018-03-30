package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;


/**
 * Takes two terms, takes the lyrics fragment string and determines whether they are equal or not
 * One for equal, 0 for unequal.
 */
public class TermComparator implements ITermComparator {

    /**
     * Compares the ids of the terms, otherwise computes whether the terms are similar
     * enough to respect the given tolerance in the given termComparisonAlgorithm
     *
     * @param t1
     * @param t2
     * @param tolerance
     * @param termComparisonAlgorithm
     * @return
     */
    @Override
    public boolean compare(Term t1, Term t2, double tolerance, TermComparisonAlgorithm termComparisonAlgorithm) {

        if (t1.getId() != null && t1.getId().equals(t2.getId())) {
            return true;
        }

        switch (termComparisonAlgorithm) {
            case NAIVE:
                return naiveTermComparison(t1, t2);
            default:
                return false;
        }
    }

    public boolean compareGroups(TermGroup aTermGroup, TermGroup bTermGroup,
                                 VectorAlgorithmConfiguration vectorConfiguration,
                                 double tolerance) {

        switch (vectorConfiguration.getTermGroupMatchingStrategy()) {
            case MATCH_ONE:
                return matchOne(aTermGroup, bTermGroup, tolerance, vectorConfiguration.getTermComparisonAlgorithm());
            case MATCH_ALL:
                return matchAll(aTermGroup, bTermGroup, tolerance, vectorConfiguration.getTermComparisonAlgorithm());
            default:
                return false;
        }
    }

    private boolean matchAll(TermGroup aTermGroup, TermGroup bTermGroup,
                             double tolerance, TermComparisonAlgorithm termComparisonAlgorithm) {
        for (Term aTerm : aTermGroup.getTerms()) {
            for (Term bTerm : bTermGroup.getTerms()) {
                if (!compare(aTerm, bTerm, tolerance, termComparisonAlgorithm)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean matchOne(TermGroup aTermGroup, TermGroup bTermGroup,
                             double tolerance, TermComparisonAlgorithm termComparisonAlgorithm) {
        for (Term aTerm : aTermGroup.getTerms()) {
            for (Term bTerm : bTermGroup.getTerms()) {
                if (!compare(aTerm, bTerm, tolerance, termComparisonAlgorithm)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean naiveTermComparison(Term t1, Term t2) {
        return t1.getLyricsFragment().equals(t2.getLyricsFragment());
    }

}
