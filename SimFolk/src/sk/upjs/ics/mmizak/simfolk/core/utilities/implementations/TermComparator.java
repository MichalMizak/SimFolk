package sk.upjs.ics.mmizak.simfolk.core.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.Comparator;

public class TermComparator implements ITermComparator {

    @Override
    public boolean equals(Term t1, Term t2) {
        return t1.getLyricsFragment().equals(t2.getLyricsFragment());
    }

}
