package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

public interface IWeightedTermGroupDao {

    WeightedTermGroup getByIds(Integer groupId, Integer songId);

    List<WeightedTermGroup> getAll(TermWeightType termWeightType,
                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    List<WeightedTermGroup> getAll(Integer songId, TermWeightType termWeightType,
                                   TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedTermGroup saveOrEdit(WeightedTermGroup weightedTermGroup);

    WeightedTermGroup saveOrEditExcludingTermGroup(WeightedTermGroup weightedTermGroup);

    void delete(WeightedTermGroup weightedTermGroup);

    void deleteExcludingTermGroup(WeightedTermGroup weightedTermGroup);

    List<WeightedTermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    WeightedTermGroup syncGroupId(Term terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);
}
