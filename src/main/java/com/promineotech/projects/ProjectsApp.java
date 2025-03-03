package com.promineotech.projects;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import com.promineotech.projects.entity.Project;
import com.promineotech.projects.service.ProjectService;
import java.math.BigDecimal;

public class ProjectsApp {
    private Scanner scanner = new Scanner(System.in);
    private ProjectService projectService = new ProjectService();
    private Project curProject;

    private List<String> operations = List.of(
        "1) Add a project",
        "2) List all projects",
        "3) Select a project",
        "4) Update project details",
        "5) Delete a project"
    );

    public static void main(String[] args) {
        new ProjectsApp().processUserSelections();
    }

    private void processUserSelections() {
        boolean done = false;

        while (!done) {
            try {
                int selection = getUserSelection();
                switch (selection) {
                    case -1:
                        done = exitMenu();
                        break;
                    case 1:
                        createProject();
                        break;
                    case 2:
                        listProjects();
                        break;
                    case 3:
                        selectProject();
                        break;
                    case 4:
                        updateProjectDetails();
                        break;
                    case 5:
                        deleteProject();
                        break;
                    default:
                        System.out.println("\nInvalid selection. Try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e + " Try again.");
                e.printStackTrace();
            }
        }
    }

    private void createProject() {
        String projectName = getStringInput("Enter the project name");
        BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
        BigDecimal actualHours = getDecimalInput("Enter the actual hours");
        Integer difficulty = getIntInput("Enter project difficulty (1-5)");
        String notes = getStringInput("Enter project notes");

        Project project = new Project();
        project.setProjectName(projectName);
        project.setEstimatedHours(estimatedHours);
        project.setActualHours(actualHours);
        project.setDifficulty(difficulty);
        project.setNotes(notes);

        Project dbProject = projectService.addProject(project);
        System.out.println(" Project created: " + dbProject);
    }

    private void listProjects() {
        List<Project> projects = projectService.fetchAllProjects();
        System.out.println("\nProjects:");
        projects.forEach(project -> System.out.println("  " + project.getProjectId() + ": " + project.getProjectName()));
    }

    private void selectProject() {
        listProjects();
        Integer projectId = getIntInput("Enter project ID to select");

        if (projectId == null) {
            System.out.println(" Invalid input. Please enter a numeric project ID.");
            return;
        }

        try {
            curProject = projectService.fetchProjectById(projectId);
            System.out.println(" Project selected: " + curProject);
            displayProjectDetails();
        } catch (Exception e) {
            System.out.println(" Invalid project ID selected.");
        }
    }

    private void displayProjectDetails() {
        System.out.println("\nProject Details:");
        System.out.println("Name: " + curProject.getProjectName());
        System.out.println("Estimated Hours: " + curProject.getEstimatedHours());
        System.out.println("Actual Hours: " + curProject.getActualHours());
        System.out.println("Difficulty: " + curProject.getDifficulty());
        System.out.println("Notes: " + curProject.getNotes());
    }

    private void updateProjectDetails() {
        if (curProject == null) {
            System.out.println("\nPlease select a project first.");
            return;
        }

        System.out.println("\nUpdating project details. Press Enter to keep existing values.");
        String projectName = getStringInput("Enter new project name (" + curProject.getProjectName() + ")");
        BigDecimal estimatedHours = getDecimalInput("Enter new estimated hours (" + curProject.getEstimatedHours() + ")");
        BigDecimal actualHours = getDecimalInput("Enter new actual hours (" + curProject.getActualHours() + ")");
        Integer difficulty = getIntInput("Enter new project difficulty (" + curProject.getDifficulty() + ")");
        String notes = getStringInput("Enter new project notes (" + curProject.getNotes() + ")");

        Project updatedProject = new Project();
        updatedProject.setProjectId(curProject.getProjectId());
        updatedProject.setProjectName(projectName != null ? projectName : curProject.getProjectName());
        updatedProject.setEstimatedHours(estimatedHours != null ? estimatedHours : curProject.getEstimatedHours());
        updatedProject.setActualHours(actualHours != null ? actualHours : curProject.getActualHours());
        updatedProject.setDifficulty(difficulty != null ? difficulty : curProject.getDifficulty());
        updatedProject.setNotes(notes != null ? notes : curProject.getNotes());

        projectService.modifyProjectDetails(updatedProject);
        curProject = projectService.fetchProjectById(curProject.getProjectId());
        System.out.println("\nProject updated successfully!");
        displayProjectDetails();
    }

    private void deleteProject() {
        listProjects();
        Integer projectId = getIntInput("Enter the project ID to delete");

        if (projectId == null) {
            System.out.println(" Invalid input. Please enter a numeric project ID.");
            return;
        }

        try {
            projectService.deleteProject(projectId);
            System.out.println("\nProject deleted successfully.");

            if (curProject != null && curProject.getProjectId().equals(projectId)) {
                curProject = null;
            }
        } catch (Exception e) {
            System.out.println("Error: Unable to delete project. " + e.getMessage());
        }
    }

    private boolean exitMenu() {
        System.out.println("Exiting...");
        return true;
    }

    private int getUserSelection() {
        printOperations();
        Integer input = getIntInput("Enter a menu selection");
        return Objects.isNull(input) ? -1 : input;
    }

    private void printOperations() {
        System.out.println("\nAvailable Options:");
        operations.forEach(System.out::println);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();
        return input.isBlank() ? null : input.trim();
    }

    private Integer getIntInput(String prompt) {
        String input = getStringInput(prompt);
        return Objects.isNull(input) ? null : Integer.valueOf(input);
    }

    private BigDecimal getDecimalInput(String prompt) {
        String input = getStringInput(prompt);
        return Objects.isNull(input) ? null : new BigDecimal(input).setScale(2);
    }
}
