package sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

// TODO: implement
public class WeightedVectorDao implements IWeightedVectorDao {
    @Override
    public List<WeightedVector> getAllWeightedVectors(TermWeightType termWeightType,
                                                      TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return null;
    }

    @Override
    public WeightedVector getWeightedVectorBySongId(Integer songId, TermWeightType termWeightType,
                                                    TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return null;
    }

    @Override
    public void saveOrEdit(WeightedVector weightedVector) {

    }

    @Override
    public void delete(WeightedVector weightedVector) {

    }
}
