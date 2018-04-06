package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.List;
import java.util.Map;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermWeightType;

public interface IWeightService {

    /**
     * Resets all weights
     */
    void resetWeights();

    /**
     * Resets weights only for termWeightTypes
     *
     * @param termWeightTypes
     */
    void resetWeights(List<TermWeightType> termWeightTypes);

    /**
     * Resets weights only for termWeightType
     *
     * @param termWeightType
     */
    void resetWeights(TermWeightType termWeightType);

    WeightedTermGroup getWeightedTermById(Term term, TermWeightType termWeightType, Integer songId);

    WeightedTermGroup resetAndCalculateWeight(Term term, TermWeightType termWeightType, Integer songId);

    WeightedVector resetAndCalculateWeight(List<Term> terms, TermWeightType termWeightType, Integer songId);

    WeightedVector calculateNewWeightedVector(List<Term> terms, Long songId, VectorAlgorithmConfiguration vectorConfig,
                                              ITermComparator termComparator, double tolerance);

    WeightedVector getFittingWeightedTermVectorBySongId(Long songId, VectorAlgorithmConfiguration vectorConfig, double tolerance);

    /**
     * This method takes Tolerance, TermComparisonAlgorithm and TermScheme into account
     * @param vectorConfig
     * @param tolerance
     * @return
     */
    List<WeightedVector> getAllFittingWeightedVectors(VectorAlgorithmConfiguration vectorConfig, double tolerance);

    List<WeightedVector> getAllWeightedTermVectors(TermWeightType termWeightType,
                                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    void saveOrEdit(WeightedVector weightedVector);

    void delete(WeightedVector weightedVector);

    WeightedVector calculateNewWeightedVector(Long id, List<WeightedTermGroup> frequencyWeightedGroups, VectorAlgorithmConfiguration vectorConfig);
}
