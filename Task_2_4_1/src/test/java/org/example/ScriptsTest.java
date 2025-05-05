package org.example;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.example.model.BuildResult;
import org.example.model.Student;
import org.example.service.BuildChecker;
import org.example.service.HTMLGenerator;
import org.example.service.RepositoryCloner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the build checking system.
 */
public class ScriptsTest {

    private Map<String, Student> students;
    private List<String> tasks;

    @BeforeEach
    void setUp() {
        deleteRepositories();
        try {
            // Load configuration
            GroovyScriptEngine engine = new GroovyScriptEngine("src/main/resources/scripts");
            Binding binding = new Binding();
            engine.run("config.groovy", binding);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> config = (Map<String, Object>) binding.getVariable("config");
            
            @SuppressWarnings("unchecked")
            Map<String, Map<String, String>> studentsConfig = (Map<String, Map<String, String>>) config.get("students");
            @SuppressWarnings("unchecked")
            List<String> tasksList = (List<String>) config.get("tasks");

            // Convert configuration to Student objects
            students = new LinkedHashMap<>();
            for (Map.Entry<String, Map<String, String>> entry : studentsConfig.entrySet()) {
                String id = entry.getKey();
                Map<String, String> studentInfo = entry.getValue();
                students.put(id, new Student(id, studentInfo.get("username"), studentInfo.get("repository")));
            }
            tasks = tasksList;
        } catch (Exception e) {
            fail("Failed to load configuration: " + e.getMessage());
        }
    }

    /**
     * Deletes the repositories directory.
     */
    private void deleteRepositories() {
        File currentDir = new File("./repositories");
        if (deleteDirectory(currentDir)) {
            System.out.println("Directory deleted successfully.");
        } else {
            System.out.println("Failed to delete directory.");
        }
    }

    /**
     * Recursively deletes a directory and its contents.
     *
     * @param dir - directory to delete
     * @return true if successful
     */
    public static boolean deleteDirectory(File dir) {
        if (!dir.exists()) {
            return false;
        }

        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDirectory(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    @Test
    @DisplayName("Repository cloning test")
    void testRepositoryCloning() {
        RepositoryCloner cloner = new RepositoryCloner();
        cloner.cloneRepositories(students);

        assertTrue(new File("./repositories/Artem-MilkyWay").exists());
        assertTrue(new File("./repositories/Andrew-Vetrov").exists());
        assertTrue(new File("./repositories/Hom4ikTop4ik").exists());
    }

    @Test
    @DisplayName("Build checking test")
    void testBuildChecking() {
        RepositoryCloner cloner = new RepositoryCloner();
        cloner.cloneRepositories(students);

        BuildChecker checker = new BuildChecker();
        Map<String, Map<String, BuildResult>> results = checker.evaluateBuilds(students, tasks);

        // Check if all tasks are present
        for (String task : tasks) {
            assertTrue(results.containsKey(task), "Missing results for task: " + task);
        }

        // Check if all students are present in Task_1_1_1
        Map<String, BuildResult> taskResults = results.get("Task_1_1_1");
        assertNotNull(taskResults, "No results for Task_1_1_1");
        for (Student student : students.values()) {
            assertTrue(taskResults.containsKey(student.getId()), 
                "Missing results for student: " + student.getId());
        }

        // Check if all required fields are present
        BuildResult result = taskResults.get("Artem-MilkyWay");
        assertNotNull(result, "No build result for Artem-MilkyWay");
        assertNotNull(result.getBuildStatus(), "Missing build status");
        assertNotNull(result.getTestStatus(), "Missing test status");
        assertNotNull(result.getJavadocStatus(), "Missing javadoc status");
    }

    @Test
    @DisplayName("HTML generation test")
    void testHtmlGeneration() {
        RepositoryCloner cloner = new RepositoryCloner();
        cloner.cloneRepositories(students);

        BuildChecker checker = new BuildChecker();
        Map<String, Map<String, BuildResult>> results = checker.evaluateBuilds(students, tasks);

        HTMLGenerator generator = new HTMLGenerator();
        generator.generateReport(results);

        String filePath = "./result.html";
        StringBuilder html = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }
        } catch (IOException e) {
            fail("Failed to read HTML file: " + e.getMessage());
        }

        String htmlContent = html.toString();
        assertTrue(htmlContent.contains("table"), "HTML should contain tables");
        
        // Check if all tasks are present in the HTML
        for (String task : tasks) {
            assertTrue(htmlContent.contains(task), 
                "HTML should contain results for task: " + task);
        }
    }
}
