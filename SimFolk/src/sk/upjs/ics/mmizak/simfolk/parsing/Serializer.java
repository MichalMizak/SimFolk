package sk.upjs.ics.mmizak.simfolk.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Serializer {

    public static String generateJSON(List<Song> songs) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(songs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
