package sk.upjs.ics.mmizak.simfolk.core.utilities;

import sk.upjs.ics.mmizak.simfolk.melody.MelodySong;

import java.util.List;

public class CollectionsUtilities {
    public static MelodySong getById(List<MelodySong> melodySongs, Long songId) {
        if (songId == null)
            throw new RuntimeException("null melodysong ID @ Collections utilities");

        return melodySongs.stream().filter(song -> songId.equals(song.getId())).findFirst().orElse(null);
    }
}
