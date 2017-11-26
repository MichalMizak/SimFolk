package sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVectorPair;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorInclusion;

/**
 * vector a = (a, b, c) with weights (1, 2, 3)
 * vector b = (b, a, d) with weights (2, 3, 4)
 * A formation: vector a = (a, b, c) (1, 2, 3)
 *              vector b = (a, b, c) (3, 2, 0)
 * B formation: reverse a and b
 * AB formation: vector a = (a, b, c, d) (1, 2, 3, 0)
 *               vector b = (a, b, c, d) (2, 3, 0, 4)
 * ALL formation: take all terms from database and create huge sparse vectors
 */
public interface ITermVectorFormatter {

    WeightedTermVectorPair aFormation(WeightedTermVector a, WeightedTermVector b);
    WeightedTermVectorPair bFormation(WeightedTermVector a, WeightedTermVector b);
    WeightedTermVectorPair abFormation(WeightedTermVector a, WeightedTermVector b);
    WeightedTermVectorPair allFormation(WeightedTermVector a, WeightedTermVector b);

    WeightedTermVectorPair formVector(WeightedTermVector a, WeightedTermVector b,
                                  VectorInclusion vectorInclusion);

    WeightedTermVectorPair formVector(WeightedTermVectorPair ab, VectorInclusion vectorInclusion);
}
