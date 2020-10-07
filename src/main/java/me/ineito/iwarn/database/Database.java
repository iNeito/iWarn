package me.ineito.iwarn.database;

import me.ineito.iwarn.Main;

import java.sql.*;

public class Database {


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(Main.getConnectionUrl());
        }catch (SQLException se) {
            System.out.println("There was an error connecting to the database");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("There was an error finding h2 driver");
        }

        return connection;
    }


    public static void initDatabase() {
        initializeWarningsTable();
    }

    private static void initializeWarningsTable() {
        Connection connection = getConnection();
        String query = "CREATE TABLE IF NOT EXISTS Warnings(WarningID int NOT NULL IDENTITY(1,1), PlayerUUID varchar, AdminUUID varchar, Reason varchar, DateAdded Date DEFAULT SYSDATE)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println("There was an error creating warnings table");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
