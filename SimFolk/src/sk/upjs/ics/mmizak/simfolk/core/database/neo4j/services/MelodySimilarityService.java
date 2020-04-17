package sk.upjs.ics.mmizak.simfolk.core.database.neo4j.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.entities.NeoSimilarity;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.entities.NeoSong;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.repositories.MelodySimilarityRepository;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.repositories.MelodySongRepository;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.MusicAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.VectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MelodySimilarityService {

    private static final String AGGREGATED_RESULT = "AGGREGATED_RESULT";

    private MelodySongRepository melodySongRepository;
    private MelodySimilarityRepository melodySimilarityRepository;

    private List<MelodySong> melodySongs;
    private HashMap<MelodySong, NeoSong> partialResultSongMap = new HashMap<>();


    private Map<Long, NeoSong> aggregatedResultIdMap = new HashMap<>();
    private List<NeoSimilarity> aggregatedNeoResults = new ArrayList<>();
    private static final int MINIMUM_RESULT_SIMILARITY = 60;

    @Autowired
    public MelodySimilarityService(MelodySongRepository melodySongRepository, MelodySimilarityRepository melodySimilarityRepository) {
        this.melodySongRepository = melodySongRepository;
        this.melodySimilarityRepository = melodySimilarityRepository;
    }

    public void testFill() {
        melodySongRepository.deleteAll();
        NeoSong song1 = new NeoSong((long) 6666, "demotitle", "tittle", new ArrayList<>());
        song1.setId(6666L);
        melodySongRepository.save(song1);


        for (int i = 0; i < 20; i += 2) {
            for (int j = 0; j < 20; j++) {
                song1 = new NeoSong((long) i, "title" + i, "tittle", new ArrayList<>());
                NeoSong song2 = new NeoSong((long) i + 1, "title" + i + 1, "tittle", new ArrayList<>());

            //    NeoSimilarity similarity = new NeoSimilarity(1000 * Math.random() * j % 100, "demo" + j, song1, song2);
           //     melodySimilarityRepository.save(similarity);
            }
        }
//
        //  song1.getSimilarity().add(song2);
        //  song2.getSimilarity().add(song1);

        // song1 = melodySongRepository.save(song1);
        // song2 = melodySongRepository.save(song2);


        // Iterable<NeoSong> all = melodySongRepository.findAll();
//
//        for (NeoSong wat : all) {
//            wat.setMusicXMLFileTitle("234");
//        }
//
//        for (NeoSong neo4jMelodySong : all) {
//            melodySongRepository.save(neo4jMelodySong);
//        }

        //melodySimilarityRepository.save();

    }

    @Transactional
    public void testGet() {
        Iterable<NeoSong> all = melodySongRepository.findAll();
        all.forEach(System.out::println);
    }

    public void save(VectorAlgorithmConfiguration latestConfiguration, List<MusicAlgorithmResult> filteredResults) {
        initMap(melodySongs);
        List<NeoSimilarity> neoSimilarityList = getNeo4jMelodySimilarities(latestConfiguration,
                filteredResults);

        melodySimilarityRepository.saveAll(neoSimilarityList);
    }

    public void init(List<MelodySong> melodySongs) {
        this.melodySongs = melodySongs;
        melodySongs.forEach(s -> aggregatedResultIdMap.put(s.getId(), new NeoSong(s)));

        // TODO: history
        melodySongRepository.deleteAll();
        melodySimilarityRepository.deleteAll();
    }

    private void initMap(List<MelodySong> melodySongs) {
        partialResultSongMap.clear();
        melodySongs.forEach(s -> partialResultSongMap.put(s, new NeoSong(s)));
    }

    private List<NeoSimilarity> getNeo4jMelodySimilarities(VectorAlgorithmConfiguration latestConfigurationDescription, List<MusicAlgorithmResult> filteredResults) {
        List<NeoSimilarity> neoSimilarityList = new ArrayList<>();

        filteredResults.forEach(result -> {
            MelodySong melodySongA = result.getMelodySong();
            result.getSongToSimilarityPercentage().forEach((melodySongB, value) ->
                    neoSimilarityList.add(
                            new NeoSimilarity(
                                    value,
                                    latestConfigurationDescription,
                                    partialResultSongMap.get(melodySongA),
                                    partialResultSongMap.get(melodySongB))
                    ));
        });
        return neoSimilarityList;
    }

    public void saveAggregatedResults(double[][] aggregatedResults, double totalConfigurationEvaluationValue, int configurationsProcessed) {

        melodySimilarityRepository.deleteAll(aggregatedNeoResults);
        aggregatedNeoResults.clear();

        for (int i = 1; i < aggregatedResults.length; i++) {
            // iterate all unique index pairs
            for (int j = i + 1; j < aggregatedResults[i].length; j++) {
                // create a relationship between the aggregated result songs
                double similarityPercentage = aggregatedResults[i][j];
                if (similarityPercentage < MINIMUM_RESULT_SIMILARITY) {
                    continue;
                }
                NeoSimilarity neoSimilarity = new NeoSimilarity(similarityPercentage,
                        aggregatedResultIdMap.get((long) i), aggregatedResultIdMap.get((long) j));
                aggregatedNeoResults.add(neoSimilarity);
            }
        }
        melodySimilarityRepository.saveAll(aggregatedNeoResults);
    }
}
