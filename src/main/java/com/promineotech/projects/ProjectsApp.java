package com.promineotech.projects;
import java.sql.Connection;
import com.promineotech.projects.dao.DbConnection;
public class ProjectsApp {

	public ProjectsApp() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = DbConnection.getConnection();
		try {
	            if (connection != null) {
	                connection.close();
	                System.out.println("Connection closed successfully.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
