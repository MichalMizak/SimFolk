package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.TermGroupRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.TermGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermGroup.T_TERM_GROUP;
import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermGroupToTerm.T_TERM_GROUP_TO_TERM;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.*;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration.TermComparisonAlgorithm;


// TODO: TEST
public class TermGroupDao implements ITermGroupDao {

    private final DSLContext create;
    private final ITermDao termDao;

    public TermGroupDao(DSLContext create, ITermDao termDao) {
        this.create = create;
        this.termDao = termDao;
    }

    @Override
    public List<TermGroup> getAllTermGroups() {
        List<TermGroup> result = create.selectFrom(T_TERM_GROUP).
                fetch(this::map);

        result.forEach(this::initTerms);

        return result;
    }

    @Override
    public List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        List<TermGroup> result = create.selectFrom(T_TERM_GROUP)
                .where(T_TERM_GROUP.TERMCOMPARISONALGORITHM.eq(termComparisonAlgorithm.toString()))
                .and(T_TERM_GROUP.TOLERANCE.eq(tolerance))
                .fetch(this::map);

        result.forEach(this::initTerms);

        return result;
    }

    @Override
    public List<TermGroup> getTermGroups(TermComparisonAlgorithm termComparisonAlgorithm, TermScheme termScheme, double tolerance) {
        List<TermGroup> result = create.selectFrom(T_TERM_GROUP)
                .where(T_TERM_GROUP.TERMCOMPARISONALGORITHM.eq(termComparisonAlgorithm.toString()))
                .and(T_TERM_GROUP.TERMSCHEME.eq(termScheme.toString()))
                .and(T_TERM_GROUP.TOLERANCE.eq(tolerance))
                .fetch(this::map);

        result.forEach(this::initTerms);

        return result;
    }

    @Override
    public TermGroup getTermGroupById(Long groupId) {

        TermGroup result = create.selectFrom(T_TERM_GROUP)
                .where(T_TERM_GROUP.GROUPID.eq(groupId))
                .fetchOne(this::map);

        if (result != null)
            result = initTerms(result);


        return result;
    }

    @Override
    public TermGroup saveOrEdit(TermGroup termGroup) {

        if (termGroup.getGroupId() == null) {
            TermGroupRecord termGroupRecord = create.insertInto(T_TERM_GROUP)
                    .columns(T_TERM_GROUP.GROUPID, T_TERM_GROUP.TERMSCHEME,
                            T_TERM_GROUP.INCIDENCECOUNT,
                            T_TERM_GROUP.TERMCOMPARISONALGORITHM, T_TERM_GROUP.TOLERANCE)
                    .values(termGroup.getGroupId(), termGroup.getTermScheme().toString(),
                            termGroup.getDatabaseIncidenceCount(),
                            termGroup.getTermComparisonAlgorithm().toString(), termGroup.getTolerance())
                    .returning(T_TERM_GROUP.GROUPID)
                    .fetchOne();

            termGroup.setGroupId(termGroupRecord.getGroupid());

            termGroup.getTerms().forEach(term ->
                    create.insertInto(T_TERM_GROUP_TO_TERM)
                            .columns(T_TERM_GROUP_TO_TERM.GROUPID, T_TERM_GROUP_TO_TERM.TERMID)
                            .values(termGroup.getGroupId(), term.getId())
                            .onDuplicateKeyIgnore()
                            .execute());
        } else {
            create.update(T_TERM_GROUP)
                    .set(T_TERM_GROUP.TERMSCHEME, termGroup.getTermScheme().toString())
                    .set(T_TERM_GROUP.INCIDENCECOUNT, termGroup.getDatabaseIncidenceCount())
                    .set(T_TERM_GROUP.TERMCOMPARISONALGORITHM, termGroup.getTermComparisonAlgorithm().toString())
                    .set(T_TERM_GROUP.TOLERANCE, termGroup.getTolerance())
                    .where(T_TERM_GROUP.GROUPID.eq(termGroup.getGroupId()))
                    .execute();

            List<Long> idList =
                    termGroup.getTerms().stream().map(Term::getId).collect(Collectors.toList());

            create.deleteFrom(T_TERM_GROUP_TO_TERM)
                    .where(T_TERM_GROUP_TO_TERM.GROUPID.eq(termGroup.getGroupId())
                            .and(T_TERM_GROUP_TO_TERM.TERMID.notIn(idList)))
                    .execute();

            for (Term term : termGroup.getTerms()) {
                create.insertInto(T_TERM_GROUP_TO_TERM)
                        .columns(T_TERM_GROUP_TO_TERM.GROUPID,
                                T_TERM_GROUP_TO_TERM.TERMID)
                        .values(termGroup.getGroupId(), term.getId())
                        .onDuplicateKeyIgnore()
                        .execute();
            }
        }

        return termGroup;
    }

    @Override
    public void delete(TermGroup term) {
        create.deleteFrom(T_TERM_GROUP)
                .where(T_TERM_GROUP.GROUPID.eq(term.getGroupId()))
                .execute();
    }


    @Override
    public List<TermGroup> syncGroupIds(List<Term> terms, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {

        List<TermGroup> result = new ArrayList<>();

        for (Term term : terms) {
            result.add(syncGroupId(term, termComparisonAlgorithm, tolerance));
        }

        return result;
    }

    @Override
    public TermGroup syncGroupId(Term term, TermComparisonAlgorithm termComparisonAlgorithm, double tolerance) {
        // TODO: CHANGE INTO FETCH ONE
        Long groupId = create.select()
                .from(T_TERM_GROUP_TO_TERM)
                .join(T_TERM_GROUP)
                .on(T_TERM_GROUP_TO_TERM.GROUPID.eq(T_TERM_GROUP.GROUPID))
                .where(T_TERM_GROUP_TO_TERM.TERMID.eq(term.getId()))
                .and(T_TERM_GROUP.TERMCOMPARISONALGORITHM.eq(termComparisonAlgorithm.toString()))
                .and(T_TERM_GROUP.TOLERANCE.eq(tolerance))
                .fetchAny(T_TERM_GROUP.GROUPID);

        if (groupId != null) {
            return getTermGroupById(groupId);
        }

        List<Term> terms = new ArrayList<>();
        terms.add(term);

        return new TermGroup(null, term.getTermScheme(), terms, 1,
                termComparisonAlgorithm, tolerance);
    }

    //<editor-fold desc="Private methods">
    private TermGroup initTerms(TermGroup termGroup) {
        List<Long> termIds = create.selectFrom(T_TERM_GROUP_TO_TERM)
                .where(T_TERM_GROUP_TO_TERM.GROUPID.eq(termGroup.getGroupId()))
                .fetch(T_TERM_GROUP_TO_TERM.TERMID);

        List<Term> terms = termDao.getTermsById(termIds);

        termGroup.setTerms(terms);

        return termGroup;
    }

    private TermGroup map(TermGroupRecord termGroupRecord) {
        TermComparisonAlgorithm termComparisonAlgorithm = TermComparisonAlgorithm.valueOf(termGroupRecord.getTermcomparisonalgorithm());
        TermScheme termScheme = TermScheme.valueOf(termGroupRecord.getTermscheme());

        return new TermGroup(termGroupRecord.getGroupid(), termScheme, null, termGroupRecord.getIncidencecount(),
                termComparisonAlgorithm, termGroupRecord.getTolerance());
    }
    //</editor-fold>
}
