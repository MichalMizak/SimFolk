package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;

import java.util.List;
import java.util.Map;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;

public interface ITermGroupService {

    /**
     * Synchronizes term groups and saves the progress. Returns all TermGroups with non-null-groupId.
     *
     * @param terms
     * @param vectorConfiguration
     * @param tolerance
     * @return A List<WeightedTermGroup> with corresponding groupId and frequency as a weight
     */
    List<WeightedTermGroup> syncInitAndSaveTermGroups(List<Term> terms, VectorAlgorithmConfiguration vectorConfiguration,
                                                      double tolerance);

    /**
     * Synchronizes terms with database taking termComparisonAlgorithm into account,
     * works with previously unsaved terms. Term groups with null key are not similar to any
     * terms in the database, so can be considered a new TermGroup
     * @param terms
     * @param vectorConfiguration
     * @param tolerance
     * @return A List<WeightedTermGroup> with corresponding groupId and frequency as a weight
     */
    List<WeightedTermGroup> syncAndInitTermGroups(List<Term> terms, VectorAlgorithmConfiguration vectorConfiguration,
                                                  double tolerance);

    Map<Long, List<TermGroup>> saveOrEdit(Map<Long, List<TermGroup>> initializedTermGroups);

    List<WeightedTermGroup> saveOrEditWeightedTermGroups(List<WeightedTermGroup> termGroups);

    List<TermGroup> saveOrEdit(List<TermGroup> termGroups);

    //<editor-fold desc="Dao methods">
    List<TermGroup> getAllTermGroups();

    List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    TermGroup getTermGroupById(Long groupId);

    TermGroup saveOrEdit(TermGroup termGroup);

    void delete(TermGroup termGroup);


    /**
     * If terms are already in database (have non-zero ids) and were previously
     * assigned to an equivalence group with a given term comparison algorithm and
     * a given tolerance, we create a termGroup with the given group id and other values.
     *
     * @param terms
     * @param termComparisonAlgorithm
     * @param tolerance
     * @return
     */
    List<TermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    TermGroup syncGroupId(Term term, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, AlgorithmConfiguration.TermScheme termScheme, double tolerance);

    //</editor-fold>
}
