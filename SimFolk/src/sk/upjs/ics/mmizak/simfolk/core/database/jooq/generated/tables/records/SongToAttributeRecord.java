/*
 * This file is generated by jOOQ.
*/
package sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSongToAttribute;


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
public class SongToAttributeRecord extends UpdatableRecordImpl<SongToAttributeRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = 637720355;

    /**
     * Setter for <code>simfolk.song_to_attribute.songId</code>.
     */
    public void setSongid(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>simfolk.song_to_attribute.songId</code>.
     */
    public Long getSongid() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>simfolk.song_to_attribute.attribute</code>.
     */
    public void setAttribute(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>simfolk.song_to_attribute.attribute</code>.
     */
    public String getAttribute() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Long, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TSongToAttribute.T_SONG_TO_ATTRIBUTE.SONGID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TSongToAttribute.T_SONG_TO_ATTRIBUTE.ATTRIBUTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getSongid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getSongid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SongToAttributeRecord value1(Long value) {
        setSongid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SongToAttributeRecord value2(String value) {
        setAttribute(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SongToAttributeRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SongToAttributeRecord
     */
    public SongToAttributeRecord() {
        super(TSongToAttribute.T_SONG_TO_ATTRIBUTE);
    }

    /**
     * Create a detached, initialised SongToAttributeRecord
     */
    public SongToAttributeRecord(Long songid, String attribute) {
        super(TSongToAttribute.T_SONG_TO_ATTRIBUTE);

        set(0, songid);
        set(1, attribute);
    }
}
