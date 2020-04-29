package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory.INSTANCE;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.TermComparisonAlgorithm;


/**
 * Runs the algorithm for one AlgorithmComfiguration and returns LyricAlgorithmResult.
 */
public class VectorAlgorithmComputer implements IAlgorithmComputer {

    private static final Long NULL_ID = null;

    @Override
    public LyricAlgorithmResult computeSimilarity(VectorAlgorithmConfiguration vectorConfig, Song song) {

        //<editor-fold desc="Preparation of song to be compared">
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

            terms = termBuilder.buildTerms(vectorConfig, song.getCleanLyrics());

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
                    vectorConfig.getVectorInclusion().get(0));

            double similarity = vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(),
                    vectorPair);

            songToSimilarityPercentage.put(vectorB.getSongId(), similarity);
        }

        LyricAlgorithmResult result = new LyricAlgorithmResult();

        result.setSongToSimilarityPercentage(songToSimilarityPercentage);
        result.setVectorAlgorithmConfiguration(vectorConfig);
        result.setVectorSong(new VectorSong(vectorA));

        return result;
    }


    @Override
    public List<LyricAlgorithmResult> computeSimilarity(VectorAlgorithmConfiguration vectorConfig, List<Song> songs) {
        List<LyricAlgorithmResult> results = new ArrayList<>();
        for (Song song : songs) {
            LyricAlgorithmResult result = computeSimilarity(vectorConfig, song);
            results.add(result);

            System.out.println("Song id: " + result.getVectorSong().getSongId());
            System.out.println("Similarities: " + result.getSongToSimilarityPercentage().toString());
        }
        return results;
    }

    @Override
    public LyricAlgorithmResult computeSimilarityAndSave(VectorAlgorithmConfiguration vectorConfig, Song song) {
        song = saveSong(vectorConfig, song);

        return computeSimilarity(vectorConfig, song);
    }

    @Override
    public List<LyricAlgorithmResult> computeSimilarityAndSave(VectorAlgorithmConfiguration vectorConfig, List<Song> songs) {
        // TODO: save and refresh weights first

        songs = saveSongs(vectorConfig, songs);

        System.out.println("VectorAlgorithmComputer.computeSimilarityAndSave songs built weighted and saved" +
                " Time:" + System.currentTimeMillis());


        List<LyricAlgorithmResult> lyricAlgorithmResults = computeSimilarity(vectorConfig, songs);

        return lyricAlgorithmResults;
    }

    private Song saveSong(VectorAlgorithmConfiguration vectorConfig, Song song) {

        ITermService termService = INSTANCE.getTermService();
        ITermGroupService termGroupService = INSTANCE.getTermGroupService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();

        IWeightService weightCalculator = ServiceFactory.INSTANCE.getWeightCalculator();
        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        song = songService.initAndSave(song);

        List<Term> terms = termService.buildAndSync(song.getCleanLyrics(), vectorConfig);

        List<WeightedTermGroup> frequencyTermGroups = termGroupService.syncInitAndSaveTermGroups(terms, vectorConfig, tolerance);

        WeightedVector weightedVector = weightCalculator.calculateNewWeightedVector(song.getId(), frequencyTermGroups, vectorConfig);
        weightCalculator.saveOrEdit(weightedVector);

        return song;
    }

    private List<Song> saveSongs(VectorAlgorithmConfiguration vectorConfig, List<Song> songs) {

        ITermService termService = INSTANCE.getTermService();
        ITermGroupService termGroupService = INSTANCE.getTermGroupService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();

        IWeightService weightCalculator = ServiceFactory.INSTANCE.getWeightCalculator();
        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        List<Song> savedSongs = new ArrayList<>();

        for (Song song : songs) {
            savedSongs.add(songService.initAndSave(song));
        }

        Map<Song, List<Term>> songToTerms = new HashMap<>();

        for (Song song : savedSongs) {
            List<Term> terms = termService.buildAndSync(song.getCleanLyrics(), vectorConfig);

            songToTerms.put(song, terms);

            System.out.println("VectorAlgorithmComputer.saveSongs Song " + song.getId() + " built into terms, Time: " + System.currentTimeMillis() / 1000 + " sec");

            // save and merge groups for the first time
            termGroupService.syncInitAndSaveTermGroups(terms, vectorConfig, tolerance);
            System.out.println("VectorAlgorithmComputer.saveSongs Term groups built, Time: " + System.currentTimeMillis() / 1000 + " sec");
        }

        TermWeightType frequencyWeight = TermWeightType.getFrequencyWeight();

        // init NAIVE weights and other weights
        songToTerms.forEach((song, terms) -> {
            // get actual term groups after initialization of all songs
            List<WeightedTermGroup> frequencyWeightedGroups =
                    termGroupService.syncInitAndSaveTermGroups(terms, vectorConfig, tolerance);

            frequencyWeightedGroups.forEach(wtg -> {
                wtg.setTermWeightType(frequencyWeight);
                wtg.setSongId(song.getId());
            });

            weightCalculator.saveOrEditExcludingTermGroup(new WeightedVector(song.getId(), frequencyWeightedGroups));

            System.out.println("VectorAlgorithmComputer.saveSongs Saved naive weights for Song "
                    + song.getId() + ", Time: " + System.currentTimeMillis() / 1000 + " sec");

            WeightedVector weightedVector = weightCalculator.calculateNewWeightedVector(
                    song.getId(), frequencyWeightedGroups, vectorConfig);
            weightCalculator.saveOrEditExcludingTermGroup(weightedVector);

            System.out.println("VectorAlgorithmComputer.saveSongs Saved nontrivial weights for Song "
                    + song.getId() + ", Time: " + System.currentTimeMillis() / 1000 + " sec");
        });

        return savedSongs;
    }
}


