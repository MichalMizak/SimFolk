package sk.upjs.ics.mmizak.simfolk.utilities;

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
        return null;
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
}
