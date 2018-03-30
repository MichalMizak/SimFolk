package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;

public interface ITermGroupDao {

    List<TermGroup> getAllTermGroups();

    List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    TermGroup getTermGroupById(Long groupId);

    TermGroup saveOrEdit(TermGroup termGroup);

    void delete(TermGroup termGroup);

    /**
     * Returns a list of the same size as the parameter terms containing
     * one-term TermGroups with initialized group id.
     * Group ids will vary depending on termComparisonAlgorithm and the tolerance we use for the same term.
     * If a term belongs to a group, initializes all fields. If not, sets groupId and terms to default values
     *
     * @param terms
     * @param termComparisonAlgorithm
     * @param tolerance
     * @return
     */
    List<TermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    TermGroup syncGroupId(Term term, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance);

    List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, TermScheme termScheme, double tolerance);
}
