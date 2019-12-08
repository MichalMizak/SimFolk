package sk.upjs.ics.mmizak.simfolk.parsing;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.util.Marshalling;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;
import sk.upjs.ics.mmizak.simfolk.melody.NoteUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ScorePartwiseUnmarshaller implements IMusicXMLUnmarshaller {

    @Override
    public List<MelodySong> getSongsInMeasuresFromXML(List<File> xmlFiles) {
        return xmlFiles.stream().map(this::getMelodySongFromXmlFile).collect(Collectors.toList());
    }

    @Override
    public MelodySong getMelodySongFromXmlFile(File xmlFile) {
        ScorePartwise scorePartwise = null;
        try {
            InputStream is = new FileInputStream(xmlFile);
            scorePartwise = (ScorePartwise) Marshalling.unmarshal(is);
        } catch (FileNotFoundException | Marshalling.UnmarshallingException e) {
            e.printStackTrace();
        }

        assert scorePartwise != null;
        assert scorePartwise.getPart().isEmpty();

        return new MelodySong(scorePartwise.getPart().get(0).getMeasure(), xmlFile);
    }

    @Override
    public List<Note> getNoteListFromXML(File xmlFile) {
        List<ScorePartwise.Part.Measure> measures = getMelodySongFromXmlFile(xmlFile).getMelodyInMeasures();
        return NoteUtils.getNotesFromMeasures(measures);
    }
}
