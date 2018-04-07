/*
 * This file is generated by jOOQ.
*/
package sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.TermWeightTypeRecord;


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
public class TTermWeightType extends TableImpl<TermWeightTypeRecord> {

    private static final long serialVersionUID = 1359661981;

    /**
     * The reference instance of <code>simfolk.term_weight_type</code>
     */
    public static final TTermWeightType T_TERM_WEIGHT_TYPE = new TTermWeightType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TermWeightTypeRecord> getRecordType() {
        return TermWeightTypeRecord.class;
    }

    /**
     * The column <code>simfolk.term_weight_type.termWeightTypeId</code>.
     */
    public final TableField<TermWeightTypeRecord, Integer> TERMWEIGHTTYPEID = createField("termWeightTypeId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>simfolk.term_weight_type.isTFIDF</code>.
     */
    public final TableField<TermWeightTypeRecord, Boolean> ISTFIDF = createField("isTFIDF", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>simfolk.term_weight_type.TF</code>.
     */
    public final TableField<TermWeightTypeRecord, String> TF = createField("TF", org.jooq.impl.SQLDataType.VARCHAR(40), this, "");

    /**
     * The column <code>simfolk.term_weight_type.IDF</code>.
     */
    public final TableField<TermWeightTypeRecord, String> IDF = createField("IDF", org.jooq.impl.SQLDataType.VARCHAR(40), this, "");

    /**
     * The column <code>simfolk.term_weight_type.nonTFIDFTermWeight</code>.
     */
    public final TableField<TermWeightTypeRecord, String> NONTFIDFTERMWEIGHT = createField("nonTFIDFTermWeight", org.jooq.impl.SQLDataType.VARCHAR(40), this, "");

    /**
     * Create a <code>simfolk.term_weight_type</code> table reference
     */
    public TTermWeightType() {
        this(DSL.name("term_weight_type"), null);
    }

    /**
     * Create an aliased <code>simfolk.term_weight_type</code> table reference
     */
    public TTermWeightType(String alias) {
        this(DSL.name(alias), T_TERM_WEIGHT_TYPE);
    }

    /**
     * Create an aliased <code>simfolk.term_weight_type</code> table reference
     */
    public TTermWeightType(Name alias) {
        this(alias, T_TERM_WEIGHT_TYPE);
    }

    private TTermWeightType(Name alias, Table<TermWeightTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private TTermWeightType(Name alias, Table<TermWeightTypeRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.TERM_WEIGHT_TYPE_ISTFIDF, Indexes.TERM_WEIGHT_TYPE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TermWeightTypeRecord> getPrimaryKey() {
        return Keys.KEY_TERM_WEIGHT_TYPE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TermWeightTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<TermWeightTypeRecord>>asList(Keys.KEY_TERM_WEIGHT_TYPE_PRIMARY, Keys.KEY_TERM_WEIGHT_TYPE_ISTFIDF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTermWeightType as(String alias) {
        return new TTermWeightType(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTermWeightType as(Name alias) {
        return new TTermWeightType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TTermWeightType rename(String name) {
        return new TTermWeightType(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TTermWeightType rename(Name name) {
        return new TTermWeightType(name, null);
    }
}