package com.promineotech.projects.service;

import java.util.List;
import com.promineotech.projects.dao.ProjectDao;
import com.promineotech.projects.entity.Project;

public class ProjectService {
    private ProjectDao projectDao = new ProjectDao();

    
    public Project addProject(Project project) {
        return projectDao.insertProject(project);
    }

    
    public List<Project> fetchAllProjects() {
        return projectDao.fetchAllProjects();
    }

    
    public boolean updateProject(Project project) {
        return projectDao.updateProject(project);
    }

    
    public boolean deleteProject(int projectId) {
        return projectDao.deleteProject(projectId);
    }
}
