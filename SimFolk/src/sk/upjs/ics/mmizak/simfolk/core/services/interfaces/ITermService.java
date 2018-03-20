package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

public interface ITermService {

    /**
     * Synchronizes the term ids to the database so that
     * if a term is already in the database it has non null id
     * @param terms
     * @return
     */
    List<Term> syncTerms(List<Term> terms);

    Term syncTerm(Term term);
}
