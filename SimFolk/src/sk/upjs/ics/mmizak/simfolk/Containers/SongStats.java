package sk.upjs.ics.mmizak.simfolk.Containers;

import sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations.BiGram;
import sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations.TriGram;
import sk.upjs.ics.mmizak.simfolk.Containers.NGramImplementations.UnGram;
import sk.upjs.ics.mmizak.simfolk.utilities.LyricCleaner;
import sk.upjs.ics.mmizak.simfolk.utilities.UtilityFactory;
import sk.upjs.ics.mmizak.simfolk.utilities.interfaces.INGramBuilder;

import java.util.List;
import java.util.Map;


public class SongStats {

    private long songId;

    private List<UnGram> unGrams;
    private List<BiGram> biGrams;
    private List<TriGram> triGrams;

    // TODO: Multithreading use
    private boolean wasCompared;

    // TODO: Statistics
    private Map<Long, Double> songToSimilarityPercentage;

    public SongStats(long songId, String lyrics) {
        this.songId = songId;

        lyrics = LyricCleaner.clean(lyrics);
        
        INGramBuilder nGramBuilder = UtilityFactory.getNGramBuilder();

        this.unGrams = nGramBuilder.buildUnGrams(lyrics);
        this.biGrams = nGramBuilder.buildBiGrams(lyrics);
        this.triGrams = nGramBuilder.buildTriGrams(lyrics);
    }


}
