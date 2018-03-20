package sk.upjs.ics.mmizak.simfolk.core.dao;

import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.TermDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.WeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.WeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedVectorDao;

public enum DaoFactory {

    INSTANCE;

    private IWeightedVectorDao weightedVectorDao;

    private ITermDao termDao;

    private IWeightedTermGroupDao termGroupDao;

    public IWeightedVectorDao getWeightedVectorDao() {
        if (weightedVectorDao == null) {
            weightedVectorDao = new WeightedVectorDao();
        }
        return weightedVectorDao;
    }

    public ITermDao getTermDao() {
        if (termDao == null) {
            termDao = new TermDao();
        }
        return termDao;
    }

    public IWeightedTermGroupDao getTermGroupDao() {
        if (termGroupDao == null) {
            termGroupDao = new WeightedTermGroupDao();
        }
        return termGroupDao;
    }
}
