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
import sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.records.WeightedTermGroupRecord;


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
public class TWeightedTermGroup extends TableImpl<WeightedTermGroupRecord> {

    private static final long serialVersionUID = 1414102191;

    /**
     * The reference instance of <code>simfolk.weighted_term_group</code>
     */
    public static final TWeightedTermGroup T_WEIGHTED_TERM_GROUP = new TWeightedTermGroup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WeightedTermGroupRecord> getRecordType() {
        return WeightedTermGroupRecord.class;
    }

    /**
     * The column <code>simfolk.weighted_term_group.songId</code>.
     */
    public final TableField<WeightedTermGroupRecord, Integer> SONGID = createField("songId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>simfolk.weighted_term_group.groupId</code>.
     */
    public final TableField<WeightedTermGroupRecord, Integer> GROUPID = createField("groupId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>simfolk.weighted_term_group.termWeightType</code>.
     */
    public final TableField<WeightedTermGroupRecord, String> TERMWEIGHTTYPE = createField("termWeightType", org.jooq.impl.SQLDataType.VARCHAR(40).nullable(false), this, "");

    /**
     * The column <code>simfolk.weighted_term_group.weight</code>.
     */
    public final TableField<WeightedTermGroupRecord, Double> WEIGHT = createField("weight", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * Create a <code>simfolk.weighted_term_group</code> table reference
     */
    public TWeightedTermGroup() {
        this(DSL.name("weighted_term_group"), null);
    }

    /**
     * Create an aliased <code>simfolk.weighted_term_group</code> table reference
     */
    public TWeightedTermGroup(String alias) {
        this(DSL.name(alias), T_WEIGHTED_TERM_GROUP);
    }

    /**
     * Create an aliased <code>simfolk.weighted_term_group</code> table reference
     */
    public TWeightedTermGroup(Name alias) {
        this(alias, T_WEIGHTED_TERM_GROUP);
    }

    private TWeightedTermGroup(Name alias, Table<WeightedTermGroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private TWeightedTermGroup(Name alias, Table<WeightedTermGroupRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.WEIGHTED_TERM_GROUP_GROUPID, Indexes.WEIGHTED_TERM_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WeightedTermGroupRecord> getPrimaryKey() {
        return Keys.KEY_WEIGHTED_TERM_GROUP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WeightedTermGroupRecord>> getKeys() {
        return Arrays.<UniqueKey<WeightedTermGroupRecord>>asList(Keys.KEY_WEIGHTED_TERM_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<WeightedTermGroupRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<WeightedTermGroupRecord, ?>>asList(Keys.WEIGHTED_TERM_GROUP_IBFK_1, Keys.WEIGHTED_TERM_GROUP_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TWeightedTermGroup as(String alias) {
        return new TWeightedTermGroup(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TWeightedTermGroup as(Name alias) {
        return new TWeightedTermGroup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TWeightedTermGroup rename(String name) {
        return new TWeightedTermGroup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TWeightedTermGroup rename(Name name) {
        return new TWeightedTermGroup(name, null);
    }
}
