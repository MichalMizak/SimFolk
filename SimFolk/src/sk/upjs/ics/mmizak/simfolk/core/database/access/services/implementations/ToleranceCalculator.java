package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.IToleranceCalculator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.Tolerance;

public class ToleranceCalculator implements IToleranceCalculator {

    @Override
    public Double calculateTolerance(Tolerance tolerance, TermComparisonAlgorithm termComparisonAlgorithm) {
        switch (termComparisonAlgorithm) {
            case NAIVE:
                return 1D;
            case LEVENSHTEIN_DISTANCE:
                return getLevenshteinTolerance(tolerance);
            default:
                return 0D;
        }
    }

    private Double getLevenshteinTolerance(Tolerance tolerance) {
        switch (tolerance) {
            case HIGH:
                return 0.6D;
            case MEDIUM:
                return 0.8D;
            case LOW:
                return 0.9D;
            case NONE:
                return 1D;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
