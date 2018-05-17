package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

public interface ILyricCleaner {

    String clean(String lyrics);

    Song clean(Song song);
}