package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ITermService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.ArrayList;
import java.util.List;

public class TermService implements ITermService {

    private ITermDao termDao;

    public TermService(ITermDao termDao) {
        this.termDao = termDao;
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
    public List<Term> getTermsById(List<Integer> termIds) {
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
