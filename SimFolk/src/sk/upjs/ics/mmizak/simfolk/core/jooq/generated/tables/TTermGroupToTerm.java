/*
 * This file is generated by jOOQ.
*/
package sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables;


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

import sk.upjs.ics.mmizak.simfolk.core.jooq.generated.Indexes;
import sk.upjs.ics.mmizak.simfolk.core.jooq.generated.Keys;
import sk.upjs.ics.mmizak.simfolk.core.jooq.generated.Simfolk;
import sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.records.TermGroupToTermRecord;


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
public class TTermGroupToTerm extends TableImpl<TermGroupToTermRecord> {

    private static final long serialVersionUID = -1179693830;

    /**
     * The reference instance of <code>simfolk.term_group_to_term</code>
     */
    public static final TTermGroupToTerm T_TERM_GROUP_TO_TERM = new TTermGroupToTerm();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TermGroupToTermRecord> getRecordType() {
        return TermGroupToTermRecord.class;
    }

    /**
     * The column <code>simfolk.term_group_to_term.groupId</code>.
     */
    public final TableField<TermGroupToTermRecord, Integer> GROUPID = createField("groupId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>simfolk.term_group_to_term.termId</code>.
     */
    public final TableField<TermGroupToTermRecord, Integer> TERMID = createField("termId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>simfolk.term_group_to_term</code> table reference
     */
    public TTermGroupToTerm() {
        this(DSL.name("term_group_to_term"), null);
    }

    /**
     * Create an aliased <code>simfolk.term_group_to_term</code> table reference
     */
    public TTermGroupToTerm(String alias) {
        this(DSL.name(alias), T_TERM_GROUP_TO_TERM);
    }

    /**
     * Create an aliased <code>simfolk.term_group_to_term</code> table reference
     */
    public TTermGroupToTerm(Name alias) {
        this(alias, T_TERM_GROUP_TO_TERM);
    }

    private TTermGroupToTerm(Name alias, Table<TermGroupToTermRecord> aliased) {
        this(alias, aliased, null);
    }

    private TTermGroupToTerm(Name alias, Table<TermGroupToTermRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.TERM_GROUP_TO_TERM_PRIMARY, Indexes.TERM_GROUP_TO_TERM_TERMID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TermGroupToTermRecord> getPrimaryKey() {
        return Keys.KEY_TERM_GROUP_TO_TERM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TermGroupToTermRecord>> getKeys() {
        return Arrays.<UniqueKey<TermGroupToTermRecord>>asList(Keys.KEY_TERM_GROUP_TO_TERM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TermGroupToTermRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TermGroupToTermRecord, ?>>asList(Keys.TERM_GROUP_TO_TERM_IBFK_1, Keys.TERM_GROUP_TO_TERM_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTermGroupToTerm as(String alias) {
        return new TTermGroupToTerm(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTermGroupToTerm as(Name alias) {
        return new TTermGroupToTerm(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TTermGroupToTerm rename(String name) {
        return new TTermGroupToTerm(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TTermGroupToTerm rename(Name name) {
        return new TTermGroupToTerm(name, null);
    }
}
