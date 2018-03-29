package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ILyricCleaner;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.HashSet;
import java.util.Set;

/**
 * Cleans lyrics from special characters () [] {} / . ,
 * and
 */
// TODO: Test

public class LyricCleaner implements ILyricCleaner {

    @Override
    public String clean(String lyrics) {

        lyrics = cleanSpecialCharacters(lyrics);
        lyrics = cleanRepetition(lyrics);
        lyrics = cleanWhitespaces(lyrics);
        lyrics = cleanNumbers(lyrics);

        return lyrics;
    }

    @Override
    public Song clean(Song song) {

        String cleanLyrics = clean(song.getLyrics());

        song.setLyrics(cleanLyrics);

        return song;
    }

    /**
     * Cleans special characters () [] {} <> / . , :
     *
     * @param lyrics Song lyrics to be cleaned
     * @return lyrics without special characters
     */
    private String cleanSpecialCharacters(String lyrics) {
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

    private Set<Character> getSpecialCharactersSet() {
        Set<Character> specialCharactersSet = new HashSet<>();

        char[] specialCharacters = new char[]{'(', ')', '[', ']', '{', '}',
                '<', '>', '/', '.', ',', ':'};

        for (char specialCharacter : specialCharacters) {
            specialCharactersSet.add(specialCharacter);
        }

        return specialCharactersSet;
    }

    /**
     * takes care of ... repetition cases
     *
     * @param lyrics
     * @return
     */
    private String cleanRepetition(String lyrics) {
        return lyrics.replaceAll("//.//.//.", "");
    }

    /**
     * replaces all whitespaces with " "
     *
     * @param lyrics
     * @return
     */
    private String cleanWhitespaces(String lyrics) {
        return lyrics.trim().replaceAll("\\s+", " ");
    }


    /**
     * Cleans lyrics from numbers and repetition numbers such as 2x, 3x...
     *
     * @param lyrics
     * @return clean lyrics without numbers
     */
    private String cleanNumbers(String lyrics) {
        ;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lyrics.length(); i++) {
            char judgedChar = lyrics.charAt(i);
            if (isANumber(judgedChar)) {
                if ((i + 1 < lyrics.length()) && (lyrics.charAt(i + 1) == 'x')) {
                    i++;
                }
                continue;
            }
            result.append(judgedChar);
        }
        return result.toString();
    }

    private boolean isANumber(char judgedChar) {
        if (judgedChar >= '0' && judgedChar <= '9') {
            return true;
        }
        return false;
    }
}
