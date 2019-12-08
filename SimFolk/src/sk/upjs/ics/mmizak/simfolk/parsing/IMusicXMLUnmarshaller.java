package sk.upjs.ics.mmizak.simfolk.parsing;

import org.audiveris.proxymusic.Note;
import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.io.File;
import java.util.List;

public interface IMusicXMLUnmarshaller {

    List<MelodySong> getSongsInMeasuresFromXML(List<File> xmlFiles);

    MelodySong getMelodySongFromXmlFile(File xmlFile);

    List<Note> getNoteListFromXML(File xmlFile);
}
