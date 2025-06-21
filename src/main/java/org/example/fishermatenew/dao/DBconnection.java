package org.example.fishermatenew.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    public Connection conn;

    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/fishermate";
        String username = "root";
        String password = "";

        try {
            // Use the updated MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        return conn;
    }
}
