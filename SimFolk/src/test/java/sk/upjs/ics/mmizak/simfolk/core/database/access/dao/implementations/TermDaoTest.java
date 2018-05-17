package test.java.sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations.TermDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import test.javask.upjs.ics.mmizak.simfolk.core.dao.implementations.DaoTestSetup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TermDaoTest {

    Connection connection;
    private TermDao termDao;


    @BeforeAll
    void setUpAll() {
        connection = DaoTestSetup.createConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        termDao = new TermDao(create);
    }

    @AfterAll
    void tearDownAll() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }

    @BeforeEach
    void setUpEach() {

        List<Term> terms = new ArrayList<>();
        terms.add(new Term(null, "test", new ArrayList<>(Collections.singleton("test")), AlgorithmConfiguration.TermScheme.UNGRAM));
        terms.add(new Term(null, "test2", new ArrayList<>(Collections.singleton("test2")), AlgorithmConfiguration.TermScheme.UNGRAM));
        terms.add(new Term(null, "test3", new ArrayList<>(Collections.singleton("test3")), AlgorithmConfiguration.TermScheme.UNGRAM));
        terms.add(new Term(null, "test4", new ArrayList<>(Collections.singleton("test4")), AlgorithmConfiguration.TermScheme.UNGRAM));

        termDao.saveOrEdit(terms);

    }

    @AfterEach
    void tearDownEach() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void syncTermIds() {

    }

    @Test
    void syncTermId() {
        Term okTerm = new Term(null, "test", new ArrayList<>(Collections.singleton("test")), AlgorithmConfiguration.TermScheme.UNGRAM);
        Term okTerm2 = new Term(null, "test2", new ArrayList<>(Collections.singleton("test2")), AlgorithmConfiguration.TermScheme.UNGRAM);
        Term notOkTerm1 = new Term(null, "test6", new ArrayList<>(Collections.singleton("test3")), AlgorithmConfiguration.TermScheme.UNGRAM);
        Term notOkTerm2 = new Term(null, "test7", new ArrayList<>(Collections.singleton("test4")), AlgorithmConfiguration.TermScheme.UNGRAM);

        termDao.syncTermId(okTerm);
        termDao.syncTermId(okTerm2);
        termDao.syncTermId(notOkTerm1);
        termDao.syncTermId(notOkTerm2);

        assertNotNull(okTerm.getId());
        assertNotNull(okTerm2.getId());

        assertNull(notOkTerm1.getId());
        assertNull(notOkTerm2.getId());
    }

    @Test
    void getAllTerms() {
    }

    @Test
    void getTermsById() {
    }

    @Test
    void getTermByTermScheme() {
    }

    @Test
    void getTerm() {
    }

    @Test
    void getTermById() {
    }

    @Test
    void saveOrEdit() {
    }

    @Test
    void saveOrEdit1() {
    }

    @Test
    void delete() {
    }
}