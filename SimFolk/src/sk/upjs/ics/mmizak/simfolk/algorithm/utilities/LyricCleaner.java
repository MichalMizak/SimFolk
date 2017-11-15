package sk.upjs.ics.mmizak.simfolk.algorithm.utilities;

import java.util.HashSet;
import java.util.Set;

/**
 * Cleans lyrics from special characters () [] {} / . ,
 * and
 */
// TODO: Test

public class LyricCleaner {

    public static String clean(String lyrics) {

        lyrics = cleanSpecialCharacters(lyrics);
        lyrics = cleanRepetition(lyrics);
        lyrics = cleanWhitespaces(lyrics);
        lyrics = cleanNumbers(lyrics);

        return lyrics;
    }

    /**
     * Cleans special characters () [] {} <> / . , :
     *
     * @param lyrics Song lyrics to be cleaned
     * @return lyrics without special characters
     */
    private static String cleanSpecialCharacters(String lyrics) {
        Set<Character> specialCharactersSet = getSpecialCharactersSet();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lyrics.length(); i++) {
            char judgedChar = lyrics.charAt(i);
            if (specialCharactersSet.contains(judgedChar)) {
                continue;
            }
            result.append(judgedChar);
        }
        return result.toString();
    }

    private static Set<Character> getSpecialCharactersSet() {
        Set<Character> specialCharactersSet = new HashSet<>();

        char[] specialCharacters = new char[]{'(', ')', '[', ']', '{', '}',
                '<', '>', '/', '.', ',', ':'};

        for (char specialCharacter : specialCharacters) {
            specialCharactersSet.add(specialCharacter);
        }

        return specialCharactersSet;
    }

    /** takes care of ... repetition cases
     *
     * @param lyrics
     * @return
     */
    private static String cleanRepetition(String lyrics) {
        return lyrics;
    }

    /**
     *
     * replaces all whitespaces with " "
     * @param lyrics
     * @return
     */
    private static String cleanWhitespaces(String lyrics) {
        return lyrics.trim().replaceAll("\\s+", " ");
    }


    /**
     * Cleans lyrics from numbers and repetition numbers such as 2x, 3x...
     * @param lyrics
     * @return clean lyrics without numbers
     */
    private static String cleanNumbers(String lyrics) {;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lyrics.length(); i++) {
            char judgedChar = lyrics.charAt(i);
            if (isIrrelevantNumber(judgedChar)) {
                if ((i+1 < lyrics.length()) && (lyrics.charAt(i+1) == 'x')) {
                    i++;
                }
                continue;
            }
            result.append(judgedChar);
        }
        return result.toString();
    }

    private static boolean isIrrelevantNumber(char judgedChar) {
        if (judgedChar >= '0' && judgedChar <= '9') {
            return true;
        }
        return false;
    }
}
