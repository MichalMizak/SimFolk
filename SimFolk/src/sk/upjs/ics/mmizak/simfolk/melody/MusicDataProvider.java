package sk.upjs.ics.mmizak.simfolk.melody;

import sk.upjs.ics.mmizak.simfolk.core.AlgorithmStarter;
import sk.upjs.ics.mmizak.simfolk.core.factories.ServiceFactory;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ISongService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.parsing.IMusicXMLUnmarshaller;
import sk.upjs.ics.mmizak.simfolk.parsing.ScorePartwiseUnmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.AlgorithmStarter.MUSICXML_RESOURCE_DIRECTORY;

public class MusicDataProvider {

    /**
     * Iterate the xml database
     *
     * @return unmarshalled melodies from songs
     */

    public List<MelodySong> getMelodySongs() {

        IMusicXMLUnmarshaller scorePartwiseParser = new ScorePartwiseUnmarshaller();

        List<File> xmlFiles = new ArrayList<>();
        xmlFiles = iterateXMLFiles(MUSICXML_RESOURCE_DIRECTORY);

        xmlFiles.add(new File("C:\\UPJŠ\\Bakalárska práca\\SimFolk\\SimFolk\\src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\xmlFiles\\zaspievalo_vtaca_edit.xml"));


        List<MelodySong> melodySongs = scorePartwiseParser.getSongsInMeasuresFromXML(xmlFiles);
        assert !xmlFiles.isEmpty();

//        for (int i = 0; i < melodySongs.size(); i++) {
//            melodySongs.get(i).setId((long) i + 1);
//        }

        saveMelodySong(melodySongs);

        return melodySongs;
    }

    /**
     * Improvised method to save melody songs into the lyric database
     *
     * @param melodySongs
     */
    private void saveMelodySong(List<MelodySong> melodySongs) {
        ISongService songService = ServiceFactory.INSTANCE.getSongService();

        melodySongs.forEach(songService::initAndSave);

        System.out.println();
    }

    private static List<File> iterateXMLFiles(String musicxmlResourceDirectory) {
        List<File> result = new ArrayList<>();

        File resourceDirectory = new File(musicxmlResourceDirectory);

        int counter = 0;
        // first
        if (!resourceDirectory.isDirectory()) {
            return result;
        }

        File[] xmlDirectories = resourceDirectory.listFiles();
        assert xmlDirectories != null;

        for (File xmlDirectory : xmlDirectories) {
            if (!xmlDirectory.isDirectory()) {
                if (xmlDirectory.getPath().endsWith(".xml")) {
                    result.add(xmlDirectory);
                }
                continue;
            }
            File[] potentialXMLs = xmlDirectory.listFiles();

            assert potentialXMLs != null;
            for (File potentialXML : potentialXMLs) {
                if (potentialXML.getPath().endsWith(".xml")) {
                    result.add(potentialXML);
                    counter++;
                    if (counter == AlgorithmStarter.SONGS_TO_COMPARE_COUNT) {
                        return result;
                    }
                }
            }
        }

        return result;
    }


}
