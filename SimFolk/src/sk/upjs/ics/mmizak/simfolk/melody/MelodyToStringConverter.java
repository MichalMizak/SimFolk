package sk.upjs.ics.mmizak.simfolk.melody;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.ScorePartwise;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.MusicStringFormat.RHYTHM;

public class MelodyToStringConverter {

    public List<String> getMelodyInMeasuresAsString(List<ScorePartwise.Part.Measure> measures,
                                                    AlgorithmConfiguration.MusicStringFormat format) {

        List<List<Note>> notesInMeasures = new ArrayList<>();

        for (ScorePartwise.Part.Measure measure : measures) {
            notesInMeasures.add(NoteUtils.getNotesFromMeasure(measure));
        }

        List<String> notesInMeasureStringList = new ArrayList<>();

        if (format == RHYTHM)
            for (List<Note> notes : notesInMeasures) {
                notesInMeasureStringList.add(getRhythmString(notes));
            }
        else {
            notesInMeasures = NoteUtils.removeRests(notesInMeasures);
            switch (format) {

                case ABSOLUTE:
                    for (List<Note> notes : notesInMeasures) {
                        notesInMeasureStringList.add(getAbsoluteString(notes));
                    }
                    break;
                case RELATIVE:
                    for (List<Note> notes : notesInMeasures) {
                        notesInMeasureStringList.add(getRelativeString(notes));
                    }
                    break;
                case CONTOUR:
                    for (List<Note> notes : notesInMeasures) {
                        notesInMeasureStringList.add(getCountourString(notes));
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        List<String> result = removeEmptyMeasures(notesInMeasureStringList);

        return result;
    }

    private List<String> removeEmptyMeasures(List<String> notesInMeasureStringList) {
        List<String> result = new ArrayList<>();

        for (String measure : notesInMeasureStringList) {
            if (measure != null && !measure.trim().isEmpty()) {
                result.add(measure);
            }
        }
        return result;
    }

    private String getRhythmString(List<Note> notes) {
        StringBuilder sb = new StringBuilder();

        for (Note note : notes) {
            sb.append(note.getDuration()).append(" ");
        }

        return sb.toString().trim();
    }

    private String getRelativeString(List<Note> notes) {
        StringBuilder sb = new StringBuilder();

        if (notes.isEmpty())
            return "";

        Note previousNote = notes.get(0);

        for (int i = 1; i < notes.size(); i++) {
            sb.append(NoteUtils.getRelativeDifferenceAsString(previousNote, notes.get(i)));
        }

        return sb.toString().trim();
    }

    private String getAbsoluteString(List<Note> notes) {
        StringBuilder sb = new StringBuilder();

        for (Note note : notes) {

            String absoluteNoteDegreeString = NoteUtils.getAbsoluteNoteDegreeString(note);

            sb.append(absoluteNoteDegreeString)
                    .append(note.getPitch().getOctave());
        }

        return sb.toString().trim();
    }

    private String getCountourString(List<Note> notes) {
        StringBuilder sb = new StringBuilder();

        if (notes.isEmpty())
            return "";

        Note previousNote = notes.get(0);

        for (int i = 1; i < notes.size(); i++) {
            sb.append(NoteUtils.getCountourAsString(previousNote, notes.get(i)));
        }

        return sb.toString().trim();
    }
}
