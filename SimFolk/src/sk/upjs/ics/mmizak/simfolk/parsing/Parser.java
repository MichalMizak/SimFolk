package sk.upjs.ics.mmizak.simfolk.parsing;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders.SongBuilder;

import java.io.*;
import java.util.*;

public class Parser {

    static final String VIKTOR_ZOZNAM = "src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\ViktorZoznam.txt";
    static final String VIKTOR = "src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\Viktor.txt";
    static final String PIESNE372 = "src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\piesne372.txt";


    public List<Song> parseViktor() {

        InputStream is = null;
        try {
            is = new FileInputStream(VIKTOR_ZOZNAM);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Song> songs;

        try (Scanner scanner = new Scanner(is, "UTF8")) {

            songs = new ArrayList<>();

            Song song = new SongBuilder().createSong();

            String region = "";
            String key = "";

            String character = null;
            Set<String> attributes = new HashSet<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.trim();
                if (line.equals("") || line.equals("Polka	Valčík	Čardáše")) {
                    continue;
                }

                /*  $ - region
                    ! - character
                    % - key (MOL, DUR, NULL)
                    @ - attribute
                    -@ - remove attribute
                */

                // key
                if (line.startsWith("%")) {
                    key = getKey(line);
                    continue;
                }

                // character set, attribute and region reset
                if (line.startsWith("!")) {
                    if (character == null) {
                        character = line.substring(1);
                        attributes = new HashSet<>();
                        region = null;
                        continue;
                    } else {
                        character = null;
                        continue;
                    }
                }

                if (line.startsWith("$")) {
                    region = line.substring(1);
                    continue;
                }

                // attributes add
                if (line.startsWith("@")) {
                    attributes.add(line.substring(1));
                    continue;
                }

                // attributes remove
                if (line.startsWith("-@")) {
                    String attribute = line.substring(2);
                    attributes.remove(attribute);
                    continue;
                }

                String title = line;
                if (title.trim().equals("")) {
                    break;
                }
                song.setTitle(line);
                song.setLyrics(findSongLyrics(title));
                song.setRegion(region);

                // remove additional keys
                if (attributes.contains("DUR")) {
                    attributes.remove("DUR");
                }

                if (attributes.contains("MOL")) {
                    attributes.remove("MOL");
                }
                attributes.add(key);

                song.setAttributes(new ArrayList<>(attributes));
                song.setSource("Spevník ĽH Zemplín - Viktor Gliganič");
                song.setSongStyle(character);
                songs.add(song);
                song = new SongBuilder().createSong();

            }
        }

        return songs;
    }

    private String findSongLyrics(String title) {

        StringBuilder lyrics = new StringBuilder();

        InputStream is = null;
        try {
            is = new FileInputStream(VIKTOR);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (Scanner scanner = new Scanner(is, "UTF8")) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.trim().equals(title.trim())) {
                    line = scanner.nextLine();
                    // skip blank lines
                    while (line.trim().equals("")) {
                        line = scanner.nextLine();
                    }
                    while (!(line.equals("") || line.trim().equals("Polka\tValčík\tČardáše\n".trim()))) {
                        lyrics.append(line).append("\n");
                        line = scanner.nextLine();
                    }
                }
            }
        }
        return lyrics.toString();
    }

    private String getKey(String key) {
        if (key.equals("%DUR")) {
            return "DUR";
        }
        if (key.equals("%MOL")) {
            return "MOL";
        }
        return "NULL";
    }


    public List<Song> parsePiesne372() {

        InputStream is = null;
        try {
            is = new FileInputStream(PIESNE372);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Song> songs;
        try (Scanner scanner = new Scanner(is, "UTF8")) {

            int counter = 1;

            StringBuilder lyrics = new StringBuilder();
            String title = null;

            songs = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();


                if (line.contains(Integer.toString(counter))) {
                    int dotIndex = 0;

                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '.') {
                            dotIndex = i;
                            break;
                        }
                    }
                    if (lyrics.length() != 0) {
                        Song song = new SongBuilder().createSong();

                        song.setLyrics(lyrics.toString());
                        song.setTitle(title);
                        song.setSource("Jednotný Griešov spevník");
                        songs.add(song);
                    }

                    title = line.substring(dotIndex + 1);

                    lyrics = new StringBuilder();

                    counter++;

                    continue;
                }

                byte[] ptext = new byte[0];
                String utf8Lyrics = null;
                try {
                    ptext = line.getBytes("UTF-8");
                    utf8Lyrics = new String(ptext, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                lyrics.append(utf8Lyrics).append("\n");

            }
        }
        return songs;
    }
}
