package org.example.service;

import org.example.model.BuildResult;
import org.example.model.Student;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.BuildLauncher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

/**
 * Service for checking student builds and test results.
 */
public class BuildChecker {
    private static final String REPOSITORIES_DIR = "repositories";

    public Map<String, Map<String, BuildResult>> evaluateBuilds(Map<String, Student> students, List<String> tasks) {
        Map<String, Map<String, BuildResult>> results = new LinkedHashMap<>();
        
        for (String task : tasks) {
            Map<String, BuildResult> taskResults = new LinkedHashMap<>();
            
            for (Student student : students.values()) {
                String repoPath = REPOSITORIES_DIR + "/" + student.getId();
                String taskPath = repoPath + "/" + task;
                File projectDir = new File(taskPath);
                
                BuildResult result = new BuildResult();
                
                if (!projectDir.exists() || !new File(taskPath + "/build.gradle").exists()) {
                    result.setBuildStatus("-");
                    result.setTestStatus("-");
                } else {
                    try (ProjectConnection connection = GradleConnector.newConnector()
                            .forProjectDirectory(projectDir)
                            .connect()) {
                        
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        
                        // Check build
                        try {
                            connection.newBuild()
                                    .forTasks("clean", "build")
                                    .setStandardOutput(outputStream)
                                    .setStandardError(outputStream)
                                    .run();
                            result.setBuildStatus("+");
                        } catch (Exception e) {
                            result.setBuildStatus("-");
                        }
                        
                        // Check tests
                        try {
                            connection.newBuild()
                                    .forTasks("test")
                                    .setStandardOutput(outputStream)
                                    .setStandardError(outputStream)
                                    .run();
                            result.setTestStatus("+");
                        } catch (Exception e) {
                            result.setTestStatus("-");
                        }
                    } catch (Exception e) {
                        result.setBuildStatus("-");
                        result.setTestStatus("-");
                    }
                }
                
                taskResults.put(student.getId(), result);
            }
            
            results.put(task, taskResults);
        }
        
        return results;
    }

    private Map<String, BuildResult> evaluateTask(Map<String, Student> students, String taskName) {
        Map<String, BuildResult> evaluationResults = new LinkedHashMap<>();

        for (Student student : students.values()) {
            System.out.println("----------");
            System.out.println(student.getId() + ":");

            BuildResult result = new BuildResult();
            String taskPath = REPOSITORIES_DIR + "/" + student.getId() + "/" + taskName;

            try {
                GradleConnector connector = GradleConnector.newConnector();
                connector.forProjectDirectory(new File(taskPath));
                ProjectConnection connection = connector.connect();
                BuildLauncher build = connection.newBuild();

                // Check build
                try {
                    build.forTasks("build").run();
                    result.setBuildStatus("+");
                    System.out.println("Building +");
                } catch (Exception e) {
                    System.out.println("Building -");
                }

                // Check tests
                try {
                    build.forTasks("test")
                            .addArguments("-i")
                            .run();
                    String testReportPath = taskPath + "/build/reports/tests/test/index.html";
                    Document reportDoc = Jsoup.parse(new File(testReportPath));
                    String successRate = reportDoc.getElementById("successRate")
                            .select("div.percent")
                            .first()
                            .text();
                    System.out.println("TESTS COMPLETED + " + successRate);
                    result.setTestStatus(successRate);
                } catch (Exception e) {
                    System.out.println("TESTS FAILED - ");
                }

                // Check documentation
                try {
                    build.forTasks("javadoc").run();
                    result.setJavadocStatus("+");
                    System.out.println("JAVADOC +");
                } catch (Exception e) {
                    System.out.println("Javadoc -");
                }

                connection.close();
            } catch (Exception e) {
                System.err.println("Error evaluating build: " + e.getMessage());
            }

            evaluationResults.put(student.getId(), result);
        }

        return evaluationResults;
    }
} 