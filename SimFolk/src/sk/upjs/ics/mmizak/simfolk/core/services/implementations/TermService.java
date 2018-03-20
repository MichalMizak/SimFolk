package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.dao.DaoFactory;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.List;

public class TermService implements ITermService {

    private ITermDao termDao = DaoFactory.INSTANCE.getTermDao();

    @Override
    public List<Term> syncTerms(List<Term> terms) {
        return termDao.syncTermIds(terms);
    }

    @Override
    public Term syncTerm(Term term) {
        return termDao.syncTermId(term);
    }
}
