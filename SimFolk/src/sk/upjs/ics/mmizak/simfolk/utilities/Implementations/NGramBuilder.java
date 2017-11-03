package sk.upjs.ics.mmizak.simfolk.utilities.Implementations;

import sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations.BiGram;
import sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations.TriGram;
import sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations.UnGram;
import sk.upjs.ics.mmizak.simfolk.utilities.interfaces.INGramBuilder;

import java.util.Arrays;
import java.util.List;

// TODO: Test
public class NGramBuilder implements INGramBuilder {

    @Override
    public List<UnGram> buildUnGrams(String lyrics) {

        String[] words = lyrics.split("\\s+");
        UnGram[] unGrams = new UnGram[words.length];

        for (int i = 0; i < unGrams.length; i++) {
            unGrams[i] = new UnGram(words[i]);
        }

        return Arrays.asList(unGrams);
    }

    @Override
    public List<BiGram> buildBiGrams(String lyrics) {
        String[] words = lyrics.split("\\s+");
        int biGramCount = words.length - 1;
        BiGram[] biGrams = new BiGram[biGramCount];

        for (int i = 0; i < biGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(words[i]).append(" ").append(words[i+1]);
             biGrams[i] = new BiGram(sb.toString());
        }

        return Arrays.asList(biGrams);
    }

    @Override
    public List<TriGram> buildTriGrams(String lyrics) {
        String[] words = lyrics.split("\\s+");
        int triGramCount = words.length - 2;
        TriGram[] triGrams = new TriGram[triGramCount];

        for (int i = 0; i < triGramCount; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(words[i]).append(" ").append(words[i+1]).append(" ").append(words[i+2]);
            triGrams[i] = new TriGram(sb.toString());
        }

        return Arrays.asList(triGrams);
    }
}
