/*
 * This file is generated by jOOQ.
*/
package sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.Indexes;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.Keys;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.Simfolk;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.TermTokenizedRecord;


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
public class TTermTokenized extends TableImpl<TermTokenizedRecord> {

    private static final long serialVersionUID = -600067565;

    /**
     * The reference instance of <code>simfolk.term_tokenized</code>
     */
    public static final TTermTokenized T_TERM_TOKENIZED = new TTermTokenized();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TermTokenizedRecord> getRecordType() {
        return TermTokenizedRecord.class;
    }

    /**
     * The column <code>simfolk.term_tokenized.termId</code>.
     */
    public final TableField<TermTokenizedRecord, Long> TERMID = createField("termId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>simfolk.term_tokenized.orderNumber</code>.
     */
    public final TableField<TermTokenizedRecord, Integer> ORDERNUMBER = createField("orderNumber", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>simfolk.term_tokenized.word</code>.
     */
    public final TableField<TermTokenizedRecord, String> WORD = createField("word", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * Create a <code>simfolk.term_tokenized</code> table reference
     */
    public TTermTokenized() {
        this(DSL.name("term_tokenized"), null);
    }

    /**
     * Create an aliased <code>simfolk.term_tokenized</code> table reference
     */
    public TTermTokenized(String alias) {
        this(DSL.name(alias), T_TERM_TOKENIZED);
    }

    /**
     * Create an aliased <code>simfolk.term_tokenized</code> table reference
     */
    public TTermTokenized(Name alias) {
        this(alias, T_TERM_TOKENIZED);
    }

    private TTermTokenized(Name alias, Table<TermTokenizedRecord> aliased) {
        this(alias, aliased, null);
    }

    private TTermTokenized(Name alias, Table<TermTokenizedRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Simfolk.SIMFOLK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TERM_TOKENIZED_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TermTokenizedRecord> getPrimaryKey() {
        return Keys.KEY_TERM_TOKENIZED_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TermTokenizedRecord>> getKeys() {
        return Arrays.<UniqueKey<TermTokenizedRecord>>asList(Keys.KEY_TERM_TOKENIZED_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TermTokenizedRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TermTokenizedRecord, ?>>asList(Keys.TERM_TOKENIZED_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTermTokenized as(String alias) {
        return new TTermTokenized(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTermTokenized as(Name alias) {
        return new TTermTokenized(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TTermTokenized rename(String name) {
        return new TTermTokenized(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TTermTokenized rename(Name name) {
        return new TTermTokenized(name, null);
    }
}
