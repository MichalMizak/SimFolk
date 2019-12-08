package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermScheme;

// TODO: Test
public class StringTermBuilder implements ITermBuilder {

    private List<Term> buildNGrams(String lyrics, int n) {
        switch (n) {
            case 1:
                return buildUnGrams(lyrics, TermScheme.NGRAM);
            case 2:
                return buildBiGrams(lyrics, TermScheme.NGRAM);
            case 3:
                return buildTriGrams(lyrics, TermScheme.NGRAM);
        }

        String[] words = lyrics.split("\\s+");
        int nGramCount = words.length - n - 1;
        Term[] nGrams = new Term[nGramCount];

        for (int i = 0; i < nGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            List<String> wordsAsList = new ArrayList<>();

            sb.append(words[i]);
            wordsAsList.add(words[i]);

            for (int j = 1; j < n; j++) {
                sb.append(" ").append(words[i + j]);
                wordsAsList.add(words[i + j]);
            }

            nGrams[i] = new Term(null, sb.toString(), wordsAsList, TermScheme.NGRAM);
        }

        return new ArrayList<>(Arrays.asList(nGrams));
    }

    private List<Term> buildUnGrams(String lyrics, TermScheme termScheme) {

        String[] words = lyrics.split("\\s+");
        Term[] unGrams = new Term[words.length];

        for (int i = 0; i < unGrams.length; i++) {
            unGrams[i] = new Term(null, words[i], new ArrayList<>(Arrays.asList(words[i])), termScheme);
        }

        return new ArrayList<>(Arrays.asList(unGrams));
    }

    private List<Term> buildBiGrams(String lyrics, TermScheme termScheme) {
        String[] words = lyrics.split("\\s+");
        int biGramCount = words.length - 1;
        Term[] biGrams = new Term[biGramCount];

        for (int i = 0; i < biGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(words[i]).append(" ").append(words[i + 1]);

            List<String> wordsAsList = new ArrayList<>();
            wordsAsList.add(words[i]);
            wordsAsList.add(words[i + 1]);

            biGrams[i] = new Term(null, sb.toString(), wordsAsList, termScheme);
        }

        return new ArrayList<>(Arrays.asList(biGrams));
    }

    private List<Term> buildTriGrams(String lyrics, TermScheme termScheme) {
        String[] words = lyrics.split("\\s+");
        if (words.length < 2) {
            return new ArrayList<>();
        }
        int triGramCount = words.length - 2;
        Term[] triGrams = new Term[triGramCount];

        for (int i = 0; i < triGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(words[i]).append(" ").append(words[i + 1]).append(" ").append(words[i + 2]);

            List<String> wordsAsList = new ArrayList<>();
            wordsAsList.add(words[i]);
            wordsAsList.add(words[i + 1]);
            wordsAsList.add(words[i + 2]);

            triGrams[i] = new Term(null, sb.toString(), wordsAsList, termScheme);
        }

        return new ArrayList<>(Arrays.asList(triGrams));
    }

    private List<Term> buildTerms(TermScheme termScheme, String lyrics) {
        switch (termScheme) {
            case UNGRAM:
                return buildUnGrams(lyrics, termScheme);
            case BIGRAM:
                return buildBiGrams(lyrics, termScheme);
            case TRIGRAM:
                return buildTriGrams(lyrics, termScheme);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<Term> buildTerms(VectorAlgorithmConfiguration vectorAlgorithmConfiguration, Object lyrics) {

        if (!(lyrics instanceof String)) {
            throw new RuntimeException("Cannot proccess non-string parameter in StringTermBuilder");
        }

        String lyricsString = (String) lyrics;

        TermScheme termScheme = vectorAlgorithmConfiguration.getTermScheme();
        Integer termDimension = vectorAlgorithmConfiguration.getTermDimension();

        if (termDimension == null || !termScheme.isDependantOnN()) {
            return buildTerms(termScheme, lyricsString);
        }

        switch (termScheme) {
            case NGRAM:
                return buildNGrams(lyricsString, termDimension);
            default:
                return new ArrayList<>();
        }
    }
}
