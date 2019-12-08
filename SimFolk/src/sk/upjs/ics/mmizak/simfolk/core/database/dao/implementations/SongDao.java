package sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ISongDao;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.SongRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders.SongBuilder;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSong.T_SONG;
import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSongToAttribute.T_SONG_TO_ATTRIBUTE;

public class SongDao implements ISongDao {

    private DSLContext create;

    public SongDao(DSLContext create) {
        this.create = create;
    }

    @Override
    public List<Song> getAll() {
        List<Song> result = create.selectFrom(T_SONG).fetch(this::map);

        result.forEach(this::initAttributes);

        return result;
    }


    @Override
    public Song getById(Long id) {
        Song result = create.selectFrom(T_SONG).where(T_SONG.SONGID.eq(id)).
                fetchOne(this::map);

        return result == null ? null : initAttributes(result);
    }

    @Override
    public Song saveOrEdit(Song s) {

        if (s.getId() == null) {
            SongRecord songRecord = create
                    .insertInto(T_SONG, T_SONG.TITLE, T_SONG.LYRICS, T_SONG.CLEANLYRICS,
                            T_SONG.SONGSTYLE, T_SONG.REGION, T_SONG.SOURCE)
                    .values(s.getTitle(), s.getLyrics(), s.getCleanLyrics(),
                            s.getSongStyle(), s.getRegion(), s.getSource())
                    .onDuplicateKeyIgnore()
                    .returning(T_SONG.SONGID)
                    .fetchOne();

            if (songRecord == null) {
                s = getByLyrics(s.getLyrics());
            } else {
                s.setId(songRecord.getSongid());


                for (String attribute : s.getAttributes()) {
                    create.insertInto(T_SONG_TO_ATTRIBUTE, T_SONG_TO_ATTRIBUTE.SONGID,
                            T_SONG_TO_ATTRIBUTE.ATTRIBUTE)
                            .values(s.getId(), attribute)
                            .onDuplicateKeyIgnore()
                            .execute();
                }
            }
        } else {
            create.update(T_SONG)
                    .set(T_SONG.TITLE, s.getTitle())
                    .set(T_SONG.LYRICS, s.getLyrics())
                    .set(T_SONG.CLEANLYRICS, s.getCleanLyrics())
                    .set(T_SONG.SONGSTYLE, s.getSongStyle())
                    .set(T_SONG.REGION, s.getRegion())
                    .set(T_SONG.SOURCE, s.getSource())
                    .where(T_SONG.SONGID.eq(s.getId()))
                    .execute();


            create.deleteFrom(T_SONG_TO_ATTRIBUTE)
                    .where(T_SONG_TO_ATTRIBUTE.SONGID.eq(s.getId())
                            .and(T_SONG_TO_ATTRIBUTE.ATTRIBUTE.notIn(s.getAttributes())))
                    .execute();

            for (String attribute : s.getAttributes()) {
                create.insertInto(T_SONG_TO_ATTRIBUTE, T_SONG_TO_ATTRIBUTE.SONGID,
                        T_SONG_TO_ATTRIBUTE.ATTRIBUTE)
                        .values(s.getId(), attribute)
                        .onDuplicateKeyIgnore()
                        .execute();
            }
        }

        return s;
    }

    private Song getByLyrics(String lyrics) {
        Song result = create.selectFrom(T_SONG).where(T_SONG.LYRICS.eq(lyrics)).
                fetchOne(this::map);

        return result == null ? null : initAttributes(result);
    }

    /**
     * On delete songtoattributes cascades
     *
     * @param song
     */
    @Override
    public void delete(Song song) {
        create.deleteFrom(T_SONG)
                .where(T_SONG.SONGID.eq(song.getId()))
                .execute();
    }

    @Override
    public Long getCount() {
        return create.selectCount().from(T_SONG).fetchOne(0, long.class);
    }

    // TODO: change this into fetchOne
    @Override
    public Song syncId(Song song) {
        Long id = create.select()
                .from(T_SONG)
                .where(T_SONG.LYRICS.eq(song.getLyrics()))
                .fetchOne(T_SONG.SONGID);

        if (id != null) {
            song.setId(id);
        }

        return song;
    }

    //<editor-fold desc="Utilities">
    private Song initAttributes(Song s) {
        List<String> attributes = create.selectFrom(T_SONG_TO_ATTRIBUTE)
                .where(T_SONG_TO_ATTRIBUTE.SONGID.equal(s.getId()))
                .fetch(T_SONG_TO_ATTRIBUTE.ATTRIBUTE);
        s.setAttributes(attributes);
        return s;
    }

    private Song map(SongRecord s) {
        return new SongBuilder().setId(s.getSongid())
                .setTitle(s.getTitle())
                .setLyrics(s.getLyrics())
                .setCleanLyrics(s.getCleanlyrics())
                .setSongStyle(s.getSongstyle())
                .setAttributes(null)
                .setRegion(s.getRegion())
                .setSource(s.getSource()).createSong();
    }
    //</editor-fold>
}
