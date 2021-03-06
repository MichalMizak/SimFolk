package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.utilities.CollectionsUtilities;
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
import java.util.*;
import java.util.concurrent.RecursiveTask;

import static sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory.INSTANCE;

public class MusicAlgorithmComputer implements IMusicAlgorithmComputer {
    @Override
    public List<MusicAlgorithmResult> computeMusicSimilarity(VectorAlgorithmConfiguration vectorConfig, List<MelodySong> melodySongs) {
        //<editor-fold desc="Preparation of song to be compared">
        // dependencies initiation
        // Music specific services
        ITermBuilder termBuilder = INSTANCE.getTermBuilder();


        ITermService termService = INSTANCE.getTermService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();
        ITermComparator termComparator = INSTANCE.getTermComparator();
        ITermGroupService termGroupService = INSTANCE.getTermGroupService();
        IWeightService weightCalculator = ServiceFactory.INSTANCE.getWeightCalculator();
        ITermVectorFormatter termVectorFormatter = INSTANCE.getTermVectorFormatter();
        IVectorComparator vectorComparator = INSTANCE.getVectorComparator();

        // local variables for readability
        AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();

        // calculate numeric value of tolerance
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        List<WeightedVector> musicVectors = new ArrayList<>();

        for (MelodySong melodySong : melodySongs) {

            // transforms measures into terms
            List<Term> terms = termBuilder.buildTerms(vectorConfig, melodySong);

            // assigns database ids to existing terms
            terms = termService.syncTermIds(terms);

            List<WeightedTermGroup> frequencyTermGroups = termGroupService.syncAndInitTermGroups(terms, vectorConfig, tolerance);

            WeightedVector vectorA = weightCalculator.calculateNewWeightedVector(melodySong.getId(), frequencyTermGroups, vectorConfig);
            musicVectors.add(vectorA);
        }

        List<MusicAlgorithmResult> results = new ArrayList<>();

        // sort vectors for performance
        musicVectors.forEach(WeightedVector::sort);

        for (int i = 0; i < musicVectors.size(); i++) {

            Map<AlgorithmConfiguration.VectorInclusion, Map<MelodySong, Double>> inclusionToResult = new HashMap<>();

            for (AlgorithmConfiguration.VectorInclusion vectorInclusion : vectorConfig.getVectorInclusion()) {
                inclusionToResult.put(vectorInclusion, new HashMap<>());
            }

            WeightedVector vectorA = musicVectors.get(i);

            for (int j = i + 1; j < musicVectors.size(); j++) {
                WeightedVector vectorB = musicVectors.get(j);

                for (AlgorithmConfiguration.VectorInclusion vectorInclusion : vectorConfig.getVectorInclusion()) {
                    WeightedVectorPair vectorPair = termVectorFormatter.formVectors(vectorA, vectorB,
                            termComparisonAlgorithm,
                            tolerance, termComparator,
                            vectorInclusion);

                    double similarity = vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(),
                            vectorPair);

                    // TODO: for god's sake make this prettier
                    //melodyToSimilarityPercentage.put((long) j + 1, similarity);
                    // melody songs are sorted the same way as musicVectors
                    inclusionToResult.get(vectorInclusion).put(melodySongs.get(j), similarity);
                    //  melodyToVectorPair.put(melodySongs.get(j), vectorPair);
                }


            }
            MusicAlgorithmResult result = new MusicAlgorithmResult();
            result.setMelodySong(melodySongs.get(i));

            result.setInclusionToSongToSimilarityPercentage(inclusionToResult);
            // setSongToSimilarityPercentage(melodyToSimilarityPercentage);
            result.setVectorAlgorithmConfiguration(vectorConfig);
            result.setVectorSong(new VectorSong(vectorA));

            results.add(result);
        }

        return results;

        //</editor-fold>
    }


    /**
     * Prepare database for one configuration calculation
     *
     * @param vectorConfig
     * @param melodySongs
     * @return
     */
    @Override
    public List<MusicAlgorithmResult> computeMusicSimilarityAndSave(VectorAlgorithmConfiguration vectorConfig, List<MelodySong> melodySongs) {
        // optimize it = get list of all vectorConfigs so that nothing needs to be calculated twice.
        ITermService termService = INSTANCE.getTermService();
        ITermGroupService termGroupService = INSTANCE.getTermGroupService();
        IToleranceCalculator toleranceCalculator = INSTANCE.getToleranceCalculator();

        IWeightService weightCalculator = ServiceFactory.INSTANCE.getWeightCalculator();

        Map<MelodySong, List<Term>> songToTerms = new HashMap<>();

        for (MelodySong melodySong : melodySongs) {
            List<Term> terms = termService.buildAndSync(melodySong, vectorConfig);

            songToTerms.put(melodySong, terms);
        }

        List<Term> allTerms = new ArrayList<>();
        songToTerms.values().forEach(allTerms::addAll);

        AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        // save and merge groups for the first time
        List<WeightedTermGroup> allTermGroups = termGroupService.syncInitAndSaveTermGroups(allTerms, vectorConfig, tolerance);

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

            WeightedVector weightedVector = weightCalculator.calculateNewWeightedVector(
                    song.getId(), frequencyWeightedGroups, vectorConfig);
            weightCalculator.saveOrEditExcludingTermGroup(weightedVector);
        });

        return computeMusicSimilarity(vectorConfig, melodySongs);
    }
}
