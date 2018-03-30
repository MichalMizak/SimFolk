package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.IToleranceCalculator;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.Tolerance;

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
