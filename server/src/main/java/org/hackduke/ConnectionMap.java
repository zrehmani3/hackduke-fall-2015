package org.hackduke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMap {

    private ConnectionMap() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/foodbank?user=root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
