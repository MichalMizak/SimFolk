package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.TermGroupIdComparator;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.*;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorInclusion;


/**
 * The class formats two vectors to match each other.
 * Note: The vectors might be sorted or otherwise modified in the process.
 */
public class TermVectorFormatter implements ITermVectorFormatter {

    @Override
    public WeightedVectorPair sortAndFormVectors(WeightedVector a, WeightedVector b,
                                                 TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                                 ITermComparator termComparator, VectorInclusion vectorInclusion) {
        // Sort arrays by group id
        a.sort();
        b.sort();
        return formVectors(a, b, termComparisonAlgorithm, tolerance, termComparator, vectorInclusion);
    }

    @Override
    public WeightedVectorPair formVectors(WeightedVectorPair ab,
                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                          ITermComparator termComparator, VectorInclusion vectorInclusion) {
        return formVectors(ab.getA(), ab.getB(), termComparisonAlgorithm, tolerance, termComparator, vectorInclusion);
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

            default:
                throw new RuntimeException("Unimplemented vector inclusion");
        }
    }

    @Override
    public WeightedVectorPair aFormation(WeightedVector a, WeightedVector b,
                                         ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                         double tolerance) {
        // we do not create aResult because a vector remains unchanged in the process
        List<WeightedTermGroup> bResult = new ArrayList<>();

        // Number of elements processed from the respective List
        // and index of next element to be processed
        int aIndex = 0;
        int bIndex = 0;

        int aVectorSize = a.getVector().size();
        int bVectorSize = b.getVector().size();

        // if aIndex is incremented an element must always be added
        // to bResult in order for the Lists to match by size
        while (aIndex < aVectorSize && bIndex < bVectorSize) {

            WeightedTermGroup aGroup = a.getVector().get(aIndex);
            WeightedTermGroup bGroup = b.getVector().get(bIndex);

            // Handle null states
            Long aGroupId = aGroup.getGroupId();
            Long bGroupId = bGroup.getGroupId();

            if (aGroupId == null) {
                bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
                aIndex++;
                continue;
            }

            if (bGroupId == null) {
                bIndex++;
                continue;
            }

            /* Check if current element group id of first
             array is smaller than current element group id
             of second array. If yes, store first
             array element, increment first array
             index and add dummy element to second array.
             Do the same again but reversed,
             otherwise the group ids must be equal so add both to the result */

            if (aGroupId < bGroupId) {
                bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
                aIndex++;
                continue;
            }
            if (aGroupId > bGroupId) {
                bIndex++;
                continue;
            }

            // else they must be equal
            bResult.add(bGroup);
            aIndex++;
            bIndex++;
        }

        // Store remaining elements of first array
        while (aIndex < aVectorSize) {
            WeightedTermGroup aGroup = a.getVector().get(aIndex);
            bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
            aIndex++;
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
        List<WeightedTermGroup> aResult = new ArrayList<>();
        List<WeightedTermGroup> bResult = new ArrayList<>();

        // Number of elements processed from the respective List
        // and index of next element to be processed
        int aIndex = 0;
        int bIndex = 0;

        int aVectorSize = a.getVector().size();
        int bVectorSize = b.getVector().size();

        while (aIndex < aVectorSize && bIndex < bVectorSize) {

            WeightedTermGroup aGroup = a.getVector().get(aIndex);
            WeightedTermGroup bGroup = b.getVector().get(bIndex);

            // Handle null states
            Long aGroupId = aGroup.getGroupId();
            Long bGroupId = bGroup.getGroupId();

            if (aGroupId == null) {
                aIndex++;
                continue;
            }

            if (bGroupId == null) {
                bIndex++;
                continue;
            }

            /* Check if current element group id of first
             array is smaller than current element group id
             of second array. If yes, increment first array
             pointer.
             Do the same again but reversed,
             otherwise the group ids must be equal so add
             both to the result and increment pointers */

            if (aGroupId < bGroupId) {
                aIndex++;
                continue;
            }
            if (aGroupId > bGroupId) {
                bIndex++;
                continue;
            }

            // else they must be equal
            aResult.add(aGroup);
            bResult.add(bGroup);
            aIndex++;
            bIndex++;
        }

        WeightedVector aResultVector = new WeightedVector(a.getSongId(), aResult);
        WeightedVector bResultVector = new WeightedVector(b.getSongId(), bResult);

        return new WeightedVectorPair(aResultVector, bResultVector);
    }

    @Override
    public WeightedVectorPair allFormation(WeightedVector a, WeightedVector b,
                                           ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm,
                                           double tolerance) {
        // TODO: Implement
        throw new UnsupportedOperationException();
    }

    @Override
    public WeightedVectorPair unificationFormation(WeightedVector a, WeightedVector b, ITermComparator termComparator,
                                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        List<WeightedTermGroup> aResult = new ArrayList<>();
        List<WeightedTermGroup> bResult = new ArrayList<>();

        // Number of elements processed from the respective List
        // and index of next element to be processed
        int aIndex = 0;
        int bIndex = 0;

        int aVectorSize = a.getVector().size();
        int bVectorSize = b.getVector().size();

        while (aIndex < aVectorSize && bIndex < bVectorSize) {

            WeightedTermGroup aGroup = a.getVector().get(aIndex);
            WeightedTermGroup bGroup = b.getVector().get(bIndex);

            // Handle null states
            Long aGroupId = aGroup.getGroupId();
            Long bGroupId = bGroup.getGroupId();

            if (aGroupId == null) {
                aResult.add(aGroup);
                bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
                aIndex++;
                continue;
            }

            if (bGroupId == null) {
                aResult.add(getDummyGroup(b, a, termComparisonAlgorithm, tolerance, bGroup));
                bResult.add(bGroup);
                bIndex++;
                continue;
            }

            /* Check if current element group id of first
             array is smaller than current element group id
             of second array. If yes, store first
             array element, increment first array
             index and add dummy element to second array.
             Do the same again but reversed,
             otherwise the group ids must be equal so add both to the result */

            if (aGroupId < bGroupId) {
                aResult.add(aGroup);
                bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
                aIndex++;
                continue;
            }
            if (aGroupId > bGroupId) {
                aResult.add(getDummyGroup(b, a, termComparisonAlgorithm, tolerance, bGroup));
                bResult.add(bGroup);
                bIndex++;
                continue;
            }

            // else they must be equal
            aResult.add(aGroup);
            bResult.add(bGroup);
            aIndex++;
            bIndex++;
        }

        // Store remaining elements of first array
        while (aIndex < aVectorSize) {
            WeightedTermGroup aGroup = a.getVector().get(aIndex);
            aResult.add(aGroup);
            bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
            aIndex++;
        }

        // Store remaining elements of second array
        while (bIndex < bVectorSize) {
            WeightedTermGroup bGroup = b.getVector().get(bIndex);
            aResult.add(getDummyGroup(b, a, termComparisonAlgorithm, tolerance, bGroup));
            bResult.add(bGroup);
            bIndex++;
        }

        WeightedVector aResultVector = new WeightedVector(a.getSongId(), aResult);
        WeightedVector bResultVector = new WeightedVector(b.getSongId(), bResult);

        return new WeightedVectorPair(aResultVector, bResultVector);
    }

    /**
     * Create a dummy group with 0 weight
     *
     * @param a
     * @param b
     * @param termComparisonAlgorithm
     * @param tolerance
     * @param aGroup
     * @return
     */
    private WeightedTermGroup getDummyGroup(WeightedVector a, WeightedVector b, TermComparisonAlgorithm
            termComparisonAlgorithm, double tolerance, WeightedTermGroup aGroup) {
        Double dummyWeight = 0D;

        return new WeightedTermGroup(b.getSongId(), a.getTermWeightType(), dummyWeight, aGroup.getGroupId(),
                aGroup.getTermScheme(), aGroup.getTerms(),
                termComparisonAlgorithm, tolerance, aGroup.getDatabaseIncidenceCount()
        );
    }

    /**
     * Assumes that if a group has null id it is unique in database
     *
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

          /*List<WeightedTermGroup> bResult = new ArrayList<>();
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
                bResult.add(getDummyGroup(a, b, termComparisonAlgorithm, tolerance, aGroup));
            }
            foundSimilarTerm = false;
        }
*/
}
