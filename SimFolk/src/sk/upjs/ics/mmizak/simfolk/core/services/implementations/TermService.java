package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

public class TermService implements ITermService {

    private ITermDao termDao;
    private ITermBuilder termBuilder;

    public TermService(ITermDao termDao, ITermBuilder termBuilder) {
        this.termDao = termDao;
        this.termBuilder = termBuilder;
    }

    @Override
    public List<Term> buildAndSync(Object objectToBuildTermsFrom, VectorAlgorithmConfiguration vectorConfig) {
        List<Term> terms = termBuilder.buildTerms(vectorConfig, objectToBuildTermsFrom);

        terms = saveOrEdit(terms);
        return terms;
    }

    //<editor-fold desc="Methods delegated to DAO">
    @Override
    public List<Term> syncTermIds(List<Term> terms) {
        return termDao.syncTermIds(terms);
    }

    @Override
    public Term syncTermId(Term term) {
        return termDao.syncTermId(term);
    }

    @Override
    public List<Term> getAllTerms() {
        return termDao.getAllTerms();
    }

    @Override
    public List<Term> getTermsById(List<Long> termIds) {
        return termDao.getTermsById(termIds);
    }

    @Override
    public List<Term> getTermByTermScheme(AlgorithmConfiguration.TermScheme termScheme) {
        return termDao.getTermByTermScheme(termScheme);
    }

    @Override
    public Term getTermById(Term term) {
        return termDao.getTermById(term);
    }

    @Override
    public Term saveOrEdit(Term term) {
        return termDao.saveOrEdit(term);
    }


    @Override
    public List<Term> saveOrEdit(List<Term> terms) {
        return termDao.saveOrEdit(terms);
    }

    @Override
    public void delete(Term term) {
        termDao.delete(term);
    }
    //</editor-fold>
}
