/*
 * This file is generated by jOOQ.
*/
package sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated;


import javax.annotation.Generated;

import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TResultToSong;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSong;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSongToAttribute;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTerm;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermGroup;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermGroupToTerm;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermTokenized;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermWeightType;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TVectorAlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TVectorAlgorithmResult;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TWeightedTermGroup;


/**
 * Convenience access to all tables in simfolk
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>simfolk.result_to_song</code>.
     */
    public static final TResultToSong T_RESULT_TO_SONG = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TResultToSong.T_RESULT_TO_SONG;

    /**
     * The table <code>simfolk.song</code>.
     */
    public static final TSong T_SONG = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSong.T_SONG;

    /**
     * The table <code>simfolk.song_to_attribute</code>.
     */
    public static final TSongToAttribute T_SONG_TO_ATTRIBUTE = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TSongToAttribute.T_SONG_TO_ATTRIBUTE;

    /**
     * The table <code>simfolk.term</code>.
     */
    public static final TTerm T_TERM = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTerm.T_TERM;

    /**
     * The table <code>simfolk.term_group</code>.
     */
    public static final TTermGroup T_TERM_GROUP = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermGroup.T_TERM_GROUP;

    /**
     * The table <code>simfolk.term_group_to_term</code>.
     */
    public static final TTermGroupToTerm T_TERM_GROUP_TO_TERM = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermGroupToTerm.T_TERM_GROUP_TO_TERM;

    /**
     * The table <code>simfolk.term_tokenized</code>.
     */
    public static final TTermTokenized T_TERM_TOKENIZED = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermTokenized.T_TERM_TOKENIZED;

    /**
     * The table <code>simfolk.term_weight_type</code>.
     */
    public static final TTermWeightType T_TERM_WEIGHT_TYPE = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermWeightType.T_TERM_WEIGHT_TYPE;

    /**
     * The table <code>simfolk.vector_algorithm_configuration</code>.
     */
    public static final TVectorAlgorithmConfiguration T_VECTOR_ALGORITHM_CONFIGURATION = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TVectorAlgorithmConfiguration.T_VECTOR_ALGORITHM_CONFIGURATION;

    /**
     * The table <code>simfolk.vector_algorithm_result</code>.
     */
    public static final TVectorAlgorithmResult T_VECTOR_ALGORITHM_RESULT = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TVectorAlgorithmResult.T_VECTOR_ALGORITHM_RESULT;

    /**
     * The table <code>simfolk.weighted_term_group</code>.
     */
    public static final TWeightedTermGroup T_WEIGHTED_TERM_GROUP = sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TWeightedTermGroup.T_WEIGHTED_TERM_GROUP;
}
