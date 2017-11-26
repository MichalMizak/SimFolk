package sk.upjs.ics.mmizak.simfolk.core.vector.space;

import sk.upjs.ics.mmizak.simfolk.core.IAlgorithmRunner;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.IVectorComparator;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.utilities.UtilityFactory.INSTANCE;

public class VectorAlgorithmRunner implements IAlgorithmRunner {

    private VectorAlgorithmConfiguration algorithmConfiguration;

    @Override
    public AlgorithmResult computeSimilarity(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception {
        AlgorithmResult result = new AlgorithmResult();

        if (!algorithmConfiguration.getClass().equals(VectorAlgorithmConfiguration.class)) {
            throw new Exception("Invalid AlgorithmConfiguration");
        }

        this.algorithmConfiguration = (VectorAlgorithmConfiguration) algorithmConfiguration;

        ITermBuilder termBuilder = INSTANCE.getTermBuilder();

        List<Term> terms = termBuilder.buildTerms(this.algorithmConfiguration.getTermScheme(),
                this.algorithmConfiguration.getTermDimension(), song.getLyrics());

        // TODO: weighting @VectorAlgorithmRunner

        ITermVectorFormatter termVectorFormatter = INSTANCE.getTermVectorFormatter();

        IVectorComparator vectorComparator = INSTANCE.getVectorComparator();



        return result;
    }

    @Override
    public List<AlgorithmResult> computeSimilarity(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception {
        List<AlgorithmResult> results = new ArrayList<>();
        for (Song song : songs) {
            results.add(computeSimilarity(algorithmConfiguration, song));
        }
        return results;
    }

    @Override
    public AlgorithmResult computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception {
        AlgorithmResult result = computeSimilarity(algorithmConfiguration, song);

        // TODO: save

        return result;
    }

    @Override
    public List<AlgorithmResult> computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception {
        // TODO: refresh weights
        List<AlgorithmResult> algorithmResults = computeSimilarity(algorithmConfiguration, songs);

        // TODO: save

        return algorithmResults;
    }
}
