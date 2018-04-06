package sk.upjs.ics.mmizak.simfolk.core.database.access.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ISongDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.services.interfaces.ISongService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SongService implements ISongService {

    private ISongDao songDao;

    public SongService(ISongDao songDao) {
        this.songDao = songDao;
    }


    @Override
    public List<Song> saveOrEdit(List<Song> viktor) {
        return viktor.stream().map(this::saveOrEdit).collect(Collectors.toList());
    }

    @Override
    public List<Song> getAll() {
        return songDao.getAll();
    }

    @Override
    public Song getById(Long id) {
        return songDao.getById(id);
    }

    @Override
    public Song saveOrEdit(Song song) {
        return songDao.saveOrEdit(song);
    }

    @Override
    public void delete(Song song) {
        songDao.delete(song);
    }

    @Override
    public Song syncId(Song song) {
        return songDao.syncId(song);
    }


}
