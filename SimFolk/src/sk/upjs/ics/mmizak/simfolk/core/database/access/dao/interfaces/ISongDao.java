package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public interface ISongDao {

    List<Song> getAll();

    Song getById(Integer id);

    Song saveOrEdit(Song song);

    void delete(Song song);

    Song syncId(Song song);
}
