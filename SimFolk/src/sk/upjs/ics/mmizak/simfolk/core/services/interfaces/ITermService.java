package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermScheme;

public interface ITermService {

    /**
     *  Use very carefully and make sure the instance is of required type
      */

    List<Term> buildAndSync(Object objectToBuildTermsFrom, VectorAlgorithmConfiguration vectorConfig);

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
