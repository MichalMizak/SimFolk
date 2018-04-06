package sk.upjs.ics.mmizak.simfolk.core;

import sk.upjs.ics.mmizak.simfolk.core.database.access.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations.DummyVectorAlgorithmConfigurationService;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ISongService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmComputer;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.VectorAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
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


    public static void main(String[] args) throws Exception {

        DummyVectorAlgorithmConfigurationService dummyVectorConfigurationGenerator = new DummyVectorAlgorithmConfigurationService();

        AlgorithmConfiguration vectorAlgorithmConfiguration =
                dummyVectorConfigurationGenerator.generateRandomConfiguration();


        Parser parser = new Parser();
        IAlgorithmComputer algorithmComputer = new VectorAlgorithmComputer();
        List<Song> viktor = parser.parseViktor();

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

        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        modifiedSong = songService.saveOrEdit(modifiedSong);
        viktor = songService.saveOrEdit(viktor);

        VectorAlgorithmResult result = algorithmComputer.computeSimilarityAndSave(vectorAlgorithmConfiguration, modifiedSong);

        System.out.println("Song id: " + result.getVectorSong().getSongId());
        System.out.println("Similarities: " + result.getSongToSimilarityPercentage().toString());

        for (Song songToCompare : viktor) {

            // TODO: For progress send an object to the algorithm computer
            result = algorithmComputer.computeSimilarityAndSave(vectorAlgorithmConfiguration, songToCompare);

            System.out.println("Song id: " + result.getVectorSong().getSongId());
            System.out.println("Similarities: " + result.getSongToSimilarityPercentage().toString());
            // TODO: Handle result

        }
    }

}

