package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

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
        Map<Long, List<WeightedTermGroup>> songIdToWeightedTermGroups =
                new HashMap<>();
        for (WeightedTermGroup allGroup : allGroups) {
            songIdToWeightedTermGroups.computeIfAbsent(allGroup.getSongId(), k -> new ArrayList<>()).add(allGroup);
        }

        List<WeightedVector> result = new ArrayList<>();

        songIdToWeightedTermGroups.forEach((id, group) -> result.add(new WeightedVector(id, group)));

        return result;
    }

    @Override
    public List<WeightedVector> getAllFittingWeightedVectors(VectorAlgorithmConfiguration vectorConfig, double tolerance) {
        List<WeightedTermGroup> fittingTermGroups = weightedTermGroupDao.getAllFitting(vectorConfig, tolerance);

        // TODO: Delegate this to a batch select from database
        // Groups term groups by songIds
        Map<Long, List<WeightedTermGroup>> songIdToWeightedTermGroups =
                new HashMap<>();
        for (WeightedTermGroup allGroup : fittingTermGroups) {
            songIdToWeightedTermGroups.computeIfAbsent(allGroup.getSongId(), k -> new ArrayList<>()).add(allGroup);
        }

        List<WeightedVector> result = new ArrayList<>();

        songIdToWeightedTermGroups.forEach((id, group) -> result.add(new WeightedVector(id, group)));

        return result;
    }

    @Override
    public WeightedVector getFittingWeightedVectorBySongId(Long songId, VectorAlgorithmConfiguration vectorConfig, double tolerance) {
        List<WeightedTermGroup> fittingTermGroups = weightedTermGroupDao.getAllFittingBySongId(songId, vectorConfig, tolerance);

        return new WeightedVector(songId, fittingTermGroups);
    }

    @Override
    public WeightedVector getFittingWeightedVectorBySongId(Long songId, TermWeightType termWeightType,
                                                    TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        List<WeightedTermGroup> fittingGroups = weightedTermGroupDao.getAllFittingBySongId(songId, termWeightType, termComparisonAlgorithm, tolerance);

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
