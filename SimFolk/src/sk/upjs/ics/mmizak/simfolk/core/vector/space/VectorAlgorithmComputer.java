package sk.upjs.ics.mmizak.simfolk.core.vector.space;

import sk.upjs.ics.mmizak.simfolk.core.IAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermService;
import sk.upjs.ics.mmizak.simfolk.core.utilities.UtilityFactory;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermBuilder;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermComparator;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.ITermVectorFormatter;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.IVectorComparator;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.IWeightService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sk.upjs.ics.mmizak.simfolk.core.utilities.UtilityFactory.INSTANCE;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;


/**
 * Runs the algorithm for one AlgorithmComfiguration and returns VectorAlgorithmResult.
 */
public class VectorAlgorithmComputer implements IAlgorithmComputer {

    private VectorAlgorithmConfiguration vectorAlgorithmConfiguration;

    @Override
    public VectorAlgorithmResult computeSimilarity(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception {

        //<editor-fold desc="Preparation of song to be compared">
        if (!algorithmConfiguration.getClass().equals(VectorAlgorithmConfiguration.class)) {
            throw new Exception("Invalid AlgorithmConfiguration");
        }

        vectorAlgorithmConfiguration = (VectorAlgorithmConfiguration) algorithmConfiguration;

        // dependencies initiation
        ITermBuilder termBuilder = INSTANCE.getTermBuilder();
        ITermService termService = INSTANCE.getTermService();
        ITermComparator termComparator = INSTANCE.getTermComparator();
        IWeightService weightCalculator = UtilityFactory.INSTANCE.getWeightCalculator();
        ITermVectorFormatter termVectorFormatter = INSTANCE.getTermVectorFormatter();
        IVectorComparator vectorComparator = INSTANCE.getVectorComparator();


        List<Term> terms = termBuilder.buildTerms(vectorAlgorithmConfiguration.getTermScheme(),
                vectorAlgorithmConfiguration.getTermDimension(), song.getLyrics());

        // assigns database ids to existing terms
        terms = termService.syncTerms(terms);

        TermWeightType termWeightType = vectorAlgorithmConfiguration.getTermWeightType();

        // WARNING: songId is null for unsaved songs!
        WeightedVector vectorA = weightCalculator.calculateWeightedTermVector(terms, termWeightType, song.getId(),
                vectorAlgorithmConfiguration.getTermComparisonAlgorithm(), vectorAlgorithmConfiguration.getTolerance(),
                termComparator);
        //</editor-fold>

        List<WeightedVector> allWeightedVectors = weightCalculator.getAllWeightedTermVectors(termWeightType,
                vectorAlgorithmConfiguration.getTermComparisonAlgorithm(), vectorAlgorithmConfiguration.getTolerance());

        Map<Long, Double> songToSimilarityPercentage = new HashMap<>();

        for (WeightedVector vectorB : allWeightedVectors) {

            WeightedVectorPair vectorPair = termVectorFormatter.formVectors(vectorA, vectorB,
                    vectorAlgorithmConfiguration.getTermComparisonAlgorithm(),
                    vectorAlgorithmConfiguration.getTolerance(), termComparator,
                    vectorAlgorithmConfiguration.getVectorInclusion());

            double similarity = vectorComparator.calculateSimilarity(vectorAlgorithmConfiguration.getVectorComparisonAlgorithm(),
                    vectorPair);

            songToSimilarityPercentage.put(vectorB.getSongId(), similarity);
        }

        VectorAlgorithmResult result = new VectorAlgorithmResult();

        result.setSongToSimilarityPercentage(songToSimilarityPercentage);
        result.setVectorAlgorithmConfiguration(this.vectorAlgorithmConfiguration);
        result.setVectorSong(new VectorSong(vectorA));

        return result;
    }

    @Override
    public List<VectorAlgorithmResult> computeSimilarity(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception {
        List<VectorAlgorithmResult> results = new ArrayList<>();
        for (Song song : songs) {
            results.add(computeSimilarity(algorithmConfiguration, song));
        }
        return results;
    }

    @Override
    public VectorAlgorithmResult computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception {
        // TODO: save and refresh weights first

        VectorAlgorithmResult result = computeSimilarity(algorithmConfiguration, song);

        return result;
    }

    @Override
    public List<VectorAlgorithmResult> computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception {
        // TODO: save and refresh weights first

        List<VectorAlgorithmResult> vectorAlgorithmResults = computeSimilarity(algorithmConfiguration, songs);

        return vectorAlgorithmResults;
    }
}


