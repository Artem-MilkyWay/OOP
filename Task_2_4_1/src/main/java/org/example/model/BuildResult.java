package org.example.model;

/**
 * Represents the build evaluation results for a student's task.
 */
public class BuildResult {
    private String buildStatus = "-";
    private String javadocStatus = "-";
    private String testStatus = "-";
    private int points = 0;

    public BuildResult() {
        this.buildStatus = "-";
        this.javadocStatus = "-";
        this.testStatus = "-";
    }

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
        calculatePoints();
    }

    public String getJavadocStatus() {
        return javadocStatus;
    }

    public void setJavadocStatus(String javadocStatus) {
        this.javadocStatus = javadocStatus;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
        calculatePoints();
    }

    public int getPoints() {
        return points;
    }

    private void calculatePoints() {
        points = 0;
        if (buildStatus.equals("+")) {
            points += 1;
        }
        if (testStatus.equals("+")) {
            points += 1;
        }
    }
} 