package sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTerm;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVector;

import java.util.List;

public interface IWeightCalculator {

    void resetWeights();

    WeightedTerm calculateWeight(Term term, Long songId);

    WeightedTerm resetAndCalculateWeight(Term term, Long songId);

    WeightedTermVector calculateWeight(List<Term> terms, Long songId);

    WeightedTermVector resetAndCalculateWeight(List<Term> terms, Long songId);

}
