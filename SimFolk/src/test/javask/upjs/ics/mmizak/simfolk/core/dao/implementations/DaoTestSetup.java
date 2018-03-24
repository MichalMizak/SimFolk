package test.javask.upjs.ics.mmizak.simfolk.core.dao.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoTestSetup {

    private DaoTestSetup() {
    }

    public static Connection createConnection() {
        String userName = "simfolk";
        String pass = "simfolk";
        String url = "jdbc:mysql://localhost:3306/simfolk?serverTimezone=UTC&useSSL=false";
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, userName, pass);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
