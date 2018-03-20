package sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.List;

public class WeightedTermGroupDao implements IWeightedTermGroupDao {


    @Override
    public List<WeightedTermGroup> syncGroupIds(List<Term> terms, AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return null;
    }

    @Override
    public WeightedTermGroup syncGroupId(Term term, AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return null;
    }

    @Override
    public void saveOrEdit(WeightedTermGroup weightedTermGroup) {

    }

    @Override
    public void delete(WeightedTermGroup weightedTermGroup) {

    }
}
