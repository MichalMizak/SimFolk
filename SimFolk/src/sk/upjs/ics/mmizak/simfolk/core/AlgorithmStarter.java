package sk.upjs.ics.mmizak.simfolk.core;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.services.MelodyResultAggregatorService;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.Neo4jConfig;
import sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.DummyVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.MusicAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.MusicVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.services.implementations.VectorAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.*;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVector;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedVectorPair;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;
import sk.upjs.ics.mmizak.simfolk.melody.MusicDataProvider;
import sk.upjs.ics.mmizak.simfolk.parsing.LyricParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Basic starter of algorithm without UI.
 * Starter generates/ chooses the algorithm configurations, gets songs to be compared
 * (for now directly from code) and runs the algorithm with or without saving.
 * Starter also handles the LyricAlgorithmResult.
 */
public class AlgorithmStarter {

    /*
     * In order to change the algorithm configuration,
     * go to .core.services.implementations.DummyVectorAlgorithmConfigurationService
     * and change values in the builder in the method generateRandomConfiguration()
     */

    /**
     * Configure the number of songs to be compared.
     * In order to input your own songs you need to modify the method getSongs to return your desired song.
     */
    public static final int SONGS_TO_COMPARE_COUNT = 500;

    /**
     * Configure whether to save songs or not. Note the algorithm isn't 100% precise with this set to false
     * for performance reasons.
     */
    public static final boolean SAVE_SONGS = true;


    public static final AlgorithmConfiguration.AlgorithmType ALGORITHM_TYPE = AlgorithmConfiguration.AlgorithmType.MUSIC;

    public static final String MUSICXML_RESOURCE_DIRECTORY =
            "C:\\UPJŠ\\Bakalárska práca\\SimFolk\\SimFolk\\src\\sk\\upjs\\ics\\mmizak\\simfolk\\melody\\resources";

    private static void registerShutdownHook(GraphDatabaseService graphDatabaseService) {
        Runtime.getRuntime().addShutdownHook(new Thread(graphDatabaseService::shutdown));
    }


    public static void main(String[] args) {

        AlgorithmStarter algorithmStarter = new AlgorithmStarter();
        algorithmStarter.compute();

        // thesisOutput();
    }

    //<editor-fold desc="Non-simfolk-related">
    public static void nextLesserElement(int[] a) {
        //int[] a = {40, 50, 11, 32, 55, 68, 75};
        //        nextLesserElement(a);

        Stack<Integer> s = new Stack<Integer>();
        Stack<Integer> indices = new Stack<>();

        int[] result = new int[a.length];

        s.push(a[a.length - 1]);
        indices.push(a.length - 1);

        for (int i = a.length - 2; i >= 0; i--) {
            if (!s.isEmpty()) {
                while (!s.isEmpty()) {
                    if (s.peek() < a[i]) {
                        break;
                    }
                    s.pop();
                    result[indices.pop()] = i;
                }
            }
            s.push(a[i]);
            indices.push(i);
        }
        while (!s.isEmpty()) {
            s.pop();
            result[indices.pop()] = 0;
        }
    }
    //</editor-fold>

    public void compute() {

        DummyVectorAlgorithmConfigurationService dummyVectorConfigurationGenerator =
                new DummyVectorAlgorithmConfigurationService();

        VectorAlgorithmConfiguration vectorAlgorithmConfiguration = dummyVectorConfigurationGenerator.generateRandomConfiguration();

        //List<VectorAlgorithmConfiguration> allConfigurations = dummyVectorConfigurationGenerator.loadAllConfigurations();
        IVectorAlgorithmConfigurationService musicConfService = new MusicVectorAlgorithmConfigurationService();

        List<VectorAlgorithmConfiguration> allConfigurations = musicConfService.loadAllConfigurations();

        if (ALGORITHM_TYPE == AlgorithmConfiguration.AlgorithmType.MUSIC) {

            IMusicAlgorithmComputer algorithmComputer = new MusicAlgorithmComputer();

            List<MelodySong> melodySongs = new MusicDataProvider().getMelodySongs();

            if (SAVE_SONGS) {
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Neo4jConfig.class);
                MelodyResultAggregatorService resultAggregator = context.getBean(MelodyResultAggregatorService.class);

                resultAggregator.init(melodySongs);

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                allConfigurations.forEach(vac -> {
                    List<MusicAlgorithmResult> oneConfigurationResults = algorithmComputer.computeMusicSimilarityAndSave(vac, melodySongs);

                    resultAggregator.addResult(vac, oneConfigurationResults);
                    executorService.execute(resultAggregator);
                });

                executorService.execute(context::close);
                executorService.shutdown();
            } else {
                algorithmComputer.computeMusicSimilarity(vectorAlgorithmConfiguration, melodySongs);
            }
        } else {
            IAlgorithmComputer algorithmComputer = new VectorAlgorithmComputer();
            List<Song> songs = getSongs();

            // TODO: For progress send an object to the algorithm computer

            if (SAVE_SONGS)
                algorithmComputer.computeSimilarityAndSave(vectorAlgorithmConfiguration, songs);
            else
                algorithmComputer.computeSimilarity(vectorAlgorithmConfiguration, songs);

            // TODO: Handle result
        }
    }



    public static void getAndSaveSongs(ISongService songService) {
        List<Song> songs = getSongs();

        songService.saveOrEdit(songs);
    }

    private static List<Song> getSongs() {

        int songsToCompare = SONGS_TO_COMPARE_COUNT;

        List<Song> songs = new ArrayList<>();
        if (SONGS_TO_COMPARE_COUNT == 0) {
            return songs;
        }

        Song modifiedSong = getModifiedSong();
        songs.add(modifiedSong);

        // -1 because of adding modifiedSong
        songsToCompare--;

        LyricParser lyricParser = new LyricParser();
        List<Song> viktor = lyricParser.parseViktor();

        int count = Math.min(viktor.size(), songsToCompare);

        for (int i = 0; i < count; i++) {
            songs.add(viktor.get(i));
        }

        songsToCompare -= viktor.size();
        if (songsToCompare <= 0) {
            return songs;
        }

        List<Song> piesne372 = lyricParser.parsePiesne372();

        count = Math.min(piesne372.size(), songsToCompare);

        for (int i = 0; i < count; i++) {
            songs.add(piesne372.get(i));
        }

        songsToCompare -= piesne372.size();


        return songs;
    }

    private static Song getModifiedSong() {
        Song modifiedSong = new Song();
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
        return modifiedSong;
    }


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

}


