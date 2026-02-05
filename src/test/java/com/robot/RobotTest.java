package com.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Robot class.
 */
class RobotTest {

    private Robot robot;

    @BeforeEach
    void setUp() {
        robot = new Robot();
    }

    @Test
    @DisplayName("New robot starts at position (0, 0)")
    void testInitialPosition() {
        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
    }

    @Test
    @DisplayName("New robot faces north")
    void testInitialDirection() {
        assertEquals(Direction.NORTH, robot.getDirection());
    }

    @Test
    @DisplayName("New robot has pen up")
    void testInitialPenState() {
        assertFalse(robot.isPenDown());
    }

    @Test
    @DisplayName("penDown() sets pen to down")
    void testPenDown() {
        robot.penDown();
        assertTrue(robot.isPenDown());
    }

    @Test
    @DisplayName("penUp() sets pen to up")
    void testPenUp() {
        robot.penDown();
        robot.penUp();
        assertFalse(robot.isPenDown());
    }

    @Test
    @DisplayName("turnRight() changes direction clockwise")
    void testTurnRight() {
        assertEquals(Direction.NORTH, robot.getDirection());
        robot.turnRight();
        assertEquals(Direction.EAST, robot.getDirection());
        robot.turnRight();
        assertEquals(Direction.SOUTH, robot.getDirection());
        robot.turnRight();
        assertEquals(Direction.WEST, robot.getDirection());
        robot.turnRight();
        assertEquals(Direction.NORTH, robot.getDirection());
    }

    @Test
    @DisplayName("turnLeft() changes direction counter-clockwise")
    void testTurnLeft() {
        assertEquals(Direction.NORTH, robot.getDirection());
        robot.turnLeft();
        assertEquals(Direction.WEST, robot.getDirection());
        robot.turnLeft();
        assertEquals(Direction.SOUTH, robot.getDirection());
        robot.turnLeft();
        assertEquals(Direction.EAST, robot.getDirection());
        robot.turnLeft();
        assertEquals(Direction.NORTH, robot.getDirection());
    }

    @Test
    @DisplayName("setX() updates X position")
    void testSetX() {
        robot.setX(5);
        assertEquals(5, robot.getX());
    }

    @Test
    @DisplayName("setY() updates Y position")
    void testSetY() {
        robot.setY(7);
        assertEquals(7, robot.getY());
    }

    @Test
    @DisplayName("setDirection() updates direction")
    void testSetDirection() {
        robot.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, robot.getDirection());
    }

    @Test
    @DisplayName("reset() restores initial state")
    void testReset() {
        // Change state
        robot.setX(5);
        robot.setY(7);
        robot.setDirection(Direction.SOUTH);
        robot.penDown();

        // Reset
        robot.reset();

        // Verify initial state
        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertEquals(Direction.NORTH, robot.getDirection());
        assertFalse(robot.isPenDown());
    }

    @Test
    @DisplayName("getStatus() returns correct format with pen up")
    void testGetStatusPenUp() {
        String status = robot.getStatus();
        assertEquals("Position: 0, 0 - Pen: up - Facing: north", status);
    }

    @Test
    @DisplayName("getStatus() returns correct format with pen down")
    void testGetStatusPenDown() {
        robot.penDown();
        String status = robot.getStatus();
        assertEquals("Position: 0, 0 - Pen: down - Facing: north", status);
    }

    @Test
    @DisplayName("getStatus() returns correct format after moving")
    void testGetStatusAfterChanges() {
        robot.setX(3);
        robot.setY(4);
        robot.setDirection(Direction.EAST);
        robot.penDown();
        String status = robot.getStatus();
        assertEquals("Position: 3, 4 - Pen: down - Facing: east", status);
    }

    @Test
    @DisplayName("toString() returns same as getStatus()")
    void testToString() {
        assertEquals(robot.getStatus(), robot.toString());
    }

    @Test
    @DisplayName("Multiple pen state changes work correctly")
    void testMultiplePenChanges() {
        assertFalse(robot.isPenDown());
        robot.penDown();
        assertTrue(robot.isPenDown());
        robot.penDown();  // Already down
        assertTrue(robot.isPenDown());
        robot.penUp();
        assertFalse(robot.isPenDown());
        robot.penUp();  // Already up
        assertFalse(robot.isPenDown());
    }

    @Test
    @DisplayName("Multiple turns work correctly")
    void testMultipleTurns() {
        // 3 rights = 1 left
        robot.turnRight();
        robot.turnRight();
        robot.turnRight();
        assertEquals(Direction.WEST, robot.getDirection());

        robot.reset();

        // 3 lefts = 1 right
        robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
        assertEquals(Direction.EAST, robot.getDirection());
    }

    @Test
    @DisplayName("Status format for all directions")
    void testStatusForAllDirections() {
        robot.setDirection(Direction.NORTH);
        assertTrue(robot.getStatus().contains("north"));

        robot.setDirection(Direction.EAST);
        assertTrue(robot.getStatus().contains("east"));

        robot.setDirection(Direction.SOUTH);
        assertTrue(robot.getStatus().contains("south"));

        robot.setDirection(Direction.WEST);
        assertTrue(robot.getStatus().contains("west"));
    }
}
