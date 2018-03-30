package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations.TermComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

public interface ITermGroupService {

    List<TermGroup> syncAndInitTermGroups(List<Term> terms, VectorAlgorithmConfiguration vectorConfiguration,
                                          TermComparator termComparator, double tolerance);

    //<editor-fold desc="Dao methods">
    List<TermGroup> getAllTermGroups();

    List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    TermGroup getTermGroupById(Long groupId);

    TermGroup saveOrEdit(TermGroup termGroup);

    void delete(TermGroup termGroup);


    /**
     * If terms are already in database (have non-zero ids) and were previously
     * assigned to an equivalence group with a given term comparison algorithm and
     * a given tolerance, we assign them with the group id
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
