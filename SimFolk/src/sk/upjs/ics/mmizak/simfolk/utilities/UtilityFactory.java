package sk.upjs.ics.mmizak.simfolk.utilities;

import sk.upjs.ics.mmizak.simfolk.utilities.Implementations.NGramBuilder;
import sk.upjs.ics.mmizak.simfolk.utilities.interfaces.INGramBuilder;

public class UtilityFactory {

    private static INGramBuilder nGramBuilder;

    public static INGramBuilder getNGramBuilder() {
        if (nGramBuilder == null) {
            nGramBuilder = new NGramBuilder();
        }
        return nGramBuilder;
    }
}
