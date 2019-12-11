package sk.upjs.ics.mmizak.simfolk.melody;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoteUtils {

    private static final Map<String, Integer> stepToDegree = new HashMap<>();

    private static final Map<Integer, String> degreeToStepString = new HashMap<>();

    static {
        stepToDegree.put("C", 1);
        stepToDegree.put("D", 3);
        stepToDegree.put("E", 5);
        stepToDegree.put("F", 6);
        stepToDegree.put("G", 8);
        stepToDegree.put("A", 10);
        stepToDegree.put("B", 12);
    }

    static {
        degreeToStepString.put(1, "01");
        degreeToStepString.put(2, "02");
        degreeToStepString.put(3, "03");
        degreeToStepString.put(4, "04");
        degreeToStepString.put(5, "05");
        degreeToStepString.put(6, "06");
        degreeToStepString.put(7, "07");
        degreeToStepString.put(8, "08");
        degreeToStepString.put(9, "09");
        degreeToStepString.put(10, "10");
        degreeToStepString.put(11, "11");
        degreeToStepString.put(12, "12");
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
        Pitch pitch = note.getPitch();
        assert pitch != null;

        Step step = pitch.getStep();
        assert step != null;

        return stepToDegree.get(step.toString());
    }

    public static int getAbsoluteNoteDegree(Note note) {
        Integer degreeWithoutAlterations = getDegreeWithoutAlterations(note);

        // since we got here pitch is safely not null

        BigDecimal alter = note.getPitch().getAlter();

        int alterationValue;

        if (alter == null)
            alterationValue = 0;
        else
            alterationValue = alter.intValue();

        if (degreeWithoutAlterations == 0 && alterationValue == -1)
            return 12;
        if (degreeWithoutAlterations == 12 && alterationValue == 1)
            return 1;
        return degreeWithoutAlterations + alterationValue;
    }

    public static String getAbsoluteNoteDegreeString(Note note) {
        return degreeToStepString.get(getAbsoluteNoteDegree(note));
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
