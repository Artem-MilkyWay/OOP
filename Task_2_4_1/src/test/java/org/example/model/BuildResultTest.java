package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildResultTest {
    
    @Test
    void testInitialState() {
        BuildResult result = new BuildResult();
        assertEquals("-", result.getBuildStatus());
        assertEquals("-", result.getTestStatus());
        assertEquals("-", result.getJavadocStatus());
        assertEquals(0, result.getPoints());
    }
    
    @Test
    void testSuccessfulBuild() {
        BuildResult result = new BuildResult();
        result.setBuildStatus("+");
        assertEquals(1, result.getPoints());
    }
    
    @Test
    void testSuccessfulTests() {
        BuildResult result = new BuildResult();
        result.setTestStatus("+");
        assertEquals(1, result.getPoints());
    }
    
    @Test
    void testFullPoints() {
        BuildResult result = new BuildResult();
        result.setBuildStatus("+");
        result.setTestStatus("+");
        assertEquals(2, result.getPoints());
    }
    
    @Test
    void testFailedBuild() {
        BuildResult result = new BuildResult();
        result.setBuildStatus("-");
        result.setTestStatus("+");
        assertEquals(1, result.getPoints());
    }
} 