package com.promineotech.projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.promineotech.projects.entity.Project;
import com.promineotech.projects.exception.DbException;
import com.promineotech.projects.service.ProjectService;

public class ProjectsApp {
    private Scanner scanner = new Scanner(System.in);
    private ProjectService projectService = new ProjectService();

    private List<String> operations = List.of(
        "1) Add a project",
        "2) List all projects"
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
                    default:
                        System.out.println("\nInvalid selection. Try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e + " Try again.");
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
        System.out.println("✅ Project created: " + dbProject);
    }

    private void listProjects() {
        List<Project> projects = projectService.fetchAllProjects();
        projects.forEach(project -> System.out.println("🔹 " + project));
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

    private Integer getIntInput(String prompt) {
        String input = getStringInput(prompt);
        return Objects.isNull(input) ? null : Integer.valueOf(input);
    }

    private BigDecimal getDecimalInput(String prompt) {
        String input = getStringInput(prompt);
        return Objects.isNull(input) ? null : new BigDecimal(input).setScale(2);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();
        return input.isBlank() ? null : input.trim();
    }

    private void printOperations() {
        System.out.println("\nAvailable Options:");
        operations.forEach(op -> System.out.println("  " + op));
    }
}

