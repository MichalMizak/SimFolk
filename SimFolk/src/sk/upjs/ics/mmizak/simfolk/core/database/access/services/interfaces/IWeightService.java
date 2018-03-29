package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

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

    WeightedVector getWeightedTermVectorBySongId(Integer songId, TermWeightType termWeightType,
                                                 TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedVector calculateNewWeightedVector(List<Term> terms, TermWeightType termWeightType, Integer songId,
                                              TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                              ITermComparator termComparator);

    List<WeightedVector> getAllWeightedTermVectors(TermWeightType termWeightType,
                                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    void saveOrEdit(WeightedVector weightedVector);

    void delete(WeightedVector weightedVector);
}
