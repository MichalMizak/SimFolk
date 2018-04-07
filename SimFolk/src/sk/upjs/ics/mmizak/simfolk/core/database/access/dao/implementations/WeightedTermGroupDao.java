package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.Record2;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermWeightTypeDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.WeightedTermGroupRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TWeightedTermGroup.T_WEIGHTED_TERM_GROUP;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

public class WeightedTermGroupDao implements IWeightedTermGroupDao {


    private final DSLContext create;
    private final ITermWeightTypeDao termWeightTypeDao;
    private final ITermGroupDao termGroupDao;

    public WeightedTermGroupDao(DSLContext create, ITermWeightTypeDao termWeightTypeDao, ITermGroupDao termGroupDao) {
        this.create = create;
        this.termWeightTypeDao = termWeightTypeDao;
        this.termGroupDao = termGroupDao;
    }

    @Override
    public WeightedTermGroup getByIds(Long groupId, Long songId) {
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

        selectAndInitWeightedGroupsByTermGroups(result, fittingTermGroups);

        return result;
    }

    @Override
    @Deprecated
    public List<WeightedTermGroup> getAllFittingBySongId(Long songId, TermWeightType termWeightType,
                                                         TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        List<WeightedTermGroup> result = new ArrayList<>();

        List<TermGroup> fittingTermGroups = termGroupDao.getTermGroups(termComparisonAlgorithm, tolerance);

        selectAndInitWeightedGroupsByTermGroupsAndSongId(songId, result, fittingTermGroups);
        return result;
    }

    @Override
    public WeightedTermGroup saveOrEdit(WeightedTermGroup weightedTermGroup) {
        TermGroup newTermGroup = termGroupDao.saveOrEdit(weightedTermGroup);

        weightedTermGroup.setTermGroup(newTermGroup);

        saveOrEditExcludingTermGroup(weightedTermGroup);

        return weightedTermGroup;
    }

    /**
     * Also inits weighted term group type
     *
     * @param wtg
     * @return
     */
    @Override
    public WeightedTermGroup saveOrEditExcludingTermGroup(WeightedTermGroup wtg) {
        if (getByIds(wtg.getGroupId(), wtg.getSongId()) == null) {

            if (wtg.getTermWeightType().getId() == null) {
                wtg.setTermWeightType(termWeightTypeDao.saveOrEdit(wtg.getTermWeightType()));
            }

            create.insertInto(T_WEIGHTED_TERM_GROUP)
                    .columns(T_WEIGHTED_TERM_GROUP.GROUPID, T_WEIGHTED_TERM_GROUP.SONGID,
                            T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPEID, T_WEIGHTED_TERM_GROUP.WEIGHT)
                    .values(wtg.getGroupId(), wtg.getSongId(),
                            wtg.getTermWeightType().getId(), wtg.getTermWeight())
                    .execute();
        } else {

            if (wtg.getTermWeightType().getId() == null) {
                wtg.setTermWeightType(termWeightTypeDao.saveOrEdit(wtg.getTermWeightType()));
            }

            create.update(T_WEIGHTED_TERM_GROUP)
                    .set(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPEID, wtg.getTermWeightType().getId())
                    .set(T_WEIGHTED_TERM_GROUP.WEIGHT, wtg.getTermWeight())
                    .where(T_WEIGHTED_TERM_GROUP.GROUPID.eq(wtg.getGroupId()))
                    .and(T_WEIGHTED_TERM_GROUP.SONGID.eq(wtg.getSongId()))
                    .execute();
        }

        return wtg;
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

    @Override
    public List<WeightedTermGroup> getAllFitting(VectorAlgorithmConfiguration vectorConfig, double tolerance) {
        List<WeightedTermGroup> result = new ArrayList<>();

        List<TermGroup> fittingTermGroups = termGroupDao.getTermGroups(vectorConfig.getTermComparisonAlgorithm(),
                vectorConfig.getTermScheme(), tolerance);

        selectAndInitWeightedGroupsByTermGroups(result, fittingTermGroups);

        return result;
    }

    @Override
    public List<WeightedTermGroup> getAllFittingBySongId(Long songId, VectorAlgorithmConfiguration vectorConfig, double tolerance) {
        List<WeightedTermGroup> result = new ArrayList<>();

        List<TermGroup> fittingTermGroups = termGroupDao.getTermGroups(vectorConfig.getTermComparisonAlgorithm(),
                vectorConfig.getTermScheme(), tolerance);

        selectAndInitWeightedGroupsByTermGroupsAndSongId(songId, result, fittingTermGroups);
        return result;
    }

    private void selectAndInitWeightedGroupsByTermGroupsAndSongId(Long songId, List<WeightedTermGroup> result, List<TermGroup> fittingTermGroups) {
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
    }

    private void selectAndInitWeightedGroupsByTermGroups(List<WeightedTermGroup> result, List<TermGroup> fittingTermGroups) {
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
    }

    //<editor-fold desc="Private methods">

    private WeightedTermGroup syncWeightWithTermGroup(Long groupId, Long songId, TermGroup termGroup) {
        Record2<Integer, Double> typeToWeight = create.select(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPEID, T_WEIGHTED_TERM_GROUP.WEIGHT)
                .from(T_WEIGHTED_TERM_GROUP)
                .where(T_WEIGHTED_TERM_GROUP.GROUPID.eq(groupId))
                .and(T_WEIGHTED_TERM_GROUP.SONGID.eq(songId))
                .fetchOne();

        if (typeToWeight == null) {
            return null;
        }

        Integer weightTypeId = typeToWeight.get(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPEID);
        TermWeightType termWeightType = termWeightTypeDao.getById(weightTypeId);

        Double weight = typeToWeight.get(T_WEIGHTED_TERM_GROUP.WEIGHT);

        return new WeightedTermGroup(termGroup, songId, termWeightType, weight);
    }

    private WeightedTermGroup map(WeightedTermGroupRecord weightedTermGroupRecord) {

        Long songid = weightedTermGroupRecord.getSongid();
        Double weight = weightedTermGroupRecord.getWeight();

        Integer weightTypeId = weightedTermGroupRecord.get(T_WEIGHTED_TERM_GROUP.TERMWEIGHTTYPEID);
        TermWeightType termWeightType = termWeightTypeDao.getById(weightTypeId);

        return new WeightedTermGroup(songid, termWeightType, weight);
    }
    //</editor-fold>
}
