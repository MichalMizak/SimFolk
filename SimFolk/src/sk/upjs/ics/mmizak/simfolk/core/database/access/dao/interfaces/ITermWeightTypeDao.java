package sk.upjs.ics.mmizak.simfolk.core.database.access.dao.interfaces;

import sk.upjs.ics.mmizak.simfolk.core.vector.space.entities.weighting.TermWeightType;

import java.util.List;

public interface ITermWeightTypeDao {

    List<TermWeightType> getAll();

    TermWeightType getById(Integer id);

    TermWeightType saveOrEdit(TermWeightType song);

    void delete(TermWeightType song);
}
