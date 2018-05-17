package sk.upjs.ics.mmizak.simfolk.core.services.implementations;

import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ISongDao;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ILyricCleaner;
import sk.upjs.ics.mmizak.simfolk.core.services.interfaces.ISongService;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.List;
import java.util.stream.Collectors;

public class SongService implements ISongService {

    private ISongDao songDao;
    private ILyricCleaner lyricCleaner;

    public SongService(ISongDao songDao, ILyricCleaner lyricCleaner) {
        this.songDao = songDao;
        this.lyricCleaner = lyricCleaner;
    }

    @Override
    public Song initAndSave(Song song) {
        song = lyricCleaner.clean(song);
        song = syncId(song);

        song = saveOrEdit(song);
        return song;
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
