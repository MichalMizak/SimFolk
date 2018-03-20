package sk.upjs.ics.mmizak.simfolk.core.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

public interface IWeightedTermGroupDao {

    /*WeightedTermGroup getWeightedTermGroupById(Long termId, Long songId, TermWeightType termWeightType,
                                               TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);*/

    /*WeightedTermGroup getWeightedTermGroupById(List<Long> termIds, List<Long> songIds, TermWeightType termWeightType,
                                               TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);*/

    /**
     * If terms are already in database (have non-zero ids) and were previously
     * assigned to an equivalence group with a given term comparison algorithm and
     * a given tolerance, we assign them with the group id
     * @param terms
     * @param termComparisonAlgorithm
     * @param tolerance
     * @return
     */
    List<WeightedTermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm,
                                         double tolerance);

    WeightedTermGroup syncGroupId(Term term, TermComparisonAlgorithm termComparisonAlgorithm,
                                  double tolerance);

    void saveOrEdit(WeightedTermGroup weightedTermGroup);

    void delete(WeightedTermGroup weightedTermGroup);
}
