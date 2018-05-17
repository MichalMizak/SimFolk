package sk.upjs.ics.mmizak.simfolk.core.services.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public interface ISongService {

    List<Song> getAll();

    Song getById(Long id);

    Song saveOrEdit(Song song);

    void delete(Song song);

    Song syncId(Song song);

    Song initAndSave(Song song);

    List<Song> saveOrEdit(List<Song> viktor);
}
