package com.promineotech.projects.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private Integer projectId;
    private String projectName;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
    private Integer difficulty;
    private String notes;

   
    private List<String> categories = new ArrayList<>();
    private List<String> materials = new ArrayList<>();
    private List<String> steps = new ArrayList<>();

    
    public Project() {}

    
    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public BigDecimal getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(BigDecimal estimatedHours) { this.estimatedHours = estimatedHours; }

    public BigDecimal getActualHours() { return actualHours; }
    public void setActualHours(BigDecimal actualHours) { this.actualHours = actualHours; }

    public Integer getDifficulty() { return difficulty; }
    public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public List<String> getMaterials() { return materials; }
    public void setMaterials(List<String> materials) { this.materials = materials; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    @Override
    public String toString() {
        return "Project [ID=" + projectId + ", Name=" + projectName +
                ", Estimated Hours=" + estimatedHours + ", Actual Hours=" + actualHours +
                ", Difficulty=" + difficulty + ", Notes=" + notes +
                ", Categories=" + categories + ", Materials=" + materials +
                ", Steps=" + steps + "]";
    }
}
