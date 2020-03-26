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
        // "hexadecimal" notation
        degreeToStepString.put(1, "1");
        degreeToStepString.put(2, "2");
        degreeToStepString.put(3, "3");
        degreeToStepString.put(4, "4");
        degreeToStepString.put(5, "5");
        degreeToStepString.put(6, "6");
        degreeToStepString.put(7, "7");
        degreeToStepString.put(8, "8");
        degreeToStepString.put(9, "9");
        degreeToStepString.put(10, "A");
        degreeToStepString.put(11, "B");
        degreeToStepString.put(12, "C");
    }

    private NoteUtils() {
    }

    /**
     * Determine if note is higher than the other
     *
     * @param noteToBeHigher
     * @param noteToBeLower
     * @return true if @param noteToBeHigher is higher than @param noteToBeLower
     */
    public static boolean isHigherInPitch(Note noteToBeHigher, Note noteToBeLower) {

        int octaveToBeHigher = noteToBeHigher.getPitch().getOctave();
        int octaveToBeLower = noteToBeLower.getPitch().getOctave();

        if (octaveToBeHigher == octaveToBeLower) {
            return getAbsoluteNoteDegree(noteToBeHigher) > getAbsoluteNoteDegree(noteToBeLower);
        }

        return octaveToBeHigher > octaveToBeLower;
    }

    public static String getCountourAsString(Note noteA, Note noteB) {
        if (isHigherInPitch(noteA, noteB))
            return "L"; // meaning we go lower/ klesáme
        else return "H"; // meaning we go higher/ stúpame
    }

    public static int getDegreeWithoutAlterations(Note note) {
        Pitch pitch = note.getPitch();

        Step step = pitch.getStep();

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

    /**
     * Returns relative note difference as string
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static String getRelativeDifferenceAsString(Note noteA, Note noteB) {
        int difference = getRelativeDifference(noteA, noteB);

        if (difference >= 0) {
            return "+" + difference;
        } else
            return String.valueOf(difference);
    }

    /**
     * Returns relative note difference as string
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getRelativeDifference(Note noteA, Note noteB) {
        int octaveA = noteA.getPitch().getOctave();
        int octaveB = noteB.getPitch().getOctave();
        return octaveA * getAbsoluteNoteDegree(noteA) - octaveB * getAbsoluteNoteDegree(noteB);
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
            if (attribute instanceof Note) {

                Note noteToAdd = (Note) attribute;
                if (!isPitchedNote(noteToAdd)) {
                    continue;
                }
                notes.add(noteToAdd);
            }
        }

        return notes;
    }

    private static boolean isPitchedNote(Note noteToAdd) {
        if (noteToAdd.getUnpitched() != null) {
            System.out.println("Found unpitched note");
        }
        return noteToAdd.getPitch() != null && noteToAdd.getPitch().getStep() != null && noteToAdd.getUnpitched() == null;
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
