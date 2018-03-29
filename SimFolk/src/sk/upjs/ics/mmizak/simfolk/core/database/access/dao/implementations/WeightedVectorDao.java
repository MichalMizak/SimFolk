package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.*;
import java.util.stream.Collectors;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class WeightedVectorDao implements IWeightedVectorDao {

    private final DSLContext create;
    private final IWeightedTermGroupDao weightedTermGroupDao;

    public WeightedVectorDao(DSLContext create, IWeightedTermGroupDao weightedTermGroupDao) {
        this.create = create;
        this.weightedTermGroupDao = weightedTermGroupDao;
    }

    @Override
    public List<WeightedVector> getAllWeightedVectors(TermWeightType termWeightType,
                                                      TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        List<WeightedTermGroup> allGroups = weightedTermGroupDao.getAll(termWeightType, termComparisonAlgorithm, tolerance);

        // TODO: Delegate this to a batch select from database
        // Groups term groups by songIds
        Map<Integer, List<WeightedTermGroup>> songIdToWeightedTermGroups =
                new HashMap<>();
        for (WeightedTermGroup allGroup : allGroups) {
            songIdToWeightedTermGroups.computeIfAbsent(allGroup.getSongId(), k -> new ArrayList<>()).add(allGroup);
        }

        List<WeightedVector> result = new ArrayList<>();

        songIdToWeightedTermGroups.forEach((id, group) -> result.add(new WeightedVector(id, group)));

        return result;
    }



    @Override
    public WeightedVector getWeightedVectorBySongId(Integer songId, TermWeightType termWeightType,
                                                    TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        List<WeightedTermGroup> fittingGroups = weightedTermGroupDao.getAll(songId, termWeightType, termComparisonAlgorithm, tolerance);

        return new WeightedVector(songId, fittingGroups);
    }

    @Override
    public void saveOrEdit(WeightedVector weightedVector) {
        for (WeightedTermGroup weightedTermGroup : weightedVector.getVector()) {
            weightedTermGroupDao.saveOrEdit(weightedTermGroup);
        }
    }

    @Override
    public void delete(WeightedVector weightedVector) {
        for (WeightedTermGroup weightedTermGroup : weightedVector.getVector()) {
            weightedTermGroupDao.delete(weightedTermGroup);
        }
    }
}
