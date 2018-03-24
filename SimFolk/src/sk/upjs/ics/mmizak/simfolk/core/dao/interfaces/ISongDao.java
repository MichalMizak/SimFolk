package sk.upjs.ics.mmizak.simfolk.core.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public interface ISongDao {

    List<Song> getAll();

    Song getById(Long id);

    void saveOrEdit(Song song);

    void delete(Song song);
}
