package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;

public interface ITermGroupDao {

    List<TermGroup> getAllTermGroups();

    List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    TermGroup getTermGroupById(Integer groupId);

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

}
