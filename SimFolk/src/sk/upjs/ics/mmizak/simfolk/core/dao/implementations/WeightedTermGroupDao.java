package sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class WeightedTermGroupDao implements IWeightedTermGroupDao {


    /**
     * The method has nothing to do with songId, weight, or termWeightType.
     * It simply returns a list of the same size as parameter terms containing
     * one-term WeightedTermGroups with initialized group id.
     * Group ids will vary depending on termComparisonAlgorithm and the tolerance we use.
     * @param terms
     * @param termComparisonAlgorithm
     * @param tolerance
     * @return
     */
    @Override
    public List<WeightedTermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm,
                                                double tolerance) {
        List<WeightedTermGroup> result = new ArrayList<>();

        if (termComparisonAlgorithm == TermComparisonAlgorithm.NAIVE) {

            result = terms.stream().map(term -> new WeightedTermGroup(null, term.getId(),
                    Collections.singletonList(term), 1L, 0,
                    null)).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public WeightedTermGroup syncGroupId(Term term, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        WeightedTermGroup result = null;

        if (termComparisonAlgorithm == TermComparisonAlgorithm.NAIVE) {

            result = new WeightedTermGroup(null, term.getId(), Collections.singletonList(term),
                    1L, 0, null);
        }

        return result;
    }

    @Override
    public void saveOrEdit(WeightedTermGroup weightedTermGroup) {

    }

    @Override
    public void delete(WeightedTermGroup weightedTermGroup) {

    }
}
