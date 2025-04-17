package ru.nsu.mikiyanskiy.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationControllerTest {

    @Test
    void testValidConfig() {
        assertTrue(ConfigurationController.isValidConfig(10, 10, 3, 20));
    }

    @Test
    void testTooFewRows() {
        assertFalse(ConfigurationController.isValidConfig(4, 10, 3, 10));
    }

    @Test
    void testTooManyColumns() {
        assertFalse(ConfigurationController.isValidConfig(10, 31, 3, 10));
    }

    @Test
    void testInvalidFood() {
        assertFalse(ConfigurationController.isValidConfig(10, 10, 0, 10));
        assertFalse(ConfigurationController.isValidConfig(10, 10, 17, 10));
    }

    @Test
    void testInvalidWinLength() {
        assertFalse(ConfigurationController.isValidConfig(10, 10, 3, 0));
        assertFalse(ConfigurationController.isValidConfig(10, 10, 3, 101));
    }

    @Test
    void testEdgeValues() {
        assertTrue(ConfigurationController.isValidConfig(5, 5, 1, 2));
        assertTrue(ConfigurationController.isValidConfig(30, 30, 16, 900));
    }
}
