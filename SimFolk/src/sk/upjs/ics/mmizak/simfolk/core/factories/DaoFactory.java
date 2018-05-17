package sk.upjs.ics.mmizak.simfolk.core.factories;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.implementations.*;
import sk.upjs.ics.mmizak.simfolk.core.database.dao.interfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DaoFactory {

    INSTANCE;

    private IWeightedVectorDao weightedVectorDao;

    private ITermDao termDao;

    private ITermWeightTypeDao termWeightTypeDao;

    private ITermGroupDao termGroupDao;

    private IWeightedTermGroupDao weightedTermGroupDao;

    private ISongDao songDao;

    private DSLContext create;

    public IWeightedVectorDao getWeightedVectorDao() {
        if (weightedVectorDao == null) {
            weightedVectorDao = new WeightedVectorDao(getCreate(), getWeightedTermGroupDao());
        }
        return weightedVectorDao;
    }

    public ITermDao getTermDao() {
        if (termDao == null) {
            termDao = new TermDao(getCreate());
        }
        return termDao;
    }

    public IWeightedTermGroupDao getWeightedTermGroupDao() {
        if (weightedTermGroupDao == null) {
            weightedTermGroupDao = new WeightedTermGroupDao(getCreate(), getTermWeightTypeDao(), getTermGroupDao());
        }
        return weightedTermGroupDao;
    }

    public ISongDao getSongDao() {
        if (songDao == null) {
            songDao = new SongDao(getCreate());
        }
        return songDao;
    }

    public DSLContext getCreate() {

        if (create == null) {
            String userName = "simfolk";
            String pass = "simfolk";
            String url = "jdbc:mysql://localhost:3306/simfolk?serverTimezone=UTC&useSSL=false";

            Connection conn;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

                conn = DriverManager.getConnection(url, userName, pass);
                this.create = DSL.using(conn, SQLDialect.MYSQL);

            } catch (SQLException | IllegalAccessException |
                    InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return create;
    }

    public ITermGroupDao getTermGroupDao() {
        if (termGroupDao == null) {
            termGroupDao = new TermGroupDao(getCreate(), getTermDao());
        }
        return termGroupDao;
    }

    public ITermWeightTypeDao getTermWeightTypeDao() {
        if (termWeightTypeDao == null) {
            termWeightTypeDao = new TermWeightTypeDao(getCreate());
        }
        return termWeightTypeDao;
    }
}
