package sk.upjs.ics.mmizak.simfolk.algorithm.utilities;

import sk.upjs.ics.mmizak.simfolk.algorithm.utilities.implementations.TermBuilder;
import sk.upjs.ics.mmizak.simfolk.algorithm.utilities.interfaces.ITermBuilder;

public class UtilityFactory {

    private static TermBuilder termBuilder;

    public static ITermBuilder getNGramBuilder() {
        if (termBuilder == null) {
            termBuilder = new TermBuilder();
        }
        return termBuilder;
    }
}
