package org.example.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatosConnection {
    private static String url ="jdbc:mysql://localhost:3306/db_curso_java?serverTimezone=UTC";
    private static String user = "root";
    private static String password = "marcos1009F!";
    private static Connection connection;

    public static Connection getIntanse() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

}
