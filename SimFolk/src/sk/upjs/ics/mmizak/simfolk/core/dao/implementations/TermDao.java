package sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.ArrayList;
import java.util.List;

// TODO: implement
public class TermDao implements ITermDao {

    @Override
    public List<Term> syncTermIds(List<Term> terms) {
        List<Term> result = new ArrayList<>();

        for (Term term : terms) {
            result.add(syncTermId(term));
        }

        return result;
    }

    @Override
    public Term syncTermId(Term term) {
        return null;
    }
}
