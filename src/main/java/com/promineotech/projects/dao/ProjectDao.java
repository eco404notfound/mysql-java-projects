package com.promineotech.projects.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.promineotech.projects.entity.Project;
import com.promineotech.projects.exception.DbException;

public class ProjectDao {
    public Project insertProject(Project project) {
        String sql = "INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, project.getProjectName());
            stmt.setBigDecimal(2, project.getEstimatedHours());
            stmt.setBigDecimal(3, project.getActualHours());
            stmt.setInt(4, project.getDifficulty());
            stmt.setString(5, project.getNotes());

            stmt.executeUpdate();
            System.out.println("✅ Project inserted successfully.");
        } catch (SQLException e) {
            throw new DbException("Error inserting project", e);
        }
        return project;
    }

    public List<Project> fetchAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM project";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setEstimatedHours(rs.getBigDecimal("estimated_hours"));
                project.setActualHours(rs.getBigDecimal("actual_hours"));
                project.setDifficulty(rs.getInt("difficulty"));
                project.setNotes(rs.getString("notes"));
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new DbException("Error fetching projects", e);
        }
        return projects;
    }
}
