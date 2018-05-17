package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.factories.DaoFactory;
import sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ITermWeightTypeDao;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.DummyVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.VectorAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;
import sk.upjs.ics.mmizak.simfolk.parsing.Parser;

import java.util.ArrayList;
import java.util.List;


/**
 * Basic starter of algorithm without UI.
 * Starter generates/ chooses the algorithm configurations, gets songs from somewhere
 * (for now directly from code) and runs the algorithm with or without saving
 * and handles the VectorAlgorithmResult
 */
public class AlgorithmStarter {


    public static void main(String[] args) {

        compute();

        // thesisOutput();

    }

    /**
     * A utility method designated to output
     */
    private static void thesisOutput() {
        DummyVectorAlgorithmConfigurationService dummyVectorConfigurationGenerator =
                new DummyVectorAlgorithmConfigurationService();

        VectorAlgorithmConfiguration vectorConfig =
                dummyVectorConfigurationGenerator.generateRandomConfiguration();

        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        Song song52 = songService.getById(52L);
        Song song66 = songService.getById(66L);

        System.out.println("fetched songs");

        ITermWeightTypeDao termWeightTypeDao = DaoFactory.INSTANCE.getTermWeightTypeDao();

        IWeightService termGroupService = ServiceFactory.INSTANCE.getWeightCalculator();
        IToleranceCalculator toleranceCalculator = ServiceFactory.INSTANCE.getToleranceCalculator();

        double tolerance = toleranceCalculator.calculateTolerance(vectorConfig.getTolerance(),
                vectorConfig.getTermComparisonAlgorithm());

        WeightedVector song52Vector = termGroupService.getFittingWeightedTermVectorBySongId(song52.getId(), vectorConfig, tolerance);

        System.out.println("got first fitting vector");
        WeightedVector song66Vector = termGroupService.getFittingWeightedTermVectorBySongId(song66.getId(), vectorConfig, tolerance);

        System.out.println("fetched vectors");

        ITermVectorFormatter termVectorFormatter = ServiceFactory.INSTANCE.getTermVectorFormatter();
        ITermComparator termComparator = ServiceFactory.INSTANCE.getTermComparator();


        WeightedVectorPair weightedVectorPair1 = termVectorFormatter.formVectors(song52Vector, song66Vector,
                vectorConfig.getTermComparisonAlgorithm(),
                tolerance, termComparator, vectorConfig.getVectorInclusion());

        System.out.println("formatted first vector");

        WeightedVectorPair weightedVectorPair2 = termVectorFormatter.formVectors(song66Vector, song52Vector,
                vectorConfig.getTermComparisonAlgorithm(),
                tolerance, termComparator, vectorConfig.getVectorInclusion());

        System.out.println(song52Vector.getVector().size());
        int rowsPerPage = Math.min(Math.min(song52Vector.getVector().size(), 28), song66Vector.getVector().size());
        for (int i = 0; i < rowsPerPage; i++) {
            System.out.println(song52Vector.pairAtIndexToString(i) + "\\\\ \\hline");
        }
        System.out.println("brejk");
        for (int i = 0; i < rowsPerPage; i++) {
            System.out.println(song66Vector.pairAtIndexToString(i) + "\\\\ \\hline");
        }


        // System.out.println(song52Vector);
        // System.out.println(song66Vector);

        rowsPerPage = Math.min(Math.min(weightedVectorPair1.getA().getVector().size(), 28), weightedVectorPair2.getA().getVector().size());


        for (int i = 0; i < rowsPerPage; i++) {
            System.out.print(weightedVectorPair1.getA().pairAtIndexToString(i) + " & ");
            System.out.println(weightedVectorPair1.getB().pairAtIndexToString(i) + "\\\\ \\hline");
        }
        System.out.println("brejk");
        for (int i = 0; i < rowsPerPage; i++) {
        }

        for (int i = 0; i < rowsPerPage; i++) {
            System.out.print(weightedVectorPair2.getA().pairAtIndexToString(i) + " & ");
            System.out.println(weightedVectorPair2.getB().pairAtIndexToString(i) + "\\\\ \\hline");
        }

        IVectorComparator vectorComparator = ServiceFactory.INSTANCE.getVectorComparator();

        System.out.println("Pair 1 similarity: " + vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(), weightedVectorPair1));
        System.out.println("Pair 2 similarity: " + vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(), weightedVectorPair2));

    }

    private static void compute() {
        DummyVectorAlgorithmConfigurationService dummyVectorConfigurationGenerator =
                new DummyVectorAlgorithmConfigurationService();

        VectorAlgorithmConfiguration vectorAlgorithmConfiguration =
                dummyVectorConfigurationGenerator.generateRandomConfiguration();
        IAlgorithmComputer algorithmComputer = new VectorAlgorithmComputer();

        Parser parser = new Parser();
        List<Song> viktor = parser.parseViktor();

        Song modifiedSong = new Song();

        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        saveSongs(viktor, modifiedSong, songService);

        System.out.println("AlgorithmStarter.compute: Songs saved, Time: " + System.currentTimeMillis() / 1000 + " sec");

        List<Song> all = songService.getAll();

  /*      for (Song song : all) {
            VectorAlgorithmResult result = algorithmComputer.computeSimilarity(vectorAlgorithmConfiguration, song);
            System.out.println(result.getSongToSimilarityPercentage());
        }
*/
        // TODO: For progress send an object to the algorithm computer
        List<VectorAlgorithmResult> result =
                algorithmComputer.computeSimilarityAndSave(vectorAlgorithmConfiguration, all);
        // TODO: Handle result
    }

    public static void saveSongs(List<Song> viktor, Song modifiedSong, ISongService songService) {
        modifiedSong.setLyrics("1/ Kolo nas poza nas, /2x/ dražečka do kriva,\n" +
                "pitala še me mac /2x/ keho mam frajira,\n" +
                "[:ja jej povedala,bi še ňestarala,\n" +
                "bi mi do ľubena /2x/ ňerozkazovala.:]\n" +
                "2.\tRadšej mi rozkažce /2x/ co mam doma robic,\n" +
                "a ňe do ľubeňa /2x/ koho ja mam ľubic.\n" +
                "[:Radšej mi rozkažce co mam doma robic,\n" +
                "A ne do ľubena /2x/ keho ja mam ľubic.:]\n" +
                "3.\tA či ja mamočko, /2x/ ňe vaša dzivčina,\n" +
                "že sce mi naklaľi, /2x/ do perini šena.\n" +
                "[:Do perini šena, do ladi kameňa,\n" +
                "a či ja mamočko, /2x/ňe vaša dzivčina.:]\n" +
                "4.\tEšče vi mamičko /2x/ za mnu zaplačece,\n" +
                "kedz na žeľenu jar /2x/ do poľa pujdzece,\n" +
                "[:na poľo vijdzece, še poobžirace,\n" +
                "dzivki z macerami,macere s dzivkami, vi pujdzece sami.:]\n" +
                "5.\tNaco ši mi dala, /2x/ kedz ja bul pijany.\n" +
                "Mala ši obracic, /2x/ koľenka do slamy.\n" +
                "[:Ja še obracala, slama me kusala, \n" +
                "ani sama neznam, /2x/ jak som še prespala.:]");

        modifiedSong.setTitle("Kolo nas");
        modifiedSong.setAttributes(new ArrayList<>());
        modifiedSong.setSource("sours");

        songService.saveOrEdit(modifiedSong);

        for (int i = 0; i < 1; i++) {
            songService.saveOrEdit(viktor.get(i));
        }
    }

}


