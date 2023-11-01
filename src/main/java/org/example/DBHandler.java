package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler {
    private final String url;
    private final String username;
    private final String password;
    public DBHandler(PropertiesHandler propertiesHandler) {
        this.url = propertiesHandler.GetProperty("DB_URL");
        this.username = propertiesHandler.GetProperty("DB_USERNAME");
        this.password = propertiesHandler.GetProperty("DB_PASSWORD");
    }

    public Connection Connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return conn;
    }
}
