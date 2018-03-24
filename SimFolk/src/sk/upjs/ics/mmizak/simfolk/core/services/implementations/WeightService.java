package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.dao.DaoFactory;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.IWeightService;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;

import java.util.*;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermWeightType;

// TODO: implement reset weights and weight calculations
// TODO: TEST
public class WeightService implements IWeightService {

    IWeightedVectorDao weightedVectorDao = DaoFactory.INSTANCE.getWeightedVectorDao();
    IWeightedTermGroupDao weightedTermGroupDao = DaoFactory.INSTANCE.getTermGroupDao();

    /**
     * Resets weights for all termWeightTypes
     */
    @Override
    public void resetWeights() {
        List<TermWeightType> termWeightTypes = Arrays.asList(TermWeightType.values());

        resetWeights(termWeightTypes);
    }

    /**
     * Resets weights for chosen termWeightTypes
     *
     * @param termWeightTypes
     */
    @Override
    public void resetWeights(List<TermWeightType> termWeightTypes) {
        for (TermWeightType type : termWeightTypes) {
            resetWeights(type);
        }
    }

    /**
     * Resets weights for this one termWeightType
     *
     * @param termWeightType
     */
    @Override
    public void resetWeights(TermWeightType termWeightType) {
       // weightedTermGroupDao.deleteAll(termWeightType);

    }

    @Override
    public WeightedTermGroup getWeightedTermById(Term term, TermWeightType termWeightType, Long songId) {
        return null;
    }

    @Override
    public WeightedTermGroup resetAndCalculateWeight(Term term, TermWeightType termWeightType, Long songId) {
        return null;
    }

    @Override
    public WeightedVector resetAndCalculateWeight(List<Term> terms, TermWeightType termWeightType, Long songId) {
        return null;
    }


    /**
     * A method that returns a WeightedVector of a song. If song already is in database (e.g. has its weights
     * calculated) then the calculation is delegated to method getWeightedTermVectorBySongId
     * <p>
     * If the @param songId contains terms which are already in the database and their weights
     * are established, in this new context the weights might turn out differently. We will try to find out
     * whether the terms already exist in the database and calculate the weights taking them into account.
     *
     * @param terms
     * @param termWeightType
     * @param songId
     * @return
     */
    public WeightedVector calculateNewWeightedVector(List<Term> terms, TermWeightType termWeightType, Long songId,
                                                     TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                                     ITermComparator termComparator) {

        if (songId != null) {
            return getWeightedTermVectorBySongId(songId, termWeightType,
                    termComparisonAlgorithm, tolerance);
        }

        List<WeightedTermGroup> frequencyWeightedGroups = getFrequencyWeightedTerm(terms, termComparisonAlgorithm,
                tolerance, termComparator);

        // init termWeightType and songId
        frequencyWeightedGroups.forEach(w -> {
            w.setTermWeightType(termWeightType);
            w.setSongId(songId);
        });

        switch (termWeightType) {
            case TF_NAIVE:
                return new WeightedVector(frequencyWeightedGroups, songId);
            case TF:
                break;
            case IDF:
                break;
            case TFIDF:
                break;
            default:
                throw new RuntimeException("Unknown termWeightType");
        }
        return null;
    }

