package org.example;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    private final String url;
    private final String username;
    private final String password;
    public DBHandler(PropertiesHandler propertiesHandler) {
        this.url = propertiesHandler.GetProperty("DB_URL");
        this.username = propertiesHandler.GetProperty("DB_USERNAME");
        this.password = propertiesHandler.GetProperty("DB_PASSWORD");

        try {
            CreateTable();
            System.out.println("Database setup successfully");
        } catch (Exception e) {
            System.out.println("Error in database setup: " + e);
        }
    }

    public Connection CreateConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (SQLException e) {
            System.out.println("Error in connecting to DB: " + e.getMessage());
        }
        return conn;

    }

    public void CloseConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in closing DB connectionL " + e);
        }
    }

    public void CreateTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS bobux_accounts(" +
                "id serial PRIMARY KEY," +
                "discord_id BIGINT UNIQUE NOT NULL," +
                "username VARCHAR (255) NOT NULL," +
                "amount INTEGER" +
                ");";
        try {
            Connection conn = CreateConnection();
            PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ps.close();
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println("Error in creating table: " + e);
        }
    }

    public void AddEntryBobuxAccounts(BobuxAccount acc) {
        String SQL = "INSERT INTO bobux_accounts(discord_id, username, amount) VALUES(?, ?, ?)";
        try {
            Connection conn = CreateConnection();
            PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, acc.DiscordID);
            ps.setString(2, acc.Username);
            ps.setInt(3, 0);

            int affectedRows = ps.executeUpdate();
            System.out.println("Successfully inserted entry with discord id " + acc.DiscordID);
        } catch (SQLException e) {
            System.out.println("Error in adding entry to DB: " + e);
        }
    }

    public boolean CheckIfExistsBobuxAccounts(BobuxAccount acc) {
        try {
            Connection conn = CreateConnection();
            String SQL = "SELECT 1 FROM bobux_accounts WHERE discord_id = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setLong(1, acc.DiscordID);
            ResultSet rs = ps.executeQuery();

            // Exists if at least 1 row is returned
            if (!rs.next()) {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error in CheckIfExistsBobuxAccounts: " + e.getMessage());
            return false;
        }
        return true;
    }
    public boolean UpdateEntryBobuxAccounts(BobuxAccount acc, int amount) {
        // Check if user exists, if not then add
        if (!CheckIfExistsBobuxAccounts(acc)) {
            System.out.println("Entry with discord id + " + acc.DiscordID + "did not exist, adding now.");
            AddEntryBobuxAccounts(acc);
        }
        try {
            Connection conn = CreateConnection();
            String SQL = "UPDATE bobux_accounts SET amount = amount + ? WHERE discord_id = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, amount);
            ps.setLong(2, acc.DiscordID);
            ps.executeUpdate();

            System.out.println("Successfully updated DB");
        } catch (SQLException e) {
            System.out.println("Error in updating table: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<BobuxAccount> GetTableBobuxAccounts() {
        List<BobuxAccount> bobuxAccounts = new ArrayList<BobuxAccount>();
        try {
            Connection conn = CreateConnection();
            String SQL = "SELECT discord_id, username, amount FROM bobux_accounts ORDER BY amount DESC";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                BobuxAccount acc = new BobuxAccount(
                        rs.getLong("discord_id"),
                        rs.getString("username"),
                        rs.getInt("amount")
                );
                bobuxAccounts.add(acc);
            }
        } catch (SQLException e) {
            System.out.println("Error in getting table: " + e.getMessage());
        }
        return bobuxAccounts;
    }




}
