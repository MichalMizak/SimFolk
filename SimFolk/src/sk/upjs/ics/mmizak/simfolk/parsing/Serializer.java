package sk.upjs.ics.mmizak.simfolk.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.upjs.ics.mmizak.simfolk.containers.Song;
import sk.upjs.ics.mmizak.simfolk.containers.SongStats;
import sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations.BiGram;
import sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations.TriGram;
import sk.upjs.ics.mmizak.simfolk.containers.nGramImplementations.UnGram;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Serializer {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, JsonProcessingException {

        Parser parser = new Parser();

        /*Serializer serializer = new Serializer();
        System.out.println(serializer.generateJSON(parser.parseViktor()));
        System.out.println(serializer.generateJSON(parser.parsePiesne372()));*/

        List<Song> songs = parser.parseViktor();
        SongStats songStats = new SongStats(0, songs.get(0).getLyrics());

        for (UnGram ungram : songStats.getUnGrams()) {
            System.out.println(ungram.getLyricsFragment());
        }
        for (BiGram biGram : songStats.getBiGrams()) {
            System.out.println(biGram.getLyricsFragment());
        }
        for (TriGram triGram : songStats.getTriGrams()) {
            System.out.println(triGram.getLyricsFragment());
        }
    }

    private String generateJSON(List<Song> songs) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(songs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
