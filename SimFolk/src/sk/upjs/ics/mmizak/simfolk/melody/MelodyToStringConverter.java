package sk.upjs.ics.mmizak.simfolk.melody;

import org.audiveris.proxymusic.Attributes;
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

        if (measures.isEmpty())
            return new ArrayList<>();


        for (ScorePartwise.Part.Measure measure : measures) {
            notesInMeasures.add(NoteUtils.getNotesFromMeasure(measure));
        }

        List<String> notesInMeasureStringList = new ArrayList<>();

        if (format == RHYTHM) {
            Integer divisions = getDivisions(measures).intValue();

            Integer divisionsFactor = NoteUtils.getDivisionsFactor(divisions);

            for (List<Note> notes : notesInMeasures) {
                notesInMeasureStringList.add(getRhythmString(notes, divisionsFactor));
            }
        } else {
//            notesInMeasures = NoteUtils.removeRests(notesInMeasures);
//            switch (format) {
//
//                case ABSOLUTE:
//                    for (List<Note> notes : notesInMeasures) {
//                        notesInMeasureStringList.add(getAbsoluteString(notes));
//                    }
//                    break;
//                case RELATIVE:
//                    for (List<Note> notes : notesInMeasures) {
//                        notesInMeasureStringList.add(getRelativeString(notes));
//                    }
//                    break;
//                case CONTOUR:
//                    for (List<Note> notes : notesInMeasures) {
//                        notesInMeasureStringList.add(getCountourString(notes));
//                    }
//                    break;
//                default:
//                    throw new UnsupportedOperationException();
//            }
        }

        List<String> result = removeEmptyMeasures(notesInMeasureStringList);

        return result;
    }

    private BigDecimal getDivisions(List<ScorePartwise.Part.Measure> measures) {
        BigDecimal divisions = null;
        for (Object o : measures.get(0).getNoteOrBackupOrForward()) {
            if (o instanceof Attributes) {
                Attributes attributes = (Attributes) o;
                divisions = attributes.getDivisions();
            }
        }
        if (divisions == null) {
            System.out.println("Found null divisions");
            return new BigDecimal(0);
        }

        return divisions;
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

    private String getRhythmString(List<Note> notes, Integer divisionsFactor) {
        StringBuilder sb = new StringBuilder();

        for (Note note : notes) {
            int absoluteDuration = NoteUtils.getAbsoluteDuration(note, divisionsFactor);
            if (absoluteDuration == 0)
                continue;
            sb.append(absoluteDuration)
                    .append(" ");
        }

        return sb.toString();
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

    /**
     * This method omits the octave as an octave change in folk songs
     * is just an indication of a tune from the same family
     *
     * @param notes
     * @return
     */
    private String getAbsoluteString(List<Note> notes) {
        StringBuilder sb = new StringBuilder();

        for (Note note : notes) {

            String absoluteNoteDegreeString = NoteUtils.getAbsoluteNoteDegreeString(note);
            sb.append(absoluteNoteDegreeString);
        }

        return sb.toString().trim();
    }

    private String getAbsoluteStringWithOctave(List<Note> notes) {
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
