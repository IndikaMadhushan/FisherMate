package org.example.fishermatenew.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnection {

    public Connection conn;
    //query to create the database automatically
    String createDB = "CREATE DATABASE IF NOT EXISTS fishermate";

    //query for create the log in table automatically
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
            "role VARCHAR(255), " +
            "port VARCHAR(255)" +
            ");";

    //query to create the weather_data table automatically
    String createWeatherDataTable = "CREATE TABLE IF NOT EXISTS weather_data(" +
            "id INT AUTO_INCREMENT PRIMARY KEY,"+
            "location VARCHAR(100)," +
            "forecast_time TIME," +
            "wind_speed DOUBLE," +
            "rain_probability DOUBLE," +
            "visibility DOUBLE," +
            "weather_condition VARCHAR(100)"+
    ")";

    //create table for storing boatrides
    String createhistoryTable = "CREATE TABLE IF NOT EXISTS history (" +

            "date DATETIME NOT NULL, " +
            "location VARCHAR(255) NOT NULL, " +
            "time DATETIME NOT NULL, " +
            "crewMembers INT NOT NULL" +
            ");";



    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/fishermate";
        String username = "root";
        String password = "";

        try {
            // Use the updated MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");

            Statement stmt = conn.createStatement();
            //create the database
            stmt.executeUpdate(createDB);
            System.out.println("database created successfully");
            stmt.executeUpdate(sql);
            System.out.println("Login table created successfully!");
            stmt.executeUpdate(createWeatherDataTable);
            System.out.println("weather table created successfully");
            stmt.executeUpdate(createhistoryTable);
            System.out.println("history table created successfully");


        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        return conn;
    }
}
