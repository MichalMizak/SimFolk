package sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.Tolerance;

public interface IToleranceCalculator {

    double calculateTolerance(Tolerance tolerance, TermComparisonAlgorithm termComparisonAlgorithm);
}
