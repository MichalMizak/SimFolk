package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ITermGroupService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;

public class TermGroupService implements ITermGroupService {

    private ITermGroupDao termGroupDao;

    public TermGroupService(ITermGroupDao termGroupDao) {
        this.termGroupDao = termGroupDao;
    }

    @Override
    public List<TermGroup> syncAndInitTermGroups(List<Term> terms, VectorAlgorithmConfiguration vectorConfiguration,
                                                 TermComparator termComparator, double tolerance) {
        List<TermGroup> syncedTermGroups = syncGroupIds(terms, vectorConfiguration.getTermComparisonAlgorithm(), tolerance);

        boolean foundNull = false;

        for (TermGroup termGroup : syncedTermGroups) {
            if (termGroup.getGroupId() == null) {
                foundNull = true;
                break;
            }
        }

        if (!foundNull) {
            return syncedTermGroups;
        }

        List<TermGroup> allTermGroups = getTermGroups(vectorConfiguration.getTermComparisonAlgorithm(),
                vectorConfiguration.getTermScheme(), tolerance);

        switch (vectorConfiguration.getTermGroupMergingStrategy()) {
            case MERGE_ALL:
                return merge(syncedTermGroups, allTermGroups, vectorConfiguration, termComparator, tolerance);
            case MERGE_ANY:
                return mergeAny(syncedTermGroups, allTermGroups, vectorConfiguration, termComparator, tolerance);
            default:
                return syncedTermGroups;
        }
    }

    /**
     * MERGE_ANY strategy merges the first term group that is similar according to termComparator.compareGroups
     *
     * @param syncedTermGroups
     * @param allTermGroups
     * @param vectorConfiguration
     * @param termComparator
     * @param tolerance
     * @return
     */
    private List<TermGroup> mergeAny(List<TermGroup> syncedTermGroups, List<TermGroup> allTermGroups,
                                     VectorAlgorithmConfiguration vectorConfiguration,
                                     TermComparator termComparator, double tolerance) {

        for (TermGroup syncedTermGroup : syncedTermGroups) {

            if (syncedTermGroup.getGroupId() != null) {
                continue;
            }

            TermGroup similarTermGroup = null;

            for (TermGroup allTermGroup : allTermGroups) {
                if (termComparator.compareGroups(syncedTermGroup, allTermGroup, vectorConfiguration, tolerance)) {
                    similarTermGroup = allTermGroup;
                    break;
                }
            }

            if (similarTermGroup != null) {
                Long similarId = similarTermGroup.getGroupId();

                List<Term> newTerms = syncedTermGroup.getTerms();

                Integer newDatabaseCount = syncedTermGroup.getDatabaseIncidenceCount() +
                        similarTermGroup.getDatabaseIncidenceCount();

                syncedTermGroup.setTerms(similarTermGroup.getTerms());
                syncedTermGroup.setGroupId(similarId);

                // TODO: implement
                for (TermGroup notUpToDateTermGroup : syncedTermGroups) {
                    if (similarId.equals(notUpToDateTermGroup.getGroupId())) {
                        notUpToDateTermGroup.setDatabaseIncidenceCount(newDatabaseCount);
                        notUpToDateTermGroup.getTerms().addAll(newTerms);
                    }

                }

                mergeAnySave(syncedTermGroup, similarTermGroup);
            }
        }

        return syncedTermGroups;
    }

    // TODO: implement
    private void mergeAnySave(TermGroup syncedTermGroup, TermGroup similarTermGroup) {
        syncedTermGroup.setDatabaseIncidenceCount(similarTermGroup.getDatabaseIncidenceCount() + 1);

    }

    /**
     * MERGE_ALL strategy merges all term groups that are similar according to termComparator.compareGroups
     *
     * @param syncedTermGroups
     * @param allTermGroups
     * @param vectorConfiguration
     * @param termComparator
     * @param tolerance
     * @return
     */
    private List<TermGroup> merge(List<TermGroup> syncedTermGroups, List<TermGroup> allTermGroups,
                                  VectorAlgorithmConfiguration vectorConfiguration,
                                  TermComparator termComparator, double tolerance) {
        for (TermGroup syncedTermGroup : syncedTermGroups) {

            if (syncedTermGroup.getGroupId() != null) {
                continue;
            }

            List<TermGroup> similarTermGroups = new ArrayList<>();

            for (TermGroup allTermGroup : allTermGroups) {
                if (termComparator.compareGroups(syncedTermGroup, allTermGroup, vectorConfiguration, tolerance)) {
                    similarTermGroups.add(allTermGroup);
                    break;
                }
            }

            if (!similarTermGroups.isEmpty()) {
                //syncedTermGroup.setGroupId(similarTermGroups.getGroupId());
            }
        }

        return syncedTermGroups;
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
