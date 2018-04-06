package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.WeightedTermGroupIdComparator;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.VectorInclusion;


/**
 * The class sorts the vectors alphabetically and formats them to match each other.
 * Warning: The class MODIFIES the vectors it receives!!!
 */
// TODO: Implement
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

                if (aGroup.getGroupId() != null && bGroup.getGroupId() != null) {
                    // the vectors are sorted in ascending order
                    if (aGroup.getGroupId() < bGroup.getGroupId()) {
                        break vectorBCycle;
                    }
                }

                if (compareGroupsById(aGroup, bGroup)) {
                    foundSimilarTerm = true;
                    bResult.add(bGroup);
                    usedIndices.add(i);
                    break vectorBCycle;
                }
            }

            if (!foundSimilarTerm) {
                // add dummy group with 0 weight
                Double dummyWeight = 0D;
                bResult.add(new WeightedTermGroup(b.getSongId(), a.getTermWeightType(), dummyWeight, aGroup.getGroupId(),
                        aGroup.getTermScheme(), aGroup.getTerms(),
                        termComparisonAlgorithm, tolerance, aGroup.getDatabaseIncidenceCount()
                ));
            }
            foundSimilarTerm = false;
        }

        WeightedVector bResultVector = new WeightedVector(b.getSongId(), bResult);

        return new WeightedVectorPair(a, bResultVector);
    }

    @Override
    public WeightedVectorPair bFormation(WeightedVector a, WeightedVector b,
                                         ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                         double tolerance) {
        return aFormation(b, a, termComparator, termComparisonAlgorithm, tolerance);
    }

    @Override
    public WeightedVectorPair intersectionFormation(WeightedVector a, WeightedVector b,
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

            case UNIFICATION:
                return unificationFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);

            case INTERSECTION:
                return intersectionFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);

            case ALL:
                return allFormation(a, b, termComparator, termComparisonAlgorithm, tolerance);

            default:
                throw new RuntimeException("Unimplemented vector inclusion");
        }
    }

    @Override
    public WeightedVectorPair unificationFormation(WeightedVector a, WeightedVector b, ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        throw new UnsupportedOperationException();
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


    /**
     * Assumes that if a group has null id it is unique in database
     * @param t1
     * @param t2
     * @return
     */
    private boolean compareGroupsById(WeightedTermGroup t1, WeightedTermGroup t2) {

        if (t1.getGroupId() == null || t2.getGroupId() == null) {
            return false;
        }

        return t1.getGroupId().equals(t2.getGroupId());
    }
}
