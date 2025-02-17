package com.promineotech.projects.dao;

import com.promineotech.projects.dao.DbConnection;
import java.sql.Connection;
public class TestDbConnection {

	public TestDbConnection() {
        try (Connection conn = DbConnection.getConnection()) {
            System.out.println("🎉 Database connection test passed!");
        } catch (Exception e) {
            System.out.println("❌ Database connection test failed: " + e.getMessage());
        }
    }
}
