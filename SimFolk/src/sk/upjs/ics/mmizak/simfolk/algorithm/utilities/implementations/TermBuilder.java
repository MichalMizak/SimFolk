package sk.upjs.ics.mmizak.simfolk.algorithm.utilities.implementations;

import sk.upjs.ics.mmizak.simfolk.algorithm.containers.term.schemes.Term;
import sk.upjs.ics.mmizak.simfolk.algorithm.utilities.interfaces.ITermBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Test
public class TermBuilder implements ITermBuilder {

    @Override
    public List<Term> buildNGrams(String lyrics, int n) {
        switch (n) {
            case 1:
                return buildUnGrams(lyrics);
            case 2:
                return buildBiGrams(lyrics);
            case 3:
                return buildTriGrams(lyrics);

            // TODO: Implement
            default:
                return null;
        }
    }

    @Override
    public List<Term> buildUnGrams(String lyrics) {

        String[] words = lyrics.split("\\s+");
        Term[] unGrams = new Term[words.length];

        for (int i = 0; i < unGrams.length; i++) {
            unGrams[i] = new Term(words[i], Arrays.asList(words));
        }

        return Arrays.asList(unGrams);
    }

    @Override
    public List<Term> buildBiGrams(String lyrics) {
        String[] words = lyrics.split("\\s+");
        int biGramCount = words.length - 1;
        Term[] biGrams = new Term[biGramCount];

        for (int i = 0; i < biGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(words[i]).append(" ").append(words[i + 1]);

            List<String> wordsAsList = new ArrayList<>();
            wordsAsList.add(words[i]);
            wordsAsList.add(words[i + 1]);

            biGrams[i] = new Term(sb.toString(), wordsAsList);
        }

        return Arrays.asList(biGrams);
    }

    @Override
    public List<Term> buildTriGrams(String lyrics) {
        String[] words = lyrics.split("\\s+");
        int triGramCount = words.length - 2;
        Term[] triGrams = new Term[triGramCount];

        for (int i = 0; i < triGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(words[i]).append(" ").append(words[i + 1]).append(" ").append(words[i + 2]);

            List<String> wordsAsList = new ArrayList<>();
            wordsAsList.add(words[i]);
            wordsAsList.add(words[i + 1]);
            wordsAsList.add(words[i + 2]);

            triGrams[i] = new Term(sb.toString(), wordsAsList);
        }

        return Arrays.asList(triGrams);
    }
}
