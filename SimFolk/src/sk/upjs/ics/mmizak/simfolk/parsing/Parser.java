package sk.upjs.ics.mmizak.simfolk.parsing;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.io.*;
import java.util.*;

class Parser {

    static final String VIKTOR_ZOZNAM = "src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\ViktorZoznam.txt";
    static final String VIKTOR = "src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\Viktor.txt";
    static final String PIESNE372 = "src\\sk\\upjs\\ics\\mmizak\\simfolk\\parsing\\resources\\piesne372.txt";



    public List<Song> parseViktor() throws FileNotFoundException {

        InputStream is = new FileInputStream(VIKTOR_ZOZNAM);

        Scanner scanner = new Scanner(is, "UTF8");

        List<Song> songs = new ArrayList<>();

        Song song = new Song();

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
            } else if (attributes.contains("MOL")) {
                attributes.remove("MOL");
            }
            attributes.add(key);

            song.setAttributes(new ArrayList<>(attributes));
            song.setSource("Spevník ĽH Zemplín - Viktor Gliganič");
            song.setSongStyle(character);
            songs.add(song);
            song = new Song();

        }
        /*for (Song songg : songs) {
            System.out.println(songg.toString());
            System.out.println("\n");
        }*/

        return songs;
    }

    private String findSongLyrics(String title) throws FileNotFoundException {

        StringBuilder lyrics = new StringBuilder();

        InputStream is = new FileInputStream(VIKTOR);
        Scanner scanner = new Scanner(is, "UTF8");

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


    public List<Song> parsePiesne372() throws FileNotFoundException, UnsupportedEncodingException {

        InputStream is = new FileInputStream(PIESNE372);
        Scanner scanner = new Scanner(is, "UTF8");

        int counter = 1;

        StringBuilder lyrics = new StringBuilder();
        String title = null;

        List<Song> songs = new ArrayList<>();

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
                    Song song = new Song();

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

            byte[] ptext = line.getBytes("UTF-8");

            String utf8Lyrics = new String(ptext, "UTF-8");

            lyrics.append(utf8Lyrics).append("\n");

        }

        /*for (Song song : songs) {
            System.out.println(song.toString());
            System.out.println("\n");
        }*/
        return songs;
    }
}
