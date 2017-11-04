package sk.upjs.ics.mmizak.simfolk.utilities.interfaces;

import sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations.BiGram;
import sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations.TriGram;
import sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations.UnGram;

import java.util.List;

public interface INGramBuilder {

    List<UnGram> buildUnGrams(String lyrics);

    List<BiGram> buildBiGrams(String lyrics);

    List<TriGram> buildTriGrams(String lyrics);

}
