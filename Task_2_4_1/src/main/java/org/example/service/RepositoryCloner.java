package org.example.service;

import org.apache.commons.io.FileUtils;
import org.example.model.Student;

import java.io.File;
import java.util.Map;

/**
 * Service for cloning student repositories from GitHub.
 */
public class RepositoryCloner {
    public void cloneRepositories(Map<String, Student> students) {
        try {
            File repositoriesDir = new File("repositories");
            if (repositoriesDir.exists()) {
                FileUtils.deleteDirectory(repositoriesDir);
            }
            repositoriesDir.mkdir();

            for (Student student : students.values()) {
                try {
                    String repoUrl = student.getRepository();
                    String repoPath = "repositories/" + student.getId();
                    
                    ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repoUrl, repoPath);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    process.waitFor();
                } catch (Exception e) {
                    // Suppress exceptions
                }
            }
        } catch (Exception e) {
            // Suppress exceptions
        }
    }
} 