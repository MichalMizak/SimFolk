package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.IToleranceCalculator;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.Tolerance;

public class ToleranceCalculator implements IToleranceCalculator {

    @Override
    public double calculateTolerance(Tolerance tolerance, TermComparisonAlgorithm termComparisonAlgorithm) {
        switch (termComparisonAlgorithm) {
            case NAIVE:
                return 1;
            default:
                return 0;
        }
    }
}
