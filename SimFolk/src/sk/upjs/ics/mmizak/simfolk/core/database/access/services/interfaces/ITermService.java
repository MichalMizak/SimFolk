package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermScheme;

public interface ITermService {

    /**
     * Synchronizes the term ids to the database so that
     * if a term is already in the database it has non null id
     * @param terms
     * @return
     */
    List<Term> syncTermIds(List<Term> terms);

    Term syncTermId(Term term);

    List<Term> getAllTerms();

    List<Term> getTermsById(List<Long> termIds);

    List<Term> getTermByTermScheme(TermScheme termScheme);

    Term getTermById(Term term);

    Term saveOrEdit(Term term);

    List<Term> saveOrEdit(List<Term> terms);

    void delete(Term term);
}
