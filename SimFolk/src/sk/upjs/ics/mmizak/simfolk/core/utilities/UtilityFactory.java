package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.utilities.implementations.TermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.utilities.implementations.TermComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.implementations.TermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.utilities.implementations.VectorComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.IVectorComparator;

public enum UtilityFactory {

    INSTANCE;


    private ITermBuilder termBuilder;

    private ITermVectorFormatter termVectorFormatter;

    private ITermComparator termComparator;

    private IVectorComparator vectorComparator;

    public ITermBuilder getTermBuilder() {
        if (termBuilder == null) {
            termBuilder = new TermBuilder();
        }
        return termBuilder;
    }

    public ITermVectorFormatter getTermVectorFormatter() {
        if (termVectorFormatter == null) {
            termVectorFormatter = new TermVectorFormatter();
        }
        return termVectorFormatter;
    }

    public ITermComparator getTermComparator() {
        if (termComparator == null) {
            termComparator = new TermComparator();
        }
        return termComparator;
    }

    public IVectorComparator getVectorComparator() {
        if (vectorComparator == null) {
            vectorComparator = new VectorComparator();
        }
        return vectorComparator;
    }
}
