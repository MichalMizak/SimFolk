package sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVectorPair;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorComparationAlgorithm;

public interface IVectorComparator {

    double calculateSimilarity(VectorComparationAlgorithm algorithm, WeightedTermVectorPair vectorPair);

    double calculateSimilarity(VectorComparationAlgorithm algorithm, List<Double> A, List<Double> B);

    double calculateCosineSimilarity(List<Double> A, List<Double> B);

}
