package com.promineotech.projects.dao;

import java.sql.Connection;

public class TestDbConnection {
	
		    public static void main(String[] args) {
		        try {
		            DbConnection.getConnection();  
		            System.out.println("Database connection successful!");
		        } catch (Exception e) {
		            System.out.println(" Database connection failed: " + e.getMessage());
		        }
		    
		

        try (Connection conn = DbConnection.getConnection()) {
            System.out.println(" Database connection test passed!");
        } catch (Exception e) {
            System.out.println(" Database connection test failed: " + e.getMessage());
        }
    }
}
