package com.promineotech.projects.service;

import java.util.List;
import com.promineotech.projects.dao.ProjectDao;
import com.promineotech.projects.entity.Project;
import com.promineotech.projects.exception.DbException;

public class ProjectService {
    private ProjectDao projectDao = new ProjectDao();

    public List<Project> fetchAllProjects() { 
        return projectDao.fetchAllProjects(); 
    }

    public Project addProject(Project project) { 
        return projectDao.insertProject(project);  
    }

    public Project fetchProjectById(int projectId) {
        return projectDao.fetchProjectById(projectId)
                         .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " does not exist."));
    }

    public void modifyProjectDetails(Project project) {
        boolean updated = projectDao.modifyProjectDetails(project);
        if (!updated) {
            throw new DbException("Project with ID " + project.getProjectId() + " does not exist.");
        }
    }

    public void deleteProject(int projectId) {
        boolean deleted = projectDao.deleteProject(projectId);
        if (!deleted) {
            throw new DbException("Project with ID " + projectId + " does not exist.");
        }
    }
}
