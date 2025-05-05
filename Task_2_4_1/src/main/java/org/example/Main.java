package org.example;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.example.model.Student;
import org.example.service.BuildChecker;
import org.example.service.HTMLGenerator;
import org.example.service.RepositoryCloner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Main application entry point.
 */
public class Main {
    /**
     * Main method that initializes and runs the build checking process.
     *
     * @param args - command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Redirect console output
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            System.setErr(new PrintStream(new ByteArrayOutputStream()));

            // Load configuration
            GroovyScriptEngine engine = new GroovyScriptEngine("src/main/resources/scripts");
            Binding binding = new Binding();
            engine.run("config.groovy", binding);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> config = (Map<String, Object>) binding.getVariable("config");
            
            @SuppressWarnings("unchecked")
            Map<String, Map<String, String>> studentsConfig = (Map<String, Map<String, String>>) config.get("students");
            @SuppressWarnings("unchecked")
            List<String> tasks = (List<String>) config.get("tasks");

            // Convert configuration to Student objects
            Map<String, Student> students = new LinkedHashMap<>();
            for (Map.Entry<String, Map<String, String>> entry : studentsConfig.entrySet()) {
                String id = entry.getKey();
                Map<String, String> studentInfo = entry.getValue();
                students.put(id, new Student(id, studentInfo.get("username"), studentInfo.get("repository")));
            }

            // Clone repositories
            RepositoryCloner cloner = new RepositoryCloner();
            cloner.cloneRepositories(students);

            // Check builds
            BuildChecker checker = new BuildChecker();
            Map<String, Map<String, org.example.model.BuildResult>> results = checker.evaluateBuilds(students, tasks);

            // Generate HTML report
            HTMLGenerator generator = new HTMLGenerator();
            generator.generateReport(results);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}