    /**
     * <p>
     * Firstly, the method synchronizes group ids with database. Then proceeds to merge terms with same group
     * ids together and assigns them the weight of the count of merged terms.
     * If a term group has null id, instead of comparing ids the method compares the terms of the groups
     * taking tolerance and termComparisonAlgorithm into account
     *
     * @param terms
     * @param termComparisonAlgorithm
     * @param tolerance
     * @param termComparator
     * @return Returns weighted term groups with their frequencies as weight.
     * Inits only fields: terms, groupId and termWeight.
     */
    private List<WeightedTermGroup> getFrequencyWeightedTerm(List<Term> terms,
                                                             TermComparisonAlgorithm termComparisonAlgorithm,
                                                             double tolerance, ITermComparator termComparator) {

        List<WeightedTermGroup> weightedTermGroups = weightedTermGroupDao.syncGroupIds(terms,
                termComparisonAlgorithm, tolerance);

        Set<Integer> usedIndices = new HashSet<>();
        List<WeightedTermGroup> result = new ArrayList<>();

        // compare the groups one by one
        for (int i = 0; i < weightedTermGroups.size(); i++) {
            if (usedIndices.contains(i)) {
                continue;
            }

            WeightedTermGroup iTermGroup = weightedTermGroups.get(i);
            Long iId = iTermGroup.getGroupId();

            iTermGroup.setTermWeight(1.0);

            for (int j = i + 1; j < weightedTermGroups.size(); j++) {
                if (usedIndices.contains(j)) {
                    continue;
                }
                WeightedTermGroup jTermGroup = weightedTermGroups.get(j);
                Long jId = jTermGroup.getGroupId();

                if (iId != null && iId.equals(jId)) {
                    mergeGroups(iTermGroup, jTermGroup, usedIndices, j);
                }

                if (iId == null || jId == null) {
                    //    Compare the i and j term groups term by term taking tolerance and
                    //    termComparisonAlgorithm into account

                    compareGroupsTermByTerm(iTermGroup, jTermGroup, termComparisonAlgorithm, tolerance,
                            termComparator, usedIndices, j, jId);
                }
            }

            result.add(iTermGroup);
        }

        return result;
    }

    /**
     * Generated method that compares two term groups term by term and merges them if they are equal.
     * jTermGroup is a singleton collection.
     * The method serves only for cosmetic purposes of getFrequencyWeightedTerm and should not be reused.
     *
     * @param iTermGroup
     * @param jTermGroup
     * @param termComparisonAlgorithm
     * @param tolerance
     * @param termComparator
     * @param usedIndices
     * @param j
     * @param jId
     */
    private void compareGroupsTermByTerm(WeightedTermGroup iTermGroup, WeightedTermGroup jTermGroup,
                                         TermComparisonAlgorithm termComparisonAlgorithm, double tolerance,
                                         ITermComparator termComparator, Set<Integer> usedIndices, int j, Long jId) {
        Term jTerm = jTermGroup.getTerms().get(0);
        for (Term iTerm : iTermGroup.getTerms()) {

            if (termComparator.compare(iTerm, jTerm, tolerance, termComparisonAlgorithm)) {

                    /*
                        If jId is null this means that no groupId has been assigned to the group.
                        If iId is null too, we do nothing. If it is not null, we "assigned" it to
                        the jGroup by not changing the id and merging the terms of j and i.
                     */

                if (jId != null) {
                    iTermGroup.setGroupId(jId);
                }

                mergeGroups(iTermGroup, jTermGroup, usedIndices, j);

                return;
            }
        }
    }


    /**
     * Merges the groups and handles used indices
     * The method serves only for cosmetic purposes of getFrequencyWeightedTerm and should not be reused.
     *
     * @param iTermGroup
     * @param jTermGroup
     * @param usedIndices
     * @param j
     */
    private void mergeGroups(WeightedTermGroup iTermGroup, WeightedTermGroup jTermGroup, Set<Integer> usedIndices, int j) {
        iTermGroup.getTerms().addAll(jTermGroup.getTerms());
        iTermGroup.setTermWeight(iTermGroup.getTermWeight() + 1);
        usedIndices.add(j);
    }


    //<editor-fold desc="Methods delegated to weightedVectorDao">
    @Override
    public WeightedVector getWeightedTermVectorBySongId(Long songId, TermWeightType termWeightType,
                                                        TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return weightedVectorDao.getWeightedVectorBySongId(songId, termWeightType, termComparisonAlgorithm, tolerance);
    }

    @Override
    public List<WeightedVector> getAllWeightedTermVectors(TermWeightType termWeightType,
                                                          TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        return weightedVectorDao.getAllWeightedVectors(termWeightType, termComparisonAlgorithm, tolerance);
    }

    @Override
    public void saveOrEdit(WeightedVector weightedVector) {
        weightedVectorDao.saveOrEdit(weightedVector);
    }

    @Override
    public void delete(WeightedVector weightedVector) {
        weightedVectorDao.delete(weightedVector);
    }
    //</editor-fold>
}
