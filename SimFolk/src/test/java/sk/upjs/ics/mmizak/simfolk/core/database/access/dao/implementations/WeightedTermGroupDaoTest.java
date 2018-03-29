package test.java.sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations.SongDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations.TermDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations.TermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations.WeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Song;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.WeightedTermGroup;
import test.javask.upjs.ics.mmizak.simfolk.core.dao.implementations.DaoTestSetup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeightedTermGroupDaoTest {

    IWeightedTermGroupDao weightedTermGroupDao;
    Connection connection;
    private double tolerance;
    private TermDao termDao;
    private TermGroupDao termGroupDao;
    private SongDao songDao;
    private TermGroup termGroup;
    private Song song;
    private WeightedTermGroup weightedTermGroup;

    @BeforeAll
    void setUpAll() {
        connection = DaoTestSetup.createConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        songDao = new SongDao(create);
        termDao = new TermDao(create);
        termGroupDao = new TermGroupDao(create, termDao);
        weightedTermGroupDao = new WeightedTermGroupDao(create, termGroupDao);
    }

    @AfterAll
    void tearDownAll() {
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }

    @BeforeEach
    void setUp() {

        List<Term> terms = new ArrayList<>();
        terms.add(new Term(null, "test", new ArrayList<>(Collections.singleton("test")), TermScheme.UNGRAM));
        terms.add(new Term(null, "test2", new ArrayList<>(Collections.singleton("test2")), TermScheme.UNGRAM));
        terms.add(new Term(null, "test3", new ArrayList<>(Collections.singleton("test3")), TermScheme.UNGRAM));
        terms.add(new Term(null, "test4", new ArrayList<>(Collections.singleton("test4")), TermScheme.UNGRAM));

        termDao.saveOrEdit(terms);

        termGroup = new TermGroup(null, terms, 4,
                TermComparisonAlgorithm.NAIVE, 0D);

        termGroup = termGroupDao.saveOrEdit(termGroup);

        this.song = new Song(null, "test title", "test lyrics", "test songstyle",
                Collections.singletonList("TEST DUR"), "test region", "test source");

        song = songDao.saveOrEdit(song);

        this.weightedTermGroup = new WeightedTermGroup(termGroup,
                song.getId(), TermWeightType.TF_NAIVE, 1D);

        weightedTermGroup = weightedTermGroupDao.saveOrEdit(weightedTermGroup);
    }

    @AfterEach
    void tearDown() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getByIds() {
        WeightedTermGroup wtg = weightedTermGroupDao.getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId());
        assertNotNull(wtg);
        assertNotNull(wtg.getSongId());
    }

    @Test
    void getAll() {
        List<WeightedTermGroup> all = weightedTermGroupDao.getAll(weightedTermGroup.getSongId(), weightedTermGroup.getTermWeightType(),
                weightedTermGroup.getTermComparisonAlgorithm(), weightedTermGroup.getTolerance());

        if (assertContainsWeightedGroup(all)) return;
        fail("failed to get correct group");
    }

    @Test
    void getAll1() {
        List<WeightedTermGroup> all = weightedTermGroupDao.getAll(weightedTermGroup.getTermWeightType(),
                weightedTermGroup.getTermComparisonAlgorithm(), weightedTermGroup.getTolerance());

        if (assertContainsWeightedGroup(all)) return;
        fail("failed to get correct group");
    }

    private boolean assertContainsWeightedGroup(List<WeightedTermGroup> all) {
        for (WeightedTermGroup wtg : all) {
            Integer songId = wtg.getSongId();
            assertNotNull(songId, wtg.getTerms().toString());

            Integer groupId = wtg.getGroupId();
            assertNotNull(groupId);

            if (songId.equals(weightedTermGroup.getSongId())
                    && groupId.equals(weightedTermGroup.getGroupId())) {
                assert true;
                return true;
            }
        }
        return false;
    }

    @Test
    void saveOrEdit() {
        assertNotNull(weightedTermGroup.getSongId());
        assertNotNull(weightedTermGroupDao.getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId()));

        Double testValue = new Double(45);
        Term testTerm = new Term(null, "TESTESTEST", new ArrayList<>(Collections.singleton("test4")), TermScheme.UNGRAM);
        ArrayList<Term> testTerms = new ArrayList<>();
        testTerms.add(testTerm);

        termDao.saveOrEdit(testTerms);


        weightedTermGroup.setTermWeight(testValue);
        weightedTermGroup.setTermWeightType(TermWeightType.TF);

        weightedTermGroup.setTolerance(testValue);
        weightedTermGroup.setTerms(testTerms);

        weightedTermGroup = weightedTermGroupDao.saveOrEdit(weightedTermGroup);
        weightedTermGroup = weightedTermGroupDao.getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId());


        assertEquals(testValue, weightedTermGroup.getTermWeight());
        assertEquals(testValue, weightedTermGroup.getTolerance());
        assertEquals(TermWeightType.TF, weightedTermGroup.getTermWeightType());

        termGroup = termGroupDao.getTermGroupById(weightedTermGroup.getGroupId());


        assertEquals(termGroup.getTolerance(), testValue);
        int testIndex = 0;

        assertEquals(termGroup.getTerms().get(testIndex).getLyricsFragment(),
                weightedTermGroup.getTerms().get(testIndex).getLyricsFragment(),
                termGroup.toString() + weightedTermGroup.toString());
    }

    @Test
    void saveOrEditExcludingTermGroup() {

        Double testValue = new Double(45);
        Term testTerm = new Term(null, "TESTESTEST", new ArrayList<>(Collections.singleton("test4")), TermScheme.UNGRAM);
        ArrayList<Term> testTerms = new ArrayList<>();
        testTerms.add(testTerm);

        termDao.saveOrEdit(testTerms);


        weightedTermGroup.setTermWeight(testValue);
        weightedTermGroup.setTermWeightType(TermWeightType.TF);

        weightedTermGroup.setTolerance(testValue);
        weightedTermGroup.setTerms(testTerms);

        weightedTermGroup = weightedTermGroupDao.saveOrEditExcludingTermGroup(weightedTermGroup);

        assertEquals(testValue, weightedTermGroup.getTermWeight());
        assertEquals(TermWeightType.TF, weightedTermGroup.getTermWeightType());

        termGroup = termGroupDao.getTermGroupById(weightedTermGroup.getGroupId());


        assertNotEquals(termGroup.getTolerance(), testValue);
        int testIndex = 0;


        assertEquals(testValue, weightedTermGroup.getTolerance());

        assertEquals(testTerms.get(0).getLyricsFragment(), weightedTermGroup.getTerms().get(testIndex).getLyricsFragment());

        assertNotEquals(termGroup.getTerms().get(testIndex).getLyricsFragment(),
                weightedTermGroup.getTerms().get(testIndex).getLyricsFragment(),
                termGroup.toString() + "\n" + weightedTermGroup.toString());


        weightedTermGroup = weightedTermGroupDao.getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId());

        assertNotEquals(testValue, weightedTermGroup.getTolerance());

        assertNotEquals(testTerms.get(0).getLyricsFragment(), weightedTermGroup.getTerms().get(testIndex).getLyricsFragment());

        assertEquals(termGroup.getTerms().get(testIndex).getLyricsFragment(),
                weightedTermGroup.getTerms().get(testIndex).getLyricsFragment(),
                termGroup.toString() + "\n" + weightedTermGroup.toString());
    }

    @Test
    void delete() {
        weightedTermGroupDao.delete(weightedTermGroup);

        weightedTermGroup = weightedTermGroupDao.getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId());

        assertNull(weightedTermGroup);

        termGroup = termGroupDao.getTermGroupById(termGroup.getGroupId());

        assertNull(termGroup);
    }

    @Test
    void deleteExcludingTermGroup() {

        weightedTermGroupDao.deleteExcludingTermGroup(weightedTermGroup);

        weightedTermGroup = weightedTermGroupDao.getByIds(weightedTermGroup.getGroupId(), weightedTermGroup.getSongId());

        assertNull(weightedTermGroup);

        termGroup = termGroupDao.getTermGroupById(termGroup.getGroupId());

        assertNotNull(termGroup);
    }

    @Test
    void syncGroupIds() {
        Term okTerm = new Term(null, "test", new ArrayList<>(Collections.singleton("test")), TermScheme.UNGRAM);
        Term okTerm2 = new Term(null, "test2", new ArrayList<>(Collections.singleton("test2")), TermScheme.UNGRAM);

        // with null ids of terms from database
        TermGroup okTermGroup = termGroupDao.syncGroupId(okTerm, TermComparisonAlgorithm.NAIVE, 0);
        TermGroup okTermGroup2 = termGroupDao.syncGroupId(okTerm2, TermComparisonAlgorithm.NAIVE, 0);

        assertNull(okTermGroup.getGroupId());
        assertNull(okTermGroup2.getGroupId());

        // with non null ids
        termDao.syncTermId(okTerm);
        termDao.syncTermId(okTerm2);

        assertNotNull(okTerm.getId());
        assertNotNull(okTerm2.getId());

        okTermGroup = termGroupDao.syncGroupId(okTerm, TermComparisonAlgorithm.NAIVE, 0);
        okTermGroup2 = termGroupDao.syncGroupId(okTerm2, TermComparisonAlgorithm.NAIVE, 0);

        assertNotNull(okTermGroup.getGroupId());
        assertNotNull(okTermGroup2.getGroupId());

        Term notOkTerm1 = new Term(null, "test6", new ArrayList<>(Collections.singleton("test3")), TermScheme.UNGRAM);
        Term notOkTerm2 = new Term(null, "test7", new ArrayList<>(Collections.singleton("test4")), TermScheme.UNGRAM);

        TermGroup notOkTermGroup = termGroupDao.syncGroupId(notOkTerm1, TermComparisonAlgorithm.NAIVE, 0);
        TermGroup notOkTermGroup2 = termGroupDao.syncGroupId(notOkTerm2, TermComparisonAlgorithm.NAIVE, 0);

        assertNull(notOkTermGroup.getGroupId());
        assertNull(notOkTermGroup2.getGroupId());

    }

    @Test
    void syncGroupId() {
    }
}