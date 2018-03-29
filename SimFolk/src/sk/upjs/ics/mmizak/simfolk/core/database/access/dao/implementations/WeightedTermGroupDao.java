package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.Record2;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.records.WeightedTermGroupRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.TWeightedTermGroup.T_WEIGHTED_TERM_GROUP;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

public class WeightedTermGroupDao implements IWeightedTermGroupDao {


    private final DSLContext create;
    private final ITermGroupDao termGroupDao;

    public WeightedTermGroupDao(DSLContext create, ITermGroupDao termGroupDao) {
        this.create = create;
        this.termGroupDao = termGroupDao;
    }

    @Override
    public WeightedTermGroup getByIds(Integer groupId, Integer songId) {
        TermGroup termGroup = termGroupDao.getTermGroupById(groupId);

        if (termGroup == null) {
            return null;
        }

        return syncWeightWithTermGroup(groupId, songId, termGroup);
    }

    /**
     * All weighted term groups, unsorted.
     *
     * @param termWeightType
     * @param termComparisonAlgorithm
     * @param tolerance
     * @return
     */
    @Override
    public List<WeightedTermGroup> getAll(TermWeightType termWeightType,
                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        List<WeightedTermGroup> result = new ArrayList<>();

        List<TermGroup> fittingTermGroups = termGroupDao.getTermGroups(termComparisonAlgorithm, tolerance);

        for (TermGroup termGroup : fittingTermGroups) {

            List<WeightedTermGroup> weightedTermGroups =
                    create.selectFrom(T_WEIGHTED_TERM_GROUP)
                            .where(T_WEIGHTED_TERM_GROUP.GROUPID.eq(termGroup.getGroupId()))
                            .fetch(this::map);

            for (WeightedTermGroup wt : weightedTermGroups) {
                wt.setTermGroup(termGroup);
            }

            result.addAll(weightedTermGroups);
        }

        return result;
    }


    @Override
    public List<WeightedTermGroup> getAll(Integer songId, TermWeightType termWeightType,
                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        List<WeightedTermGroup> result = new ArrayList<>();

        List<TermGroup> fittingTermGroups = termGroupDao.getTermGroups(termComparisonAlgorithm, tolerance);

        for (TermGroup termGroup : fittingTermGroups) {

            List<WeightedTermGroup> temp = create.selectFrom(T_WEIGHTED_TERM_GROUP)
                    .where(T_WEIGHTED_TERM_GROUP.SONGID.eq(songId))
                    .and(T_WEIGHTED_TERM_GROUP.GROUPID.eq(termGroup.getGroupId()))
                    .fetch(this::map);

            for (WeightedTermGroup weightedGroup : temp) {
                weightedGroup.setTermGroup(termGroupDao.getTermGroupById(termGroup.getGroupId()));
            }

            result.addAll(temp);
        }
        return result;
    }

    @Override
    public WeightedTermGroup saveOrEdit(WeightedTermGroup weightedTermGroup) {
        TermGroup newTermGroup = termGroupDao.saveOrEdit(weightedTermGroup);

        weightedTermGroup.setTermGroup(newTermGroup);

        saveOrEditExcludingTermGroup(weightedTermGroup);

        return weightedTermGroup;
    }

    @Override
    public WeightedTermGroup saveOrEditExcludingTermGroup(WeightedTermGroup weightedTermGroup) {
        if (getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId()) == null) {
            create.insertInto(T_WEIGHTED_TERM_GROUP)
                    .columns(T_WEIGHTED_TERM_GROUP.GROUPID, T_WEIGHTED_TERM_GROUP.SONGID,
                            T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPE, T_WEIGHTED_TERM_GROUP.WEIGHT)
                    .values(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId(),
                            weightedTermGroup.getTermWeightType().toString(), weightedTermGroup.getTermWeight())
                    .execute();
        } else {
            create.update(T_WEIGHTED_TERM_GROUP)
                    .set(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPE, weightedTermGroup.getTermWeightType().toString())
                    .set(T_WEIGHTED_TERM_GROUP.WEIGHT, weightedTermGroup.getTermWeight())
                    .where(T_WEIGHTED_TERM_GROUP.GROUPID.eq(weightedTermGroup.getGroupId()))
                    .and(T_WEIGHTED_TERM_GROUP.SONGID.eq(weightedTermGroup.getSongId()))
                    .execute();
        }

        return weightedTermGroup;
    }

    @Override
    public void delete(WeightedTermGroup weightedTermGroup) {
        termGroupDao.delete(weightedTermGroup);

        deleteExcludingTermGroup(weightedTermGroup);
    }

    @Override
    public void deleteExcludingTermGroup(WeightedTermGroup weightedTermGroup) {
        create.deleteFrom(T_WEIGHTED_TERM_GROUP)
                .where(T_WEIGHTED_TERM_GROUP.GROUPID.eq(weightedTermGroup.getGroupId()))
                .and(T_WEIGHTED_TERM_GROUP.SONGID.eq(weightedTermGroup.getSongId()))
                .execute();
    }

    @Override
    public List<WeightedTermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        List<WeightedTermGroup> result = new ArrayList<>();

        for (Term term : terms) {
            result.add(syncGroupId(term, termComparisonAlgorithm, tolerance));
        }

        return result;
    }

    /**
     * Synchronizes term group values if the term group is present in the database. SongId, termWeightType and
     * weight are set to default values
     *
     * @param terms
     * @param termComparisonAlgorithm
     * @param tolerance
     * @return
     */
    @Override
    public WeightedTermGroup syncGroupId(Term terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        TermGroup termGroup = termGroupDao.syncGroupId(terms, termComparisonAlgorithm, tolerance);

        return new WeightedTermGroup(termGroup, null, null, 1D);
    }

    //<editor-fold desc="Private methods">

    private WeightedTermGroup syncWeightWithTermGroup(Integer groupId, Integer songId, TermGroup termGroup) {
        Record2<String, Double> typeToWeight = create.select(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPE, T_WEIGHTED_TERM_GROUP.WEIGHT)
                .from(T_WEIGHTED_TERM_GROUP)
                .where(T_WEIGHTED_TERM_GROUP.GROUPID.eq(groupId))
                .and(T_WEIGHTED_TERM_GROUP.SONGID.eq(songId))
                .fetchOne();

        if (typeToWeight == null) {
            return null;
        }

        String weightType = typeToWeight.get(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPE);
        Double weight = typeToWeight.get(T_WEIGHTED_TERM_GROUP.WEIGHT);

        return new WeightedTermGroup(termGroup, songId, TermWeightType.valueOf(weightType), weight);
    }

    private WeightedTermGroup map(WeightedTermGroupRecord weightedTermGroupRecord) {

        Integer songid = weightedTermGroupRecord.getSongid();
        String termweighttype = weightedTermGroupRecord.getTermweighttype();
        Double weight = weightedTermGroupRecord.getWeight();

        return new WeightedTermGroup(songid, TermWeightType.valueOf(termweighttype), weight);
    }
    //</editor-fold>
}
