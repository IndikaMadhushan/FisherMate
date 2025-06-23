package org.example.fishermatenew.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnection {

    public Connection conn;

    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/fishermate";
        String username = "root";
        String password = "";

        String sql = "CREATE TABLE IF NOT EXISTS login (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "firstname VARCHAR(255) NOT NULL, " +
                "lastname VARCHAR(255) NOT NULL, " +
                "username VARCHAR(255) UNIQUE NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "image VARCHAR(255), " +
                "crew VARCHAR(255), " +
                "boat VARCHAR(255), " +
                "license VARCHAR(255), " +
                "port VARCHAR(255)" +
                ");";


        try {
            // Use the updated MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Login table created successfully!");

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        return conn;
    }
}
