package test.java.sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.SongDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import test.javask.upjs.ics.mmizak.simfolk.core.dao.implementations.DaoTestSetup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongDaoTest {

    SongDao songDao;
    Connection connection;

    @BeforeAll
    void setUp() {
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

    @BeforeEach
    void setupEach() {

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

        Song song = new Song(null, "new title", "lyrics", "songstyle",
                Collections.singletonList("DUR"), "region", "source");

        songDao.saveOrEdit(song);

        Assertions.assertNotNull(songDao.getAll());
    }

    @Test
    void getById() {
        Song song = new Song(null, "new title", "lyrics", "songstyle",
                Collections.singletonList("DUR"), "region", "source");

        Song song2 = songDao.getById(song.getId());

        Assertions.assertEquals(song.getId(), song2.getId());
    }

    @Test
    void saveOrEdit() {

        Song song = new Song(null, "new title", "lyrics", "songstyle",
                Collections.singletonList("DUR"), "region", "source");

        songDao.saveOrEdit(song);

        Assertions.assertNotNull(song.getId());

        String newTitle = "new title 2";
        song.setTitle(newTitle);

        songDao.saveOrEdit(song);

        Song byId = songDao.getById(song.getId());

        Assertions.assertEquals(byId.getTitle(), newTitle);

    }

    @Test
    void delete() {
        Song song = new Song(null, "new title", "lyrics", "songstyle",
                Collections.singletonList("DUR"), "region", "source");

        songDao.saveOrEdit(song);

        songDao.delete(song);

        Assertions.assertNull(songDao.getById(song.getId()));
    }

}