/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import java.sql.*;
import railcab.Config;

/**
 *
 * @author Administrator
 */
public class Dbmanager {

    public static final String JDBC_EXCEPTION = "JDBC Exception: ";
    public static final String SQL_EXCEPTION = "SQL Exception: ";
    public static Connection connection;

    /**
     * Open database connection
     */
    public static void connect() {
        Config.load();
        String url = "jdbc:mysql://" + Config.get("dbhost") + ":3306/" + Config.get("db");
        String driver = "com.mysql.jdbc.Driver";
        String userName = Config.get("dbuser");
        String password = Config.get("dbpassword");
        
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url,userName,password);
            System.out.println("Connected to the database");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("NO CONNECTION =(");
        }
        
    }

    /**
     * Close database connection
     */
    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Executes a query without result.
     * @param query, the SQl query
     */
    public static void executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e);
        }
    }

    /**
     * Executes a query with result.
     * @param query, the SQL query
     */
    public static ResultSet doQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e);
        }
        return result;
    }

    /**
     * Executes a query with result.
     * @param query, the SQL query
     */
    public static ResultSet insertQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e);
        }
        return result;
    }
}