package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorComparisonAlgorithm;

public interface IVectorComparator {

    double calculateSimilarity(VectorComparisonAlgorithm algorithm, WeightedVectorPair vectorPair);

    double calculateSimilarity(VectorComparisonAlgorithm algorithm, List<Double> A, List<Double> B);

    double calculateCosineSimilarity(List<Double> A, List<Double> B);

}
