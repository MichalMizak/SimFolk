package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.implementations;

import org.jooq.DSLContext;
import sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces.ITermWeightTypeDao;
import sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.records.TermWeightTypeRecord;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import java.util.List;

import static sk.upjs.ics.mmizak.simfolk.core.database.jooq.generated.tables.TTermWeightType.*;

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

    public TermWeightType getUnique(boolean isTFIDF, AlgorithmConfiguration.TF tf, AlgorithmConfiguration.IDF idf,
                                    AlgorithmConfiguration.NonTFIDFTermWeightType nonTFIDFTermWeightType) {

        return create.selectFrom(T_TERM_WEIGHT_TYPE)
                .where(T_TERM_WEIGHT_TYPE.ISTFIDF.eq(isTFIDF))
                .and(T_TERM_WEIGHT_TYPE.TF.eq(tf.toString()))
                .and(T_TERM_WEIGHT_TYPE.IDF.eq(idf.toString()))
                .and(T_TERM_WEIGHT_TYPE.NONTFIDFTERMWEIGHT.eq(nonTFIDFTermWeightType.toString()))
                .fetchOne(this::map);
    }

    private TermWeightType map(TermWeightTypeRecord termWeightTypeRecord) {
        if (termWeightTypeRecord.getIstfidf()) {
            return new TermWeightType(termWeightTypeRecord.getTermweighttypeid(), AlgorithmConfiguration.TF.valueOf(termWeightTypeRecord.getTf()),
                    AlgorithmConfiguration.IDF.valueOf(termWeightTypeRecord.getIdf()));
        }
        return new TermWeightType(termWeightTypeRecord.getTermweighttypeid(),
                AlgorithmConfiguration.NonTFIDFTermWeightType.valueOf(termWeightTypeRecord.getNontfidftermweight()));
    }

}
