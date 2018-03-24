package sk.upjs.ics.mmizak.simfolk.core.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

public interface IWeightedVectorDao {

    List<WeightedVector> getAllWeightedVectors(TermWeightType termWeightType,
                                               TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVector getWeightedVectorBySongId(Integer songId, TermWeightType termWeightType,
                                             TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    void saveOrEdit(WeightedVector weightedVector);

    void delete(WeightedVector weightedVector);

}
