package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermScheme;

public interface ITermBuilder {

    /*List<Term> buildNGrams(String lyrics, int n);

    List<Term> buildUnGrams(String lyrics, TermScheme termScheme);

    List<Term> buildBiGrams(String lyrics, TermScheme termScheme);

    List<Term> buildTriGrams(String lyrics, TermScheme termScheme);*/

    /**
     * Builds terms from an object according to the algorithm type and term scheme with a fixed
     * tokenized term count or a one that needs to be otherwise specified by parameter n
     *
     * @param vectorAlgorithmConfiguration holds neccessary configuration info
     * @param objectToTokenizeIntoTerms
     * @return
     */
    List<Term> buildTerms(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, Object objectToTokenizeIntoTerms);
}
