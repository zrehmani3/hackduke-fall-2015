package org.hackduke.db;

import org.hackduke.ConnectionMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDb {
    protected ResultSet result;
    private Connection connection;

    private Connection connection() {
        if (connection == null) {
            try {
                connection = ConnectionMap.getConnection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return connection;
    }

    protected boolean executeQuery(String st) {
        try {
            result = connection().prepareStatement(st).executeQuery();
            if (result.first()) {
                fetch();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void executeUpdate(String st) {
        try {
            connection().prepareStatement(st).executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean next() {
        try {
            if (result.next()) {
                fetch();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void close() {
        try {
            if (result != null) result.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract void fetch() throws SQLException;
}
