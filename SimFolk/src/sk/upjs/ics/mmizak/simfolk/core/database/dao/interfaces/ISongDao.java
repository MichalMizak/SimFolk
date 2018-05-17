package sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;

public interface ISongDao {

    List<Song> getAll();

    Song getById(Long id);

    Song saveOrEdit(Song song);

    void delete(Song song);

    Long getCount();

    Song syncId(Song song);
}
