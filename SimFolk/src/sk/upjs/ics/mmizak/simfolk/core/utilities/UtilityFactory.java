package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.services.implementations.TermService;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.WeightService;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermService;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.IWeightService;
import sk.upjs.ics.mmizak.simfolk.core.utilities.implementations.*;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.*;

public enum UtilityFactory {

    INSTANCE;

    private ITermBuilder termBuilder;

    private ITermService termService;

    private ITermVectorFormatter termVectorFormatter;

    private IWeightService weightCalculator;

    // comparators
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

    public IWeightService getWeightCalculator() {
        if (weightCalculator == null) {
            weightCalculator = new WeightService();
        }
        return weightCalculator;
    }

    public ITermService getTermService() {
        if (termService == null) {
            termService = new TermService();
        }
        return termService;
    }
}
