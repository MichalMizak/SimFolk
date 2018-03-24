package test.sk.upjs.ics.mmizak.simfolk.core.dao.implementations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sk.upjs.ics.mmizak.simfolk.core.dao.DaoFactory;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ISongDao;

import static org.junit.jupiter.api.Assertions.*;

class SongDaoTest {
    ISongDao songDao = DaoFactory.INSTANCE.getSongDao();

    @Test
    void getAll() {
        Assertions.assertNotNull(songDao.getAll());
    }

    @Test
    void getById() {
    }

    @Test
    void saveOrEdit() {
    }

    @Test
    void delete() {
    }

}