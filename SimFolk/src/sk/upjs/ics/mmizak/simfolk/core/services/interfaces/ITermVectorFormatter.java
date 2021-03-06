package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorInclusion;

/**
 * If
 * vector a = (a, b, c) with weights (1, 2, 3)
 * vector b = (b, a, d) with weights (2, 3, 4)
 * then
 * A formation: vector a = (a, b, c) (1, 2, 3)
 * vector b = (a, b, c) (3, 2, 0)
 * B formation: reverse a and b
 * INTERSECTION formation: vector a = (a, b, c, d) (1, 2, 3, 0)
 * vector b = (a, b, c, d) (2, 3, 0, 4)
 * ALL formation: take all terms from database and create huge sparse vectors
 */
public interface ITermVectorFormatter {

    WeightedVectorPair aFormation(WeightedVector a, WeightedVector b,
                                  ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVectorPair bFormation(WeightedVector a, WeightedVector b,
                                  ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVectorPair intersectionFormation(WeightedVector a, WeightedVector b,
                                             ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVectorPair allFormation(WeightedVector a, WeightedVector b,
                                    ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVectorPair formVectors(WeightedVector a, WeightedVector b,
                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                   ITermComparator termComparator, VectorInclusion vectorInclusion);

    WeightedVectorPair unificationFormation(WeightedVector a, WeightedVector b, ITermComparator termComparator, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVectorPair sortAndFormVectors(WeightedVector a, WeightedVector b,
                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                          ITermComparator termComparator, VectorInclusion vectorInclusion);

    WeightedVectorPair formVectors(WeightedVectorPair ab,
                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                   ITermComparator termComparator, VectorInclusion vectorInclusion);
}
