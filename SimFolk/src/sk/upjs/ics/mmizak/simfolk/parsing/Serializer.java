package sk.upjs.ics.mmizak.simfolk.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.upjs.ics.mmizak.simfolk.algorithm.containers.Song;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Serializer {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, JsonProcessingException {

        Parser parser = new Parser();

        List<Song> songsViktor = parser.parseViktor();
        List<Song> songsPiesne372 = parser.parsePiesne372();

        System.out.println(songsViktor.size() + songsPiesne372.size());

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
