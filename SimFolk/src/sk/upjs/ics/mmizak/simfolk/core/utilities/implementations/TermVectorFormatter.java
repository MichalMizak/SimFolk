package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.WeightedTermGroupIdComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorInclusion;


/**
 * The class sorts the vectors alphabetically and formats them to match each other.
 * Warning: The class MODIFIES the vectors it receives!!!
 */
// TODO: Implement
// TODO: Group comparator
public class TermVectorFormatter implements ITermVectorFormatter {

    @Override
    public WeightedVectorPair aFormation(WeightedVector a, WeightedVector b,
                                         ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                         double tolerance) {
        sortByGroupId(a);
        sortByGroupId(b);

        List<WeightedTermGroup> bResult = new ArrayList<>();
        Set<Integer> usedIndices = new HashSet<>();
        boolean foundSimilarTerm = false;

        for (WeightedTermGroup aGroup : a.getVector()) {

            vectorBCycle:
            for (int i = 0; i < b.getVector().size(); i++) {
                if (usedIndices.contains(i)) {
                    continue;
                }

                WeightedTermGroup bGroup = b.getVector().get(i);

                if (aGroup.getGroupId() != null) {
                    // the vectors are sorted in ascending order
                    if (aGroup.getGroupId() < bGroup.getGroupId()) {
                        break vectorBCycle;
                    }
                }

                if (compareGroups(aGroup, bGroup, termComparator, termComparisonAlgorithm, tolerance)) {
                    foundSimilarTerm = true;
                    bResult.add(bGroup);
                    usedIndices.add(i);
                    break vectorBCycle;
                }
            }

            if (!foundSimilarTerm) {
                // add dummy group with 0 weight
                bResult.add(new WeightedTermGroup(b.getSongId(), aGroup.getGroupId(), aGroup.getTerms(),
                        aGroup.getDatabaseIncidenceCount(), 0, a.getTermWeightType()));
            }
            foundSimilarTerm = false;
        }

        WeightedVector bResultVector = new WeightedVector(bResult, b.getSongId());

        return new WeightedVectorPair(a, bResultVector);
    }

    @Override
    public WeightedVectorPair bFormation(WeightedVector a, WeightedVector b,
                                         ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                         double tolerance) {
        return aFormation(b, a, termComparator, termComparisonAlgorithm, tolerance);
    }

    @Override
    public WeightedVectorPair abFormation(WeightedVector a, WeightedVector b,
                                          ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                          double tolerance) {
        return null;
    }

    @Override
    public WeightedVectorPair allFormation(WeightedVector a, WeightedVector b,
                                           ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                           double tolerance) {
        return null;
    }

    @Override
    public WeightedVectorPair formVectors(WeightedVector a, WeightedVector b,
                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                          ITermComparator termComparator, VectorInclusion vectorInclusion) {

        switch (vectorInclusion) {
            case A:
                return aFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);
            case B:
                return bFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);
            case AB:
                return abFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);
            case ALL:
                return allFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);
            default:
                throw new RuntimeException("Unimplemented vector inclusion");
        }
    }

    @Override
    public WeightedVectorPair formVectors(WeightedVectorPair ab,
                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                          ITermComparator termComparator, VectorInclusion vectorInclusion) {
        return formVectors(ab.getA(), ab.getB(), termComparisonAlgorithm, tolerance, termComparator, vectorInclusion);
    }

    private void sortByGroupId(WeightedVector vector) {
        vector.getVector().sort(new WeightedTermGroupIdComparator());
    }

    private boolean compareGroups(WeightedTermGroup t1, WeightedTermGroup t2, ITermComparator termComparator,
                                  TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        if (t1.getGroupId() != null && t2.getGroupId() != null) {
            return t1.getGroupId().equals(t2.getGroupId());
        }

        for (int i = 0; i < t1.getTerms().size(); i++) {
            Term t1Term = t1.getTerms().get(i);

            for (int j = 0; j < t2.getTerms().size(); j++) {
                Term t2Term = t2.getTerms().get(j);

                if (termComparator.compare(t1Term, t2Term, tolerance, termComparisonAlgorithm)) {
                    return true;
                }
            }
        }
        return false;
    }
}
