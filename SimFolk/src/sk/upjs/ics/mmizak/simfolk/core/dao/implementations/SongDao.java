package sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ISongDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;

import java.util.ArrayList;
import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.TSong.T_SONG;
import static sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.TSongToAttribute.T_SONG_TO_ATTRIBUTE;

public class SongDao implements ISongDao {

    private DSLContext create;

    public SongDao(DSLContext create) {
        this.create = create;
    }

    @Override
    public List<Song> getAll() {
        Result<Record> result = create.select().from(T_SONG).fetch();

        List<Song> songs = new ArrayList<>();

        for (Record r : result) {
            Integer id = r.getValue(T_SONG.SONGID);
            String lyrics = r.getValue(T_SONG.LYRICS);
            String title = r.getValue(T_SONG.TITLE);

            String region = r.getValue(T_SONG.REGION);
            String source = r.getValue(T_SONG.SOURCE);
            String songStyle = r.getValue(T_SONG.SONGSTYLE);


            Result<Record1<String>> attributes = create.select(T_SONG_TO_ATTRIBUTE.ATTRIBUTE)
                    .from(T_SONG_TO_ATTRIBUTE).where(T_SONG_TO_ATTRIBUTE.SONGID.equal(id))
                    .fetch();

            List<String> values = attributes.getValues(T_SONG_TO_ATTRIBUTE.ATTRIBUTE);

            Long idL = new Long(id);

            Song song = new Song(idL, title, lyrics, songStyle, values, region, source);

            songs.add(song);
        }
        return songs;
    }

    @Override
    public Song getById(Long id) {
        return null;
    }

    @Override
    public void saveOrEdit(Song song) {

    }

    @Override
    public void delete(Song song) {

    }
}
