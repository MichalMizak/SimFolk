package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

public interface IWeightedVectorDao {

    List<WeightedVector> getAllWeightedVectors(TermWeightType termWeightType,
                                               TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVector getFittingWeightedVectorBySongId(Long songId, TermWeightType termWeightType,
                                             TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    void saveOrEdit(WeightedVector weightedVector);

    void delete(WeightedVector weightedVector);

    /**
     * Respects Tolerance, TermComparisonAlgorithm and TermScheme
     * @param vectorConfig
     * @param tolerance
     * @return
     */
    List<WeightedVector> getAllFittingWeightedVectors(VectorAlgorithmConfiguration vectorConfig, double tolerance);

    WeightedVector getFittingWeightedVectorBySongId(Long songId, VectorAlgorithmConfiguration vectorConfig, double tolerance);
}
