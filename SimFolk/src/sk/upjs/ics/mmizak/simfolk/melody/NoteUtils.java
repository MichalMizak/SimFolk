package sk.upjs.ics.mmizak.simfolk.melody;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.ScorePartwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoteUtils {

    public static final Map<String, Integer> stepToDegree = new HashMap<>();

    static {
        stepToDegree.put("C", 1);
        stepToDegree.put("D", 3);
        stepToDegree.put("E", 5);
        stepToDegree.put("F", 6);
        stepToDegree.put("G", 8);
        stepToDegree.put("A", 10);
        stepToDegree.put("B", 12);
    }

    private NoteUtils() {
    }

    public static boolean isHigherInPitch(Note noteToBeHigher, Note noteToBeLower) {

        int octaveToBeHigher = noteToBeHigher.getPitch().getOctave();
        int octaveToBeLower = noteToBeLower.getPitch().getOctave();

        if (octaveToBeHigher == octaveToBeLower) {
            return getAbsoluteNoteDegree(noteToBeHigher) > getAbsoluteNoteDegree(noteToBeLower);
        }

        return octaveToBeHigher > octaveToBeLower;
    }

    public static int getDegreeWithoutAlterations(Note note) {
        return stepToDegree.get(note.getPitch().getStep());
    }

    public static int getAbsoluteNoteDegree(Note note) {
        Integer degreeWithoutAlterations = getDegreeWithoutAlterations(note);

        int alterationValue = note.getPitch().getAlter().intValue();

        if (degreeWithoutAlterations == 0 && alterationValue == -1)
            return 12;
        if (degreeWithoutAlterations == 12 && alterationValue == 1)
            return 1;
        return degreeWithoutAlterations + alterationValue;
    }


    public static List<Note> getNotesFromMeasures(List<ScorePartwise.Part.Measure> measures) {
        List<Note> notes = new ArrayList<>();

        for (ScorePartwise.Part.Measure measure : measures) {
            notes.addAll(getNotesFromMeasure(measure));
        }

        return notes;
    }

    public static List<Note> getNotesFromMeasure(ScorePartwise.Part.Measure measure) {
        List<Note> notes = new ArrayList<>();

        for (Object attribute : measure.getNoteOrBackupOrForward()) {
            if (attribute instanceof Note)
                notes.add((Note) attribute);
        }

        return notes;
    }


    public static List<List<Note>> removeRests(List<List<Note>> notesInMeasures) {
        List<List<Note>> result = new ArrayList<>();

        for (List<Note> notesInMeasure : notesInMeasures) {
            List<Note> resultNotes = new ArrayList<>();
            for (Note note : notesInMeasure) {
                if (note.getPitch() != null) {
                    resultNotes.add(note);
                }
            }
            if (!resultNotes.isEmpty()) {
                result.add(resultNotes);
            }
        }
        return result;
    }

    public static String toString(Note note) {
        StringBuilder sb = new StringBuilder();

        sb.append("Pitch: ")
                .append(note.getPitch().getAlter())
                .append("Duration: ")
                .append(note.getDuration())
                .append("\n")
                .append("Accidental: ")
                .append(note.getAccidental())
                .append("\n")
                .append("Rest: ")
                .append(note.getRest());

        return sb.toString();
    }

    public static List<List<ScorePartwise.Part.Measure>> getMelodiesInMeasuresFromMelodySongs(List<MelodySong> melodySongs) {
        return melodySongs.stream().map((MelodySong::getMelodyInMeasures)).collect(Collectors.toList());
    }
}
