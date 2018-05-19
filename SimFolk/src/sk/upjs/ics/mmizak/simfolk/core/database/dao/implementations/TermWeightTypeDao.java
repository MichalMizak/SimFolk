package sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.ITermWeightTypeDao;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.TermWeightTypeRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermWeightType.T_TERM_WEIGHT_TYPE;
import static sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration.*;

public class TermWeightTypeDao implements ITermWeightTypeDao {

    private final DSLContext create;

    public TermWeightTypeDao(DSLContext create) {
        this.create = create;
    }

    @Override
    public List<TermWeightType> getAll() {
        return create.selectFrom(T_TERM_WEIGHT_TYPE).fetch(this::map);
    }

    @Override
    public TermWeightType getById(Integer id) {
        return create.selectFrom(T_TERM_WEIGHT_TYPE).where(T_TERM_WEIGHT_TYPE.TERMWEIGHTTYPEID.eq(id)).fetchOne(this::map);
    }

    @Override
    public TermWeightType saveOrEdit(TermWeightType termWeightType) {

        if (termWeightType.getId() == null) {

            TermWeightTypeRecord typeRecord = create
                    .insertInto(T_TERM_WEIGHT_TYPE)
                    .columns(T_TERM_WEIGHT_TYPE.ISTFIDF, T_TERM_WEIGHT_TYPE.TF,
                            T_TERM_WEIGHT_TYPE.IDF, T_TERM_WEIGHT_TYPE.NONTFIDFTERMWEIGHT)
                    .values(termWeightType.isTFIDF(), termWeightType.getTf().toString(),
                            termWeightType.getIdf().toString(), termWeightType.getNonTFIDFTermWeight().toString())
                    .onDuplicateKeyIgnore()
                    .returning(T_TERM_WEIGHT_TYPE.TERMWEIGHTTYPEID)
                    .fetchOne();

            if (typeRecord != null) {
                termWeightType.setId(typeRecord.getTermweighttypeid());
            } else {
                termWeightType = getUnique(termWeightType.isTFIDF(), termWeightType.getTf(),
                        termWeightType.getIdf(), termWeightType.getNonTFIDFTermWeight());
            }
        } else {
            create.update(T_TERM_WEIGHT_TYPE)
                    .set(T_TERM_WEIGHT_TYPE.ISTFIDF, termWeightType.isTFIDF())
                    .set(T_TERM_WEIGHT_TYPE.TF, termWeightType.getTf().toString())
                    .set(T_TERM_WEIGHT_TYPE.IDF, termWeightType.getIdf().toString())
                    .set(T_TERM_WEIGHT_TYPE.NONTFIDFTERMWEIGHT, termWeightType.getNonTFIDFTermWeight().toString())
                    .where(T_TERM_WEIGHT_TYPE.TERMWEIGHTTYPEID.eq(termWeightType.getId()))
                    .execute();
        }

        return termWeightType;
    }

    @Override
    public void delete(TermWeightType termWeightType) {
        create.deleteFrom(T_TERM_WEIGHT_TYPE).where(T_TERM_WEIGHT_TYPE.TERMWEIGHTTYPEID.eq(termWeightType.getId()));
    }

    /**
     * Watch out, tightly coupled with unique table values, but doesn't use getUnique because of performance
     * @param termWeightType
     * @return
     */
    @Override
    public TermWeightType syncId(TermWeightType termWeightType) {
        Integer id = create.selectOne().from(T_TERM_WEIGHT_TYPE)
                .where(T_TERM_WEIGHT_TYPE.ISTFIDF.eq(termWeightType.isTFIDF()))
                .and(T_TERM_WEIGHT_TYPE.TF.eq(termWeightType.getTf().toString()))
                .and(T_TERM_WEIGHT_TYPE.IDF.eq(termWeightType.getIdf().toString()))
                .and(T_TERM_WEIGHT_TYPE.NONTFIDFTERMWEIGHT.eq(termWeightType.getNonTFIDFTermWeight().toString()))
                .fetchOne(T_TERM_WEIGHT_TYPE.TERMWEIGHTTYPEID);
        termWeightType.setId(id);
        return termWeightType;
    }

    @Override
    public TermWeightType getUnique(boolean isTFIDF, TF tf, IDF idf,
                                    NonTFIDFTermWeightType nonTFIDFTermWeightType) {
        return create.selectFrom(T_TERM_WEIGHT_TYPE)
                .where(T_TERM_WEIGHT_TYPE.ISTFIDF.eq(isTFIDF))
                .and(T_TERM_WEIGHT_TYPE.TF.eq(tf.toString()))
                .and(T_TERM_WEIGHT_TYPE.IDF.eq(idf.toString()))
                .and(T_TERM_WEIGHT_TYPE.NONTFIDFTERMWEIGHT.eq(nonTFIDFTermWeightType.toString()))
                .fetchOne(this::map);
    }

    private TermWeightType map(TermWeightTypeRecord termWeightTypeRecord) {
        if (termWeightTypeRecord.getIstfidf()) {
            return new TermWeightType(termWeightTypeRecord.getTermweighttypeid(), TF.valueOf(termWeightTypeRecord.getTf()),
                    IDF.valueOf(termWeightTypeRecord.getIdf()));
        }
        return new TermWeightType(termWeightTypeRecord.getTermweighttypeid(),
                NonTFIDFTermWeightType.valueOf(termWeightTypeRecord.getNontfidftermweight()));
    }

}
