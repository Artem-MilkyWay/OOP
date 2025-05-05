package org.example.model;

/**
 * Represents a student with their GitHub information.
 */
public class Student {
    private final String id;
    private final String username;
    private final String repository;

    public Student(String id, String username, String repository) {
        this.id = id;
        this.username = username;
        this.repository = repository;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRepository() {
        return repository;
    }
} 