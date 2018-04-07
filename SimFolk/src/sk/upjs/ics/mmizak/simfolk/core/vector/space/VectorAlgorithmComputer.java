package sk.upjs.ics.mmizak.simfolk.core.vector.space;

import sk.upjs.ics.mmizak.simfolk.core.IAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.database.access.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sk.upjs.ics.mmizak.simfolk.core.database.access.ServiceFactory.INSTANCE;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;


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
        ILyricCleaner lyricCleaner = INSTANCE.getLyricCleaner();
        ITermBuilder termBuilder = INSTANCE.getTermBuilder();
        ITermService termService = INSTANCE.getTermService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();
        ITermComparator termComparator = INSTANCE.getTermComparator();
        ITermGroupService termGroupService = INSTANCE.getTermGroupService();
        IWeightService weightCalculator = ServiceFactory.INSTANCE.getWeightCalculator();
        ITermVectorFormatter termVectorFormatter = INSTANCE.getTermVectorFormatter();
        IVectorComparator vectorComparator = INSTANCE.getVectorComparator();

        // local variables for readability
        TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();

        // calculate numeric value of tolerance
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        List<Term> terms;
        WeightedVector vectorA;

        if (song.getId() == null) {
            song = lyricCleaner.clean(song);

            terms = termBuilder.buildTerms(vectorConfig.getTermScheme(),
                    vectorConfig.getTermDimension(), song.getCleanLyrics());

            // assigns database ids to existing terms
            terms = termService.syncTermIds(terms);

            List<WeightedTermGroup> frequencyTermGroups = termGroupService.syncAndInitTermGroups(terms, vectorConfig, tolerance);

            vectorA = weightCalculator.calculateNewWeightedVector(song.getId(), frequencyTermGroups, vectorConfig);
        } else {
            vectorA = weightCalculator.getFittingWeightedTermVectorBySongId(song.getId(), vectorConfig, tolerance);
        }
        //</editor-fold>

        List<WeightedVector> fittingWeightedVectors = weightCalculator.getAllFittingWeightedVectors(vectorConfig, tolerance);

        Map<Long, Double> songToSimilarityPercentage = new HashMap<>();

        for (WeightedVector vectorB : fittingWeightedVectors) {

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
        if (!algorithmConfiguration.getClass().equals(VectorAlgorithmConfiguration.class)) {
            throw new Exception("Invalid AlgorithmConfiguration");
        }

        song = saveSong((VectorAlgorithmConfiguration) algorithmConfiguration, song);

        return computeSimilarity(algorithmConfiguration, song);
    }

    private Song saveSong(VectorAlgorithmConfiguration algorithmConfiguration, Song song) {

        ITermService termService = INSTANCE.getTermService();
        ITermGroupService termGroupService = INSTANCE.getTermGroupService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();

        IWeightService weightCalculator = ServiceFactory.INSTANCE.getWeightCalculator();
        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        VectorAlgorithmConfiguration vectorConfig = algorithmConfiguration;
        TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);


        song =  songService.initAndSave(song);

        List<Term> terms = termService.buildAndSync(song, vectorConfig);

        List<WeightedTermGroup> frequencyTermGroups = termGroupService.syncInitAndSaveTermGroups(terms, vectorConfig, tolerance);

        WeightedVector weightedVector = weightCalculator.calculateNewWeightedVector(song.getId(), frequencyTermGroups, vectorConfig);
        weightCalculator.saveOrEdit(weightedVector);

        return song;
    }

    @Override
    public List<VectorAlgorithmResult> computeSimilarityAndSave(AlgorithmConfiguration algorithmConfiguration, List<Song> songs) throws Exception {
        // TODO: save and refresh weights first

        List<VectorAlgorithmResult> vectorAlgorithmResults = computeSimilarity(algorithmConfiguration, songs);

        return vectorAlgorithmResults;
    }
}


