package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.WeightedTermAlphabeticalComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorInclusion;


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

        sortAlphabetically(a);
        sortAlphabetically(b);

        List<WeightedTermGroup> bResult = new ArrayList<>();


        for (WeightedTermGroup aTerm : a.getVector()) {
            // if (termComparator.compare(aTerm, ))
        }


        return null;
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

    private void sortAlphabetically(WeightedVector vector) {
        vector.getVector().sort(new WeightedTermAlphabeticalComparator());
    }
}
