package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.UtilityFactory;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTerm;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermVectorPair;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.VectorInclusion;

// TODO: TOLERANCE TEST
public class TermVectorFormatter implements ITermVectorFormatter {

    @Override
    public WeightedTermVectorPair aFormation(WeightedTermVector a, WeightedTermVector b) {

        a.sortAlphabetically();
        b.sortAlphabetically();

        List<WeightedTerm> bResult = new ArrayList<>();

        ITermComparator termComparator = UtilityFactory.INSTANCE.getTermComparator();



        for (WeightedTerm aTerm : a.getVector()) {
            // if (termComparator.equals(aTerm, ))
        }




        return null;
    }

    @Override
    public WeightedTermVectorPair bFormation(WeightedTermVector a, WeightedTermVector b) {
        return aFormation(b, a);
    }

    @Override
    public WeightedTermVectorPair abFormation(WeightedTermVector a, WeightedTermVector b) {
        return null;
    }

    @Override
    public WeightedTermVectorPair allFormation(WeightedTermVector a, WeightedTermVector b) {
        return null;
    }

    @Override
    public WeightedTermVectorPair formVector(WeightedTermVector a, WeightedTermVector b, VectorInclusion vectorInclusion) {
        switch (vectorInclusion) {
            case A:
                return aFormation(a, b);
            case B:
                return bFormation(a, b);
            case AB:
                return abFormation(a, b);
            case ALL:
                return allFormation(a, b);
            default:
                throw new RuntimeException("Unimplemented vector inclusion");
        }
    }

    @Override
    public WeightedTermVectorPair formVector(WeightedTermVectorPair ab, VectorInclusion vectorInclusion) {
        return formVector(ab.getA(), ab.getB(), vectorInclusion);
    }
}
