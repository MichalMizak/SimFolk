package sk.upjs.ics.mmizak.simfolk.core.dao;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.SongDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.TermDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.WeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.implementations.WeightedVectorDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ISongDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.ITermDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedTermGroupDao;
import sk.upjs.ics.mmizak.simfolk.core.dao.interfaces.IWeightedVectorDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static sk.upjs.ics.mmizak.simfolk.core.jooq.generated.tables.TSong.T_SONG;

public enum DaoFactory {

    INSTANCE;

    private IWeightedVectorDao weightedVectorDao;

    private ITermDao termDao;

    private IWeightedTermGroupDao termGroupDao;

    private ISongDao songDao;
    private DSLContext create;

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

    public ISongDao getSongDao() {
        if (songDao == null) {
            return new SongDao(getCreate());
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
}
