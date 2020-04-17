package sk.upjs.ics.mmizak.simfolk.parsing;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.Work;
import org.audiveris.proxymusic.util.Marshalling;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;
import sk.upjs.ics.mmizak.simfolk.melody.NoteUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScorePartwiseUnmarshaller implements IMusicXMLUnmarshaller {

    @Override
    public List<MelodySong> getSongsInMeasuresFromXML(List<File> xmlFiles) {
        List<MelodySong> result = xmlFiles.stream().map(this::getMelodySongFromXmlFile).collect(Collectors.toList());
        return result;
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

        MelodySong result = new MelodySong(scorePartwise.getPart().get(0).getMeasure(), xmlFile);

        Work work = scorePartwise.getWork();

        if (work != null) {
            String workTitle = work.getWorkTitle();
            if (workTitle != null && !workTitle.isEmpty()) {
                result.setTitle(workTitle);
            }
        }

        if (result.getTitle() == null)
            result.setTitle(xmlFile.getName());

        result.setLyrics(xmlFile.getAbsolutePath());
        result.setCleanLyrics(xmlFile.getAbsolutePath());

        result.setSource(xmlFile.getAbsolutePath());
        result.setAttributes(new ArrayList<>());

        return result;
    }

    @Override
    public List<Note> getNoteListFromXML(File xmlFile) {
        List<ScorePartwise.Part.Measure> measures = getMelodySongFromXmlFile(xmlFile).getMelodyInMeasures();
        return NoteUtils.getNotesFromMeasures(measures);
    }
}
