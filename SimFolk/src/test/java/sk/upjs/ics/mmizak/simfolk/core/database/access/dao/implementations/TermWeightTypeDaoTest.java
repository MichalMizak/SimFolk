package test.java.sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations.TermWeightTypeDao;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ITermWeightTypeDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;
import test.javask.upjs.ics.mmizak.simfolk.core.dao.implementations.DaoTestSetup;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TermWeightTypeDaoTest {

    ITermWeightTypeDao termWeightTypeDao;
    Connection connection;
    private DSLContext create;

    @BeforeAll
    void setUpAll() {
        connection = DaoTestSetup.createConnection();
        create = DSL.using(connection, SQLDialect.MYSQL);
        termWeightTypeDao = new TermWeightTypeDao(create);
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

    @Test
    void getUnique() {
        TermWeightType termWeightType = termWeightTypeDao.saveOrEdit(TermWeightType.getFrequencyWeight());

        assertNotNull(termWeightType);

        TermWeightType unique = termWeightTypeDao.getUnique(true, TF.TF_NAIVE, IDF.NONE, NonTFIDFTermWeightType.NONE);

        assertNotNull(unique);
    }
}