package com.promineotech.projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.promineotech.projects.exception.DbException;

public class DbConnection {

   
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String SCHEMA = "projects";  
    private static final String USER = "projects";      
    private static final String PASSWORD = "1818";  

    public static Connection getConnection() {
        String uri = "jdbc:mysql://" + HOST + ":" + PORT + "/" + SCHEMA + "?useSSL=false&allowPublicKeyRetrieval=true";
        try {
            Connection connection = DriverManager.getConnection(uri, USER, PASSWORD);
            System.out.println("Connection successful!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            throw new DbException("Unable to connect to the database", e);
        }
    }
}
