package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.IVectorComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class VectorComparator implements IVectorComparator {

    @Override
    public double calculateSimilarity(VectorComparisonAlgorithm algorithm, WeightedVectorPair vectorPair) {
        return calculateSimilarity(algorithm,
                vectorPair.getA().getWeightValueVector(), vectorPair.getB().getWeightValueVector());
    }

    @Override
    public double calculateSimilarity(VectorComparisonAlgorithm algorithm, List<Double> A, List<Double> B) {
        switch (algorithm) {
            case COS:
                return calculateCosineSimilarity(A, B);
            default:
                return 0;
        }
    }

    @Override
    public double calculateCosineSimilarity(List<Double> A, List<Double> B) {

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < A.size(); i++) {
            dotProduct += A.get(i) * B.get(i);
            normA += Math.pow(A.get(i), 2);
            normB += Math.pow(B.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
