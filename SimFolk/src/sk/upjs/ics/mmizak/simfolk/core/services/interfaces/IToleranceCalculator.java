package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.Tolerance;

public interface IToleranceCalculator {

    Double calculateTolerance(Tolerance tolerance, TermComparisonAlgorithm termComparisonAlgorithm);
}
