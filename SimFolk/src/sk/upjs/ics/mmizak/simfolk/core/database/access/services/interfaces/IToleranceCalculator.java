package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.Tolerance;

public interface IToleranceCalculator {

    double calculateTolerance(Tolerance tolerance, TermComparisonAlgorithm termComparisonAlgorithm);
}
