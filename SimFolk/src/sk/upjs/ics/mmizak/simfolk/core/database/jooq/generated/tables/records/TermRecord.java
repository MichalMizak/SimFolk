/*
 * This file is generated by jOOQ.
*/
package sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTerm;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TermRecord extends UpdatableRecordImpl<TermRecord> implements Record4<Long, String, Integer, String> {

    private static final long serialVersionUID = 139853763;

    /**
     * Setter for <code>simfolk.term.termId</code>.
     */
    public void setTermid(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>simfolk.term.termId</code>.
     */
    public Long getTermid() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>simfolk.term.lyricsFragment</code>.
     */
    public void setLyricsfragment(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>simfolk.term.lyricsFragment</code>.
     */
    public String getLyricsfragment() {
        return (String) get(1);
    }

    /**
     * Setter for <code>simfolk.term.wordCount</code>.
     */
    public void setWordcount(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>simfolk.term.wordCount</code>.
     */
    public Integer getWordcount() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>simfolk.term.termScheme</code>.
     */
    public void setTermscheme(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>simfolk.term.termScheme</code>.
     */
    public String getTermscheme() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, String, Integer, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, String, Integer, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TTerm.T_TERM.TERMID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TTerm.T_TERM.LYRICSFRAGMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TTerm.T_TERM.WORDCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TTerm.T_TERM.TERMSCHEME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getTermid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getLyricsfragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getWordcount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getTermscheme();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getTermid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getLyricsfragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getWordcount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTermscheme();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermRecord value1(Long value) {
        setTermid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermRecord value2(String value) {
        setLyricsfragment(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermRecord value3(Integer value) {
        setWordcount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermRecord value4(String value) {
        setTermscheme(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermRecord values(Long value1, String value2, Integer value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TermRecord
     */
    public TermRecord() {
        super(TTerm.T_TERM);
    }

    /**
     * Create a detached, initialised TermRecord
     */
    public TermRecord(Long termid, String lyricsfragment, Integer wordcount, String termscheme) {
        super(TTerm.T_TERM);

        set(0, termid);
        set(1, lyricsfragment);
        set(2, wordcount);
        set(3, termscheme);
    }
}
