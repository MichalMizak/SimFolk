package sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.AlgorithmConfiguration;
import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import java.util.List;

public interface ITermWeightTypeDao {

    List<TermWeightType> getAll();

    TermWeightType getById(Integer id);

    TermWeightType saveOrEdit(TermWeightType song);

    void delete(TermWeightType song);

    TermWeightType syncId(TermWeightType termWeightType);

    TermWeightType getUnique(boolean isTFIDF, AlgorithmConfiguration.TF tf, AlgorithmConfiguration.IDF idf,
                             AlgorithmConfiguration.NonTFIDFTermWeightType nonTFIDFTermWeightType);
}
