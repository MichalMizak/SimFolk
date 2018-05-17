package test.java.sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations.SongDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.builders.SongBuilder;
import test.javask.upjs.ics.mmizak.simfolk.core.dao.implementations.DaoTestSetup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongDaoTest {

    SongDao songDao;
    Connection connection;

    @BeforeAll
    void setUpAll() {
        connection = DaoTestSetup.createConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
        songDao = new SongDao(create);
    }

    @AfterEach
    void tearDownEach() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }

    @Test
    void getAll() {

        Song song = new SongBuilder().setId(null).setTitle("new title").setLyrics("lyrics").setCleanLyrics("cleanLyrics").setSongStyle("songstyle").setAttributes(Collections.singletonList("DUR")).setRegion("region").setSource("source").createSong();

        songDao.saveOrEdit(song);

        assertNotNull(songDao.getAll());
    }

    @Test
    void getById() {
        Song song = new SongBuilder().setId(null).setTitle("new title").setLyrics("lyrics").setCleanLyrics("cleanLyrics").setSongStyle("songstyle").setAttributes(Collections.singletonList("DUR")).setRegion("region").setSource("source").createSong();

        song = songDao.saveOrEdit(song);

        Song song2 = songDao.getById(song.getId());

        assertNotNull(song2);
        assertEquals(song.getId(), song2.getId());
    }

    @Test
    void saveOrEdit() {

        Song song = new SongBuilder().setId(null).setTitle("new title").setLyrics("lyrics").setCleanLyrics("cleanLyrics").setSongStyle("songstyle").setAttributes(Collections.singletonList("DUR")).setRegion("region").setSource("source").createSong();

        songDao.saveOrEdit(song);

        assertNotNull(song.getId());

        String newTitle = "new title 2";
        song.setTitle(newTitle);

        songDao.saveOrEdit(song);

        Song byId = songDao.getById(song.getId());

        assertEquals(byId.getTitle(), newTitle);

    }

    @Test
    void delete() {
        Song song = new SongBuilder().setId(null).setTitle("new title").setLyrics("lyrics").setCleanLyrics("cleanLyrics").setSongStyle("songstyle").setAttributes(Collections.singletonList("DUR")).setRegion("region").setSource("source").createSong();

        songDao.saveOrEdit(song);

        songDao.delete(song);

        assertNull(songDao.getById(song.getId()));
    }

    @Test
    void getCount() {
        Long count = songDao.getCount();
        assertNotNull(count);
    }

}