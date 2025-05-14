package com.codewithdemis.db;

import com.codewithdemis.config.DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;

    public Database() {
        try {
            this.connection = DriverManager.getConnection(
                    DbConfig.url,
                    DbConfig.user,
                    DbConfig.password
            );

        } catch (SQLException ex) {
            throw new RuntimeException("Unable to connect...", ex);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                instance = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
