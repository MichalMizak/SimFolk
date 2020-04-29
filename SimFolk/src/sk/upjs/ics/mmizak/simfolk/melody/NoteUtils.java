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

    private static final Map<String, Integer> noteTypeToAbsoluteDuration = new HashMap<>();
    private static Map<Integer, String> intervalToFuzzyClass = new HashMap<>();

    static {
        noteTypeToAbsoluteDuration.put("whole,", 96);
        noteTypeToAbsoluteDuration.put("half", 48);
        noteTypeToAbsoluteDuration.put("quarter", 24);
        noteTypeToAbsoluteDuration.put("eighth", 12);
        noteTypeToAbsoluteDuration.put("16th", 6);
        noteTypeToAbsoluteDuration.put("32nd,", 3);
    }


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

    static {
        intervalToFuzzyClass.put(-11, "9");
        intervalToFuzzyClass.put(-10, "9");
        intervalToFuzzyClass.put(-9, "9");
        intervalToFuzzyClass.put(-8, "9");

        intervalToFuzzyClass.put(-7, "8");
        intervalToFuzzyClass.put(-6, "8");
        intervalToFuzzyClass.put(-5, "8");

        intervalToFuzzyClass.put(-4, "7");
        intervalToFuzzyClass.put(-3, "7");

        intervalToFuzzyClass.put(-2, "6");
        intervalToFuzzyClass.put(-1, "6");

        intervalToFuzzyClass.put(0, "5");

        intervalToFuzzyClass.put(1, "4");
        intervalToFuzzyClass.put(2, "4");

        intervalToFuzzyClass.put(3, "3");
        intervalToFuzzyClass.put(4, "3");

        intervalToFuzzyClass.put(5, "2");
        intervalToFuzzyClass.put(6, "2");
        intervalToFuzzyClass.put(7, "2");

        intervalToFuzzyClass.put(8, "1");
        intervalToFuzzyClass.put(9, "1");
        intervalToFuzzyClass.put(10, "1");
        intervalToFuzzyClass.put(11, "1");

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

    /**
     * Determine if note is higher lower or the same
     *
     * @param noteToBeHigher
     * @param noteToBeLower
     * @return true if @param noteToBeHigher is higher than @param noteToBeLower
     */
    public static int comparePitch(Note noteToBeHigher, Note noteToBeLower) {

        int octaveToBeHigher = noteToBeHigher.getPitch().getOctave();
        int octaveToBeLower = noteToBeLower.getPitch().getOctave();

        if (octaveToBeHigher == octaveToBeLower) {
            return Integer.compare(getAbsoluteNoteDegree(noteToBeHigher), getAbsoluteNoteDegree(noteToBeLower));
        }

        return Integer.compare(octaveToBeHigher, octaveToBeLower);
    }


    public static String getCountourAsString(Note noteA, Note noteB) {
        int pitchCompare = comparePitch(noteA, noteB);
        if (pitchCompare == 0)
            return "R"; // meaning we go lower/ klesáme
        if (pitchCompare == -1)
            return "L";
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
        difference = difference % 12;

        if (difference >= 0) {
            return "" + difference + " ";
        } else
            return String.valueOf(difference) + " "; // the "-" is implicit here
    }

    /**
     * Returns relative note difference
     *
     * @param noteA
     * @param noteB
     * @return
     */
    public static int getRelativeDifference(Note noteA, Note noteB) {
        return getAbsoluteAbsoluteNoteDegree(noteA) - getAbsoluteAbsoluteNoteDegree(noteB);
    }

    public static String getFuzzyRelativeDifferenceAsString(Note noteA, Note noteB) {
        int difference = getRelativeDifference(noteA, noteB);
        difference = difference % 12;

        return intervalToFuzzyClass.get(difference);
    }

    // get the note degree taking octave into account
    private static int getAbsoluteAbsoluteNoteDegree(Note note) {
        int octave = note.getPitch().getOctave();
        return (octave * 12) + getAbsoluteNoteDegree(note);
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

    public static Integer getDivisionsFactor(Integer divisions) {
        // TODO: more sophisticated conversion, this fits only the thesis dataset
        return 24 / divisions;
    }

    public static int getAbsoluteDuration(Note note, Integer divisionsFactor) {
        if (note.getDuration() == null) {
            return 0;
        }
        int duration = note.getDuration().intValue();

        duration = divisionsFactor * duration; // normalize the length to the default divisions

        //  String noteType = note.getType().getValue();
        //  noteTypeToAbsoluteDuration.get(noteType); // get absolute length
        return duration;
    }


}
