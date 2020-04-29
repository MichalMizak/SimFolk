package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.DummyVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MethodCemetery {


    /**
     * A utility method designated to output tables for the thesis article
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
                tolerance, termComparator, vectorConfig.getVectorInclusion().get(0));

        System.out.println("formatted first vector");

        WeightedVectorPair weightedVectorPair2 = termVectorFormatter.formVectors(song66Vector, song52Vector,
                vectorConfig.getTermComparisonAlgorithm(),
                tolerance, termComparator, vectorConfig.getVectorInclusion().get(0));

        System.out.println(song52Vector.getVector().size());
        int rowsPerPage = Math.min(Math.min(song52Vector.getVector().size(), 28), song66Vector.getVector().size());
        for (int i = 0; i < rowsPerPage; i++) {
            System.out.println(song52Vector.pairAtIndexToString(i) + "\\\\ \\hline");
        }
        System.out.println("brejk");
        for (int i = 0; i < rowsPerPage; i++) {
            System.out.println(song66Vector.pairAtIndexToString(i) + "\\\\ \\hline");
        }

        rowsPerPage = Math.min(Math.min(weightedVectorPair1.getA().getVector().size(), 28), weightedVectorPair2.getA().getVector().size());


        for (int i = 0; i < weightedVectorPair1.getA().getVector().size(); i++) {
            System.out.print(weightedVectorPair1.getA().pairAtIndexToString(i) + " & ");
            System.out.println(weightedVectorPair1.getB().pairAtIndexToString(i) + "\\\\ \\hline");
        }

        for (int i = 0; i < weightedVectorPair2.getA().getVector().size(); i++) {
            System.out.print(weightedVectorPair2.getA().pairAtIndexToString(i) + " & ");
            System.out.println(weightedVectorPair2.getB().pairAtIndexToString(i) + "\\\\ \\hline");
        }

        IVectorComparator vectorComparator = ServiceFactory.INSTANCE.getVectorComparator();

        System.out.println("Pair 1 similarity: " + vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(), weightedVectorPair1));
        System.out.println("Pair 2 similarity: " + vectorComparator.calculateSimilarity(vectorConfig.getVectorComparisonAlgorithm(), weightedVectorPair2));

    }
      /* GraphDatabaseService graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(new File(""));
        registerShutdownHook(graphDatabaseService);
        graphDatabaseService.shutdown();

        GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector("0");

        GraphDatabaseService graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File("dblocation"))
                .setConfig(bolt.type, "BOLT")
                .setConfig(bolt.enabled,"BOLT" )
                .setConfig(bolt.address,"true" )
                .newGraphDatabase();*/


//        ​GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector("0");
//       ​GraphDatabaseService graphDb = new GraphDatabaseFactory()
//               ​.newEmbeddedDatabaseBuilder(DB_PATH)
//               ​.setConfig(bolt.type, "BOLT")
//               ​.setConfig(bolt.enabled, "true")
//               ​.setConfig(bolt.address, "localhost:7687")
//               ​.newGraphDatabase();


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

        for (MusicAlgorithmResult result : results) {

            sb.append("Song id: ")
                    .append(result.getVectorSong().getSongId())
                    .append("\n")
                    .append("Similarities: ")
                    //                .append(result.getSongToSimilarityPercentage().toString())
                    .append("\n");

            System.out.println("Song id: " + result.getVectorSong().getSongId());
            //      System.out.println("Similarities: " + result.getSongToSimilarityPercentage().toString());
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
}
