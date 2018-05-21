package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ITermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermGroupService;
import sk.upjs.ics.mmizak.simfolk.core.utilities.TermGroupIdComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.*;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class TermGroupService implements ITermGroupService {

    private ITermGroupDao termGroupDao;
    private ITermComparator termComparator;

    public TermGroupService(ITermGroupDao termGroupDao, ITermComparator termComparator) {
        this.termGroupDao = termGroupDao;
        this.termComparator = termComparator;
    }


    @Override
    public List<WeightedTermGroup> syncInitAndSaveTermGroups(List<Term> terms, VectorAlgorithmConfiguration vectorConfiguration, double tolerance) {
        List<WeightedTermGroup> result = syncAndInitTermGroups(terms, vectorConfiguration, tolerance);

        result = saveOrEditTermGroupsFromWeightedTermGroups(result);

        return result;
    }

    @Override
    public List<WeightedTermGroup> syncAndInitTermGroups(List<Term> terms, VectorAlgorithmConfiguration vectorConfiguration,
                                                         double tolerance) {
        List<TermGroup> syncedTermGroups = syncGroupIds(terms, vectorConfiguration.getTermComparisonAlgorithm(), tolerance);

        boolean foundNull = false;

        for (TermGroup termGroup : syncedTermGroups) {
            if (termGroup.getGroupId() == null) {
                foundNull = true;
                break;
            }
        }

        List<WeightedTermGroup> result = new ArrayList<>();

        if (!foundNull) {
            WeightedTermGroup previousTermGroup = null;

            if (!syncedTermGroups.isEmpty())
                previousTermGroup = new WeightedTermGroup(syncedTermGroups.get(0));

            // sort by ID
            syncedTermGroups.sort(new TermGroupIdComparator());

            // if it is empty the for cycle won't run and we'll return empty result
            for (int i = 1; i < syncedTermGroups.size(); i++) {
                TermGroup syncedTermGroup = syncedTermGroups.get(i);

                if (previousTermGroup.getGroupId().equals(syncedTermGroup.getGroupId())) {
                    previousTermGroup.setTermWeight(previousTermGroup.getTermWeight() + 1D);
                } else {
                    result.add(previousTermGroup);
                    previousTermGroup = new WeightedTermGroup(syncedTermGroup);
                }
            }

            result.add(previousTermGroup);
            result = initIncidenceCount(result);

            return result;
        }

        List<TermGroup> allTermGroups = getTermGroups(vectorConfiguration.getTermComparisonAlgorithm(),
                vectorConfiguration.getTermScheme(), tolerance);

        switch (vectorConfiguration.getTermGroupMergingStrategy()) {
            case MERGE_ALL:
                result = mergeAll(syncedTermGroups, allTermGroups, vectorConfiguration, tolerance);
                break;
            case MERGE_ANY:
                result = mergeAny(syncedTermGroups, allTermGroups, vectorConfiguration, tolerance);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        // TODO: This doesn't take into account if we are considering the same song
        // Meaning that if the song is already in the database, the incidence count is higher
        // than it should be by the number of times it is in the song.
        result = initIncidenceCount(result);
        return result;
    }

    private List<WeightedTermGroup> initIncidenceCount(List<WeightedTermGroup> result) {
        for (WeightedTermGroup weightedTermGroup : result) {
            weightedTermGroup.setDatabaseIncidenceCount(
                    weightedTermGroup.getDatabaseIncidenceCount() +
                            weightedTermGroup.getTermWeight().intValue());
        }

        return result;
    }

    /**
     * MERGE_ANY strategy merges the first term group that is similar according to termComparator.compareGroups
     *
     * @param termGroups
     * @param allTermGroups
     * @param vectorConfiguration
     * @param tolerance
     * @return
     */
    private List<WeightedTermGroup> mergeAny(List<TermGroup> termGroups, List<TermGroup> allTermGroups,
                                             VectorAlgorithmConfiguration vectorConfiguration, double tolerance) {
        List<WeightedTermGroup> result = new ArrayList<>();
        List<WeightedTermGroup> mergedNullGroups = getMergedNullGroups(termGroups, vectorConfiguration, tolerance, result);


        // init all null
        for (WeightedTermGroup mergedNullGroup : mergedNullGroups) {

            TermGroup similarTermGroup = null;

            // find and fetch first similar
            for (TermGroup allTermGroup : allTermGroups) {
                if (termComparator.compareGroups(mergedNullGroup, allTermGroup, vectorConfiguration, tolerance)) {
                    similarTermGroup = allTermGroup;
                    break;
                }
            }

            if (similarTermGroup != null) {
                Long similarId = similarTermGroup.getGroupId();

                boolean foundSimilarId = false;

                for (WeightedTermGroup weightedTermGroup : result) {

                    if (similarId.equals(weightedTermGroup.getGroupId())) {

                        double newTermWeight = weightedTermGroup.getTermWeight()
                                + mergedNullGroup.getTermWeight();

                        weightedTermGroup.setTermWeight(newTermWeight);

                        weightedTermGroup.getTerms().addAll(mergedNullGroup.getTerms());
                        foundSimilarId = true;
                        break;
                    }
                }

                if (!foundSimilarId) {
                    mergedNullGroup.setGroupId(similarId);
                    result.add(mergedNullGroup);
                }
            } else {
                result.add(mergedNullGroup);
            }
        }

        return result;
    }

    /**
     * MERGE_ALL strategy merges all term groups that are similar according to termComparator.compareGroups
     *
     * @param termGroups
     * @param allTermGroups
     * @param vectorConfiguration
     * @param tolerance
     * @return
     */
    private List<WeightedTermGroup> mergeAll(List<TermGroup> termGroups, List<TermGroup> allTermGroups,
                                             VectorAlgorithmConfiguration vectorConfiguration, double tolerance) {

        List<WeightedTermGroup> result = new ArrayList<>();
        List<WeightedTermGroup> mergedNullGroups = getMergedNullGroups(termGroups, vectorConfiguration, tolerance, result);

        //TODO: implement
        for (WeightedTermGroup syncedTermGroup : mergedNullGroups) {

            if (syncedTermGroup.getGroupId() != null) {
                continue;
            }

            List<TermGroup> similarTermGroups = new ArrayList<>();

            // iterate over all term groups, compare them according to comparator and
            // accumulate similar term groups
            for (TermGroup allTermGroup : allTermGroups) {
                if (termComparator.compareGroups(syncedTermGroup, allTermGroup, vectorConfiguration, tolerance)) {
                    similarTermGroups.add(allTermGroup);
                    break;
                }
            }

            // if we found no similar group we have nothing to do
            if (!similarTermGroups.isEmpty()) {
                result.add(syncedTermGroup);
                continue;
            }

            if (similarTermGroups.size() == 1) {

            }

        }

        throw new UnsupportedOperationException();
    }

    private List<WeightedTermGroup> getMergedNullGroups(List<TermGroup> termGroups, VectorAlgorithmConfiguration vectorConfiguration, double tolerance, List<WeightedTermGroup> result) {
        // map groups to ids
        Map<Long, List<TermGroup>> groupIdToTermGroups = mapGroupIdToTermGroups(termGroups);


        groupIdToTermGroups.forEach((groupId, termGroups1) -> {
            // surely is non null
            if (groupId != null) {
                WeightedTermGroup weightedTermGroup = new WeightedTermGroup(termGroups1.get(0));
                weightedTermGroup.setTermWeight((double) termGroups1.size());
                result.add(weightedTermGroup);
            }
        });


        // unify similar term groups with null ids
        List<TermGroup> nullIdGroups = groupIdToTermGroups.get(null);

        return mergeNullIdGroups(nullIdGroups, vectorConfiguration, tolerance);
    }

    /**
     * Merges all null id groups with a MERGE_ALL strategy
     *
     * @param nullIdGroups
     * @param vectorConfiguration
     * @param tolerance
     * @return
     */
    private List<WeightedTermGroup> mergeNullIdGroups(List<TermGroup> nullIdGroups,
                                                      VectorAlgorithmConfiguration vectorConfiguration,
                                                      double tolerance) {
        Set<Integer> usedIndices = new HashSet<>();
        List<WeightedTermGroup> result = new ArrayList<>();

        for (int i = 0; i < nullIdGroups.size(); i++) {
            if (usedIndices.contains(i)) {
                continue;
            }
            WeightedTermGroup iTermGroup = new WeightedTermGroup(nullIdGroups.get(i));

            for (int j = i + 1; j < nullIdGroups.size(); j++) {
                if (usedIndices.contains(j)) {
                    continue;
                }

                WeightedTermGroup jTermGroup = new WeightedTermGroup(nullIdGroups.get(j));

                if (termComparator.compareGroups(iTermGroup, jTermGroup, vectorConfiguration, tolerance)) {
                    // merge terms and refresh loop
                    iTermGroup.getTerms().addAll(jTermGroup.getTerms());
                    iTermGroup.setTermWeight(iTermGroup.getTermWeight() + 1.0);
                    usedIndices.add(j);
                    j = i + 1;
                }
            }
            result.add(iTermGroup);
        }

        return result;
    }

    private Map<Long, List<TermGroup>> mapGroupIdToTermGroups(List<TermGroup> syncedTermGroups) {
        Map<Long, List<TermGroup>> groupIdToTermGroups = new HashMap<>();

        for (TermGroup group : syncedTermGroups) {
            groupIdToTermGroups.computeIfAbsent(group.getGroupId(), k -> new ArrayList<>()).add(group);
        }
        return groupIdToTermGroups;
    }


    @Override
    public Map<Long, List<TermGroup>> saveOrEdit(Map<Long, List<TermGroup>> initializedTermGroups) {

        List<TermGroup> tempGroups = new ArrayList<>();

        initializedTermGroups.forEach((groupId, termGroups) ->
                tempGroups.addAll(saveOrEdit(termGroups)));

        return mapGroupIdToTermGroups(tempGroups);
    }

    @Override
    public List<WeightedTermGroup> saveOrEditTermGroupsFromWeightedTermGroups(List<WeightedTermGroup> termGroups) {
        List<WeightedTermGroup> result = new ArrayList<>();

        for (WeightedTermGroup termGroup : termGroups) {
            TermGroup savedTermGroup = saveOrEdit(termGroup);

            WeightedTermGroup wtg = new WeightedTermGroup(savedTermGroup, termGroup.getSongId(),
                    termGroup.getTermWeightType(), termGroup.getTermWeight());

            result.add(wtg);
        }

        return result;
    }

    @Override
    public List<TermGroup> saveOrEdit(List<TermGroup> termGroups) {
        List<TermGroup> result = new ArrayList<>();

        for (TermGroup termGroup : termGroups) {
            TermGroup savedTermGroup = saveOrEdit(termGroup);
            result.add(savedTermGroup);
        }

        return result;
    }

    //<editor-fold desc="Methods delegated to Dao">
    @Override
    public List<TermGroup> getAllTermGroups() {
        return termGroupDao.getAllTermGroups();
    }

    @Override
    public List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return termGroupDao.getTermGroups(termComparisonAlgorithm, tolerance);
    }

    @Override
    public TermGroup getTermGroupById(Long groupId) {
        return termGroupDao.getTermGroupById(groupId);
    }

    @Override
    public TermGroup saveOrEdit(TermGroup termGroup) {
        return termGroupDao.saveOrEdit(termGroup);
    }

    @Override
    public void delete(TermGroup termGroup) {
        termGroupDao.delete(termGroup);
    }

    @Override
    public List<TermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return termGroupDao.syncGroupIds(terms, termComparisonAlgorithm, tolerance);
    }

    @Override
    public TermGroup syncGroupId(Term term, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return termGroupDao.syncGroupId(term, termComparisonAlgorithm, tolerance);
    }

    @Override
    public List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, TermScheme termScheme, double tolerance) {
        return termGroupDao.getTermGroups(termComparisonAlgorithm, termScheme, tolerance);
    }
    //</editor-fold>
}
