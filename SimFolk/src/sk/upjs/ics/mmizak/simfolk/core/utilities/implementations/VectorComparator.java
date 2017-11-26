package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.IVectorComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVectorPair;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class VectorComparator implements IVectorComparator {

    @Override
    public double calculateSimilarity(VectorComparationAlgorithm algorithm, WeightedTermVectorPair vectorPair) {
        return calculateSimilarity(algorithm,
                vectorPair.getA().getWeightValueVector(), vectorPair.getB().getWeightValueVector());
    }

    @Override
    public double calculateSimilarity(VectorComparationAlgorithm algorithm, List<Double> A, List<Double> B) {
        switch (algorithm) {
            case COS:
                return calculateCosineSimilarity(A, B);
            default:
                return 0;
        }
    }

    @Override
    public double calculateCosineSimilarity(List<Double> A, List<Double> B) {
        double[] vectorA = A.stream().mapToDouble(d -> d).toArray();
        double[] vectorB = B.stream().mapToDouble(d -> d).toArray();

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
