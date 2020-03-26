package sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.TermRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTerm.T_TERM;
import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermTokenized.T_TERM_TOKENIZED;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class TermDao implements ITermDao {

    private final DSLContext create;

    public TermDao(DSLContext create) {
        this.create = create;
    }

    // TODO: optimize select into batch select
    @Override
    public List<Term> syncTermIds(List<Term> terms) {

        /*List<String> lyricsFragments = terms.stream().map(Term::getLyricsFragment).collect(Collectors.toList());

        Field<Long> ids = DSL.when(T_TERM.LYRICSFRAGMENT.in(lyricsFragments), T_TERM.TERMID)
                .otherwise();

        for (int i = 0; i < terms.size(); i++) {
            terms.get(i).setId(ids.getValue());
        }
        */

        return terms.stream().map(this::syncTermId).collect(Collectors.toList());
    }

    @Override
    public Term syncTermId(Term term) {
        Long id = create.select(T_TERM.TERMID)
                .from(T_TERM)
                .where(T_TERM.LYRICSFRAGMENT.eq(term.getLyricsFragment()))
                .and(T_TERM.TERMSCHEME.eq(term.getTermScheme().toString()))
                .fetchOne(T_TERM.TERMID);

        if (id != null) {
            term.setId(id);
        }

        return term;
    }

    @Override
    public List<Term> getAllTerms() {
        List<Term> result = create.selectFrom(T_TERM).
                fetch(this::map);

        result.forEach(this::initTokenizedLyricsFragment);

        return result;
    }

    @Override
    public List<Term> getTermsById(List<Long> termIds) {
        List<Term> result = create.selectFrom(T_TERM)
                .where(T_TERM.TERMID.in(termIds))
                .fetch(this::map);

        result.forEach(this::initTokenizedLyricsFragment);

        return result;
    }

    @Override
    public List<Term> getTermByTermScheme(TermScheme termScheme) {
        List<Term> result = create.selectFrom(T_TERM)
                .where(T_TERM.TERMSCHEME.eq(termScheme.toString()))
                .fetch(this::map);

        result.forEach(this::initTokenizedLyricsFragment);

        return result;
    }

    @Override
    public Term getTerm(TermScheme termScheme, String lyricsFragment) {
        Term term = null;
        try {
           term = create.selectFrom(T_TERM)
                   .where(T_TERM.TERMSCHEME.eq(termScheme.toString()))
                   .and(T_TERM.LYRICSFRAGMENT.eq(lyricsFragment))
                   .fetchOne(this::map);

           term = initTokenizedLyricsFragment(term);
       } catch (NullPointerException e) {
            System.out.println();
        }
        return term;
    }

    @Override
    public Term getTermById(Term term) {
        Term result = create.selectFrom(T_TERM)
                .where(T_TERM.TERMID.eq(term.getId())).
                        fetchOne(this::map);

        return result == null ? null : initTokenizedLyricsFragment(result);
    }


    @Override
    public List<Term> saveOrEdit(List<Term> terms) {
        List<Term> result = new ArrayList<>();

        for (Term term : terms) {
            result.add(saveOrEdit(term));
        }
        return result;
    }

    @Override
    public Term saveOrEdit(Term term) {

        if (term.getId() == null) {
            TermRecord termRecord = create
                    .insertInto(T_TERM)
                    .columns(T_TERM.LYRICSFRAGMENT,
                            T_TERM.TERMSCHEME, T_TERM.WORDCOUNT)
                    .values(term.getLyricsFragment(),
                            term.getTermScheme().toString(), term.getWordCount())
                    .onDuplicateKeyIgnore()
                    .returning(T_TERM.TERMID)
                    .fetchOne();

            if (termRecord != null) {
                term.setId(termRecord.getTermid());
                saveTokenizedLyricsFragment(term);
            } else {
                term = getTerm(term.getTermScheme(), term.getLyricsFragment());
            }
        } else {

            create.update(T_TERM)
                    .set(T_TERM.LYRICSFRAGMENT, term.getLyricsFragment())
                    .set(T_TERM.TERMSCHEME, term.getTermScheme().toString())
                    .set(T_TERM.WORDCOUNT, term.getWordCount())
                    .where(T_TERM.TERMID.eq(term.getId()))
                    .execute();

            // delete and save is simpler
            create.deleteFrom(T_TERM_TOKENIZED)
                    .where(T_TERM_TOKENIZED.TERMID.eq(term.getId()))
                    .execute();

            saveTokenizedLyricsFragment(term);
        }

        return term;
    }

    /**
     * On delete TTermTokenized cascades
     *
     * @param term
     */
    @Override
    public void delete(Term term) {
        create.deleteFrom(T_TERM)
                .where(T_TERM.TERMID.eq(term.getId()))
                .execute();
    }

    /**
     * Tokenized lyrics fragment is initiated separately
     *
     * @param termRecord
     * @return
     */
    private Term map(TermRecord termRecord) {
        return new Term(termRecord.getTermid(), termRecord.getLyricsfragment(),
                new ArrayList<>(), TermScheme.valueOf(termRecord.getTermscheme()));
    }

    private Term initTokenizedLyricsFragment(Term term) {

        List<String> tokenizedLyricsFragment = create.selectFrom(T_TERM_TOKENIZED)
                .where(T_TERM_TOKENIZED.TERMID.equal(term.getId()))
                .orderBy(T_TERM_TOKENIZED.ORDERNUMBER)
                .fetch(T_TERM_TOKENIZED.WORD);

        term.setTokenizedLyricsFragment(tokenizedLyricsFragment);

        return term;
    }

    private void saveTokenizedLyricsFragment(Term s) {
        int orderNumber = 0;

        for (String word : s.getTokenizedLyricsFragment()) {
            create.insertInto(T_TERM_TOKENIZED, T_TERM_TOKENIZED.TERMID,
                    T_TERM_TOKENIZED.ORDERNUMBER, T_TERM_TOKENIZED.WORD)
                    .values(s.getId(), orderNumber, word)
                    .execute();
            orderNumber++;
        }
    }
}
