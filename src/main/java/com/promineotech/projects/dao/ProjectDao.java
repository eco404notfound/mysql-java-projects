package com.promineotech.projects.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    project.setProjectId(rs.getInt(1));
                }
            }

            System.out.println(" Project inserted successfully: " + project); 
            return project;
        } catch (SQLException e) {
            throw new DbException("Error inserting project", e);
        }
    }

    public List<Project> fetchAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT project_id, project_name FROM project ORDER BY project_name";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new DbException("Error fetching projects", e);
        }
        return projects;
    }

    public Optional<Project> fetchProjectById(int projectId) {
        String sql = "SELECT * FROM project WHERE project_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false); 

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, projectId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Project project = new Project();
                    project.setProjectId(rs.getInt("project_id"));
                    project.setProjectName(rs.getString("project_name"));
                    project.setEstimatedHours(rs.getBigDecimal("estimated_hours"));
                    project.setActualHours(rs.getBigDecimal("actual_hours"));
                    project.setDifficulty(rs.getInt("difficulty"));
                    project.setNotes(rs.getString("notes"));

                    project.setCategories(fetchCategories(conn, projectId));
                    project.setMaterials(fetchMaterials(conn, projectId));
                    project.setSteps(fetchSteps(conn, projectId));

                    conn.commit(); 
                    return Optional.of(project);
                }
            } catch (SQLException e) {
                conn.rollback();  
                throw new DbException("Error fetching project", e);
            } finally {
                conn.setAutoCommit(true);  
            }
        } catch (SQLException e) {
            throw new DbException("Error establishing database connection", e);
        }
        return Optional.empty();
    }

    public boolean modifyProjectDetails(Project project) {
        String sql = "UPDATE project SET project_name = ?, estimated_hours = ?, actual_hours = ?, " +
                     "difficulty = ?, notes = ? WHERE project_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, project.getProjectName());
            stmt.setBigDecimal(2, project.getEstimatedHours());
            stmt.setBigDecimal(3, project.getActualHours());
            stmt.setInt(4, project.getDifficulty());
            stmt.setString(5, project.getNotes());
            stmt.setInt(6, project.getProjectId());

            int updatedRows = stmt.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException e) {
            throw new DbException("Error updating project details", e);
        }
    }

    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM project WHERE project_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            int deletedRows = stmt.executeUpdate();
            return deletedRows == 1;
        } catch (SQLException e) {
            throw new DbException("Error deleting project", e);
        }
    }

    private List<String> fetchCategories(Connection conn, int projectId) throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT c.category_name FROM category c " +
                     "INNER JOIN project_category pc ON c.category_id = pc.category_id " +
                     "WHERE pc.project_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        }
        return categories;
    }

    private List<String> fetchMaterials(Connection conn, int projectId) throws SQLException {
        List<String> materials = new ArrayList<>();
        String sql = "SELECT material_name, num_required FROM material WHERE project_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                materials.add(rs.getString("material_name") + " (Qty: " + rs.getInt("num_required") + ")");
            }
        }
        return materials;
    }

    private List<String> fetchSteps(Connection conn, int projectId) throws SQLException {
        List<String> steps = new ArrayList<>();
        String sql = "SELECT step_order, step_text FROM step WHERE project_id = ? ORDER BY step_order";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                steps.add("Step " + rs.getInt("step_order") + ": " + rs.getString("step_text"));
            }
        }
        return steps;
    }
}
