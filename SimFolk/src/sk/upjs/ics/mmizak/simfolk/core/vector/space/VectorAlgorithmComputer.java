package sk.upjs.ics.mmizak.simfolk.core.vector.space;

import sk.upjs.ics.mmizak.simfolk.core.IAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ITermService;
import sk.upjs.ics.mmizak.simfolk.core.utilities.UtilityFactory;
import sk.upjs.ics.mmizak.simfolk.core.utilities.interfaces.*;
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

    @Override
    public VectorAlgorithmResult computeSimilarity(AlgorithmConfiguration algorithmConfiguration, Song song) throws Exception {

        //<editor-fold desc="Preparation of song to be compared">
        if (!algorithmConfiguration.getClass().equals(VectorAlgorithmConfiguration.class)) {
            throw new Exception("Invalid AlgorithmConfiguration");
        }

        VectorAlgorithmConfiguration vectorConfig = (VectorAlgorithmConfiguration) algorithmConfiguration;

        // dependencies initiation
        ITermBuilder termBuilder = INSTANCE.getTermBuilder();
        ITermService termService = INSTANCE.getTermService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();
        ITermComparator termComparator = INSTANCE.getTermComparator();
        IWeightService weightCalculator = UtilityFactory.INSTANCE.getWeightCalculator();
        ITermVectorFormatter termVectorFormatter = INSTANCE.getTermVectorFormatter();
        IVectorComparator vectorComparator = INSTANCE.getVectorComparator();

        // local variables for readability
        TermWeightType termWeightType = vectorConfig.getTermWeightType();
        TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();

        // calculate numeric value of tolerance
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        List<Term> terms;
        WeightedVector vectorA;

        if (song.getId() == null) {
            terms = termBuilder.buildTerms(vectorConfig.getTermScheme(),
                    vectorConfig.getTermDimension(), song.getLyrics());

            // assigns database ids to existing terms
            terms = termService.syncTerms(terms);

            vectorA = weightCalculator.calculateNewWeightedVector(terms, termWeightType, song.getId(),
                    termComparisonAlgorithm, tolerance,
                    termComparator);
        } else {
            vectorA = weightCalculator.getWeightedTermVectorBySongId(song.getId(), termWeightType,
                    termComparisonAlgorithm, tolerance);
        }
        //</editor-fold>

        List<WeightedVector> allWeightedVectors = weightCalculator.getAllWeightedTermVectors(termWeightType,
                termComparisonAlgorithm, tolerance);

        Map<Integer, Double> songToSimilarityPercentage = new HashMap<>();

        for (WeightedVector vectorB : allWeightedVectors) {

            WeightedVectorPair vectorPair = termVectorFormatter.formVectors(vectorA, vectorB,
                    termComparisonAlgorithm,
                    tolerance, termComparator,
                    vectorConfig.getVectorInclusion());

            double similarity = vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(),
                    vectorPair);

            songToSimilarityPercentage.put(vectorB.getSongId(), similarity);
        }

        VectorAlgorithmResult result = new VectorAlgorithmResult();

        result.setSongToSimilarityPercentage(songToSimilarityPercentage);
        result.setVectorAlgorithmConfiguration(vectorConfig);
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

        return computeSimilarity(algorithmConfiguration, song);
    }

    @Override
    public List<VectorAlgorithmResult> computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception {
        // TODO: save and refresh weights first

        List<VectorAlgorithmResult> vectorAlgorithmResults = computeSimilarity(algorithmConfiguration, songs);

        return vectorAlgorithmResults;
    }
}


