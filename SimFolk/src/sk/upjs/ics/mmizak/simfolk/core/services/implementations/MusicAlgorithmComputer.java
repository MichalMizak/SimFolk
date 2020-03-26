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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Long, Double> melodyToSimilarityPercentage = new HashMap<>();

        List<MusicAlgorithmResult> results = new ArrayList<>();

        // sort vectors for performance
        musicVectors.forEach(WeightedVector::sort);

        for (int i = 0; i < musicVectors.size(); i++) {

            WeightedVector vectorA = musicVectors.get(i);

            for (int j = i+1; j < musicVectors.size(); j++) {
                WeightedVector vectorB = musicVectors.get(j);

                WeightedVectorPair vectorPair = termVectorFormatter.formVectors(vectorA, vectorB,
                        termComparisonAlgorithm,
                        tolerance, termComparator,
                        vectorConfig.getVectorInclusion());

                double similarity = vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(),
                        vectorPair);

                // TODO: dynamic IDs from database
                melodyToSimilarityPercentage.put((long) j + 1, similarity);
            }

            MusicAlgorithmResult result = new MusicAlgorithmResult();

            result.setSongToSimilarityPercentage(melodyToSimilarityPercentage);
            result.setVectorAlgorithmConfiguration(vectorConfig);
            result.setVectorSong(new VectorSong(vectorA));

            results.add(result);
            melodyToSimilarityPercentage = new HashMap<>();
        }

        results.forEach(result -> result.setMelodySong(CollectionsUtilities.getById(melodySongs, result.getVectorSong().getSongId())));

        return results;

        //</editor-fold>
    }

    private void writeResultToFile(VectorAlgorithmConfiguration vectorConfig, List<MelodySong> melodySongs, List<MusicAlgorithmResult> results) {
        writeResultToFile(vectorConfig, melodySongs, results);

        StringBuilder sb = new StringBuilder();
        sb.append(vectorConfig.toString());

        sb.append("\n");
        for (MelodySong melodySong : melodySongs) {
            sb.append(melodySong.getId())
                    .append(" = ")
                    .append(melodySong.getMusicXML().toString());
            sb.append("\n");
        }

        File file = new File("C:\\UPJŠ\\Bakalárska práca\\SimFolk\\SimFolk\\src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\resultMap.txt");

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sb = new StringBuilder();

        for (LyricAlgorithmResult result : results) {

            sb.append("Song id: ")
                    .append(result.getVectorSong().getSongId())
                    .append("\n")
                    .append("Similarities: ")
                    .append(result.getSongToSimilarityPercentage().toString())
                    .append("\n");

            System.out.println("Song id: " + result.getVectorSong().getSongId());
            System.out.println("Similarities: " + result.getSongToSimilarityPercentage().toString());
        }

        file = new File("C:\\UPJŠ\\Bakalárska práca\\SimFolk\\SimFolk\\src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\result.txt");


        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        for (int i = 0; i < melodySongs.size(); i++) {
            melodySongs.get(i).setId((long) i + 1);
        }

        saveMelodySong(melodySongs);

        Map<MelodySong, List<Term>> songToTerms = new HashMap<>();

        for (MelodySong melodySong : melodySongs) {
//            System.out.println("Processing:");
//            System.out.println(melodySong.getMusicXML().toString());

            List<Term> terms = termService.buildAndSync(melodySong, vectorConfig);

            songToTerms.put(melodySong, terms);

//            System.out.println();
//            System.out.println("VectorAlgorithmComputer.computeMusicSimilarityAndSave MelodySong " + melodySong.getId() +
//                    " built into terms, Time: " + System.currentTimeMillis() / 1000 + " sec");
        }

        List<Term> allTerms = new ArrayList<>();
        songToTerms.values().forEach(allTerms::addAll);

        AlgorithmConfiguration.TermComparisonAlgorithm termComparisonAlgorithm = vectorConfig.getTermComparisonAlgorithm();
        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                termComparisonAlgorithm);

        //  vectorConfigs

        // save and merge groups for the first time
        List<WeightedTermGroup> allTermGroups = termGroupService.syncInitAndSaveTermGroups(allTerms, vectorConfig, tolerance);
//        System.out.println("VectorAlgorithmComputer.saveSongs all Term groups built, Time: " + System.currentTimeMillis() / 1000 + " sec");
//        System.out.println();

       //  allTermGroups.forEach(System.out::println);

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

//            System.out.println("VectorAlgorithmComputer.saveSongs Saved naive weights for Song "
//                    + song.getId() + ", Time: " + System.currentTimeMillis() / 1000 + " sec");

            WeightedVector weightedVector = weightCalculator.calculateNewWeightedVector(
                    song.getId(), frequencyWeightedGroups, vectorConfig);
            weightCalculator.saveOrEditExcludingTermGroup(weightedVector);

//            System.out.println("VectorAlgorithmComputer.saveSongs Saved nontrivial weights for Song "
//                    + song.getId() + ", Time: " + System.currentTimeMillis() / 1000 + " sec");
        });

        return computeMusicSimilarity(vectorConfig, melodySongs);

    }

    /**
     * Improvised method to save melody songs into the lyric database
     *
     * @param melodySongs
     */
    private void saveMelodySong(List<MelodySong> melodySongs) {
        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        for (MelodySong song : melodySongs) {
            Song dummySong = new Song();
            dummySong.setLyrics(song.getMusicXML().toString());
            dummySong.setCleanLyrics(dummySong.getLyrics());
            dummySong.setSource("");
            dummySong.setAttributes(new ArrayList<>());

            songService.initAndSave(dummySong);
        }
    }
}