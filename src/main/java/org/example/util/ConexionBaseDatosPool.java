package org.example.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBaseDatosPool {
    private static String url ="jdbc:mysql://localhost:3306/db_curso_java?serverTimezone=UTC";
    private static String user = "root";
    private static String password = "marcos1009F!";
    private static BasicDataSource pool;

    public static BasicDataSource getIntanse() {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(password);
            pool.setInitialSize(3);
            pool.setMinIdle(5);
            pool.setMaxIdle(10);
            pool.setMaxTotal(10);

        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getIntanse().getConnection();
    }
}
