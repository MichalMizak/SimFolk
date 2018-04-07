package sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermScheme;

public interface ITermService {

    List<Term> buildAndSync(Song song, VectorAlgorithmConfiguration vectorConfig);

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
