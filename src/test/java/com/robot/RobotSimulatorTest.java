package com.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RobotSimulator class.
 */
class RobotSimulatorTest {

    private RobotSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new RobotSimulator();
    }

    // ==================== Initialization Tests ====================

    @Test
    @DisplayName("New simulator is not initialized")
    void testInitialStateNotInitialized() {
        assertFalse(simulator.isInitialized());
    }

    @Test
    @DisplayName("New simulator is running")
    void testInitialStateRunning() {
        assertTrue(simulator.isRunning());
    }

    @Test
    @DisplayName("Initialize command sets up floor correctly")
    void testInitializeCommand() {
        simulator.processCommand("I 10");
        assertTrue(simulator.isInitialized());
        assertNotNull(simulator.getFloor());
        assertEquals(10, simulator.getFloor().getSize());
    }

    @Test
    @DisplayName("Initialize command with lowercase works")
    void testInitializeLowercase() {
        simulator.processCommand("i 10");
        assertTrue(simulator.isInitialized());
        assertEquals(10, simulator.getFloor().getSize());
    }

    @Test
    @DisplayName("Initialize command resets robot position")
    void testInitializeResetsRobot() {
        simulator.processCommand("I 10");
        simulator.processCommand("D");
        simulator.processCommand("M 5");
        simulator.processCommand("I 5");
        
        assertEquals(0, simulator.getRobot().getX());
        assertEquals(0, simulator.getRobot().getY());
        assertEquals(Direction.NORTH, simulator.getRobot().getDirection());
        assertFalse(simulator.getRobot().isPenDown());
    }

    @Test
    @DisplayName("Initialize with invalid size returns error")
    void testInitializeInvalidSize() {
        String result = simulator.processCommand("I 0");
        assertTrue(result.contains("Error"));
        assertFalse(simulator.isInitialized());
    }

    @Test
    @DisplayName("Initialize without argument returns error")
    void testInitializeNoArgument() {
        String result = simulator.processCommand("I");
        assertTrue(result.contains("Error"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"I abc", "I 1.5", "I -5"})
    @DisplayName("Initialize with invalid argument returns error")
    void testInitializeInvalidArguments(String command) {
        String result = simulator.processCommand(command);
        assertTrue(result.contains("Error"));
    }

    // ==================== Pen Commands Tests ====================

    @Test
    @DisplayName("Pen down command works")
    void testPenDown() {
        simulator.processCommand("D");
        assertTrue(simulator.getRobot().isPenDown());
    }

    @Test
    @DisplayName("Pen up command works")
    void testPenUp() {
        simulator.processCommand("D");
        simulator.processCommand("U");
        assertFalse(simulator.getRobot().isPenDown());
    }

    @Test
    @DisplayName("Pen commands work with lowercase")
    void testPenCommandsLowercase() {
        simulator.processCommand("d");
        assertTrue(simulator.getRobot().isPenDown());
        simulator.processCommand("u");
        assertFalse(simulator.getRobot().isPenDown());
    }

    // ==================== Turn Commands Tests ====================

    @Test
    @DisplayName("Turn right command works")
    void testTurnRight() {
        simulator.processCommand("R");
        assertEquals(Direction.EAST, simulator.getRobot().getDirection());
    }

    @Test
    @DisplayName("Turn left command works")
    void testTurnLeft() {
        simulator.processCommand("L");
        assertEquals(Direction.WEST, simulator.getRobot().getDirection());
    }

    @Test
    @DisplayName("Turn commands work with lowercase")
    void testTurnCommandsLowercase() {
        simulator.processCommand("r");
        assertEquals(Direction.EAST, simulator.getRobot().getDirection());
        simulator.processCommand("l");
        assertEquals(Direction.NORTH, simulator.getRobot().getDirection());
    }

    @Test
    @DisplayName("Four right turns return to north")
    void testFourRightTurns() {
        simulator.processCommand("R");
        simulator.processCommand("R");
        simulator.processCommand("R");
        simulator.processCommand("R");
        assertEquals(Direction.NORTH, simulator.getRobot().getDirection());
    }

    // ==================== Move Command Tests ====================

    @Test
    @DisplayName("Move command requires initialization")
    void testMoveRequiresInit() {
        String result = simulator.processCommand("M 5");
        assertTrue(result.contains("Error"));
        assertTrue(result.contains("not initialized"));
    }

    @Test
    @DisplayName("Move north increases Y position")
    void testMoveNorth() {
        simulator.processCommand("I 10");
        simulator.processCommand("M 5");
        assertEquals(0, simulator.getRobot().getX());
        assertEquals(5, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move east increases X position")
    void testMoveEast() {
        simulator.processCommand("I 10");
        simulator.processCommand("R");
        simulator.processCommand("M 5");
        assertEquals(5, simulator.getRobot().getX());
        assertEquals(0, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move south decreases Y position")
    void testMoveSouth() {
        simulator.processCommand("I 10");
        simulator.processCommand("M 5");  // Move north first
        simulator.processCommand("R");
        simulator.processCommand("R");    // Face south
        simulator.processCommand("M 3");
        assertEquals(0, simulator.getRobot().getX());
        assertEquals(2, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move west decreases X position")
    void testMoveWest() {
        simulator.processCommand("I 10");
        simulator.processCommand("R");
        simulator.processCommand("M 5");  // Move east first
        simulator.processCommand("R");
        simulator.processCommand("R");    // Face west
        simulator.processCommand("M 3");
        assertEquals(2, simulator.getRobot().getX());
        assertEquals(0, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move with lowercase works")
    void testMoveLowercase() {
        simulator.processCommand("I 10");
        simulator.processCommand("m 5");
        assertEquals(5, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move without space between command and number works")
    void testMoveNoSpace() {
        simulator.processCommand("I 10");
        simulator.processCommand("M5");
        assertEquals(5, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move marks floor when pen is down")
    void testMoveWithPenDown() {
        simulator.processCommand("I 10");
        simulator.processCommand("D");
        simulator.processCommand("M 3");
        
        // Check all marked positions
        assertEquals(1, simulator.getFloor().getValue(0, 0));
        assertEquals(1, simulator.getFloor().getValue(0, 1));
        assertEquals(1, simulator.getFloor().getValue(0, 2));
        assertEquals(1, simulator.getFloor().getValue(0, 3));
    }

    @Test
    @DisplayName("Move does not mark floor when pen is up")
    void testMoveWithPenUp() {
        simulator.processCommand("I 10");
        simulator.processCommand("M 3");
        
        // Check no positions are marked
        assertEquals(0, simulator.getFloor().getValue(0, 0));
        assertEquals(0, simulator.getFloor().getValue(0, 1));
        assertEquals(0, simulator.getFloor().getValue(0, 2));
        assertEquals(0, simulator.getFloor().getValue(0, 3));
    }

    @Test
    @DisplayName("Move stops at floor boundary")
    void testMoveStopsAtBoundary() {
        simulator.processCommand("I 5");
        String result = simulator.processCommand("M 10");
        assertTrue(result.contains("Error") || result.contains("boundary"));
    }

    @Test
    @DisplayName("Move with zero spaces does nothing")
    void testMoveZeroSpaces() {
        simulator.processCommand("I 10");
        simulator.processCommand("M 0");
        assertEquals(0, simulator.getRobot().getX());
        assertEquals(0, simulator.getRobot().getY());
    }

    @Test
    @DisplayName("Move with missing argument returns error")
    void testMoveMissingArgument() {
        simulator.processCommand("I 10");
        String result = simulator.processCommand("M");
        assertTrue(result.contains("Error"));
    }

    // ==================== Status Command Tests ====================

    @Test
    @DisplayName("Status command returns correct format")
    void testStatusCommand() {
        simulator.processCommand("I 10");
        String result = simulator.processCommand("C");
        assertTrue(result.contains("Position: 0, 0"));
        assertTrue(result.contains("Pen: up"));
        assertTrue(result.contains("Facing: north"));
    }

    @Test
    @DisplayName("Status reflects changes")
    void testStatusReflectsChanges() {
        simulator.processCommand("I 10");
        simulator.processCommand("D");
        simulator.processCommand("M 4");
        simulator.processCommand("R");
        
        String result = simulator.processCommand("C");
        assertTrue(result.contains("Position: 0, 4"));
        assertTrue(result.contains("Pen: down"));
        assertTrue(result.contains("Facing: east"));
    }

    @Test
    @DisplayName("Status command works with lowercase")
    void testStatusLowercase() {
        String result = simulator.processCommand("c");
        assertTrue(result.contains("Position"));
    }

    // ==================== Print Command Tests ====================

    @Test
    @DisplayName("Print command requires initialization")
    void testPrintRequiresInit() {
        String result = simulator.processCommand("P");
        assertTrue(result.contains("Error"));
    }

    @Test
    @DisplayName("Print command returns floor representation")
    void testPrintCommand() {
        simulator.processCommand("I 5");
        String result = simulator.processCommand("P");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Print shows asterisks for marked cells")
    void testPrintShowsMarks() {
        simulator.processCommand("I 10");
        simulator.processCommand("D");
        simulator.processCommand("M 3");
        
        String result = simulator.processCommand("P");
        assertTrue(result.contains("*"));
    }

    @Test
    @DisplayName("Print command works with lowercase")
    void testPrintLowercase() {
        simulator.processCommand("I 5");
        String result = simulator.processCommand("p");
        assertNotNull(result);
    }

    // ==================== Quit Command Tests ====================

    @Test
    @DisplayName("Quit command stops simulator")
    void testQuitCommand() {
        assertTrue(simulator.isRunning());
        simulator.processCommand("Q");
        assertFalse(simulator.isRunning());
    }

    @Test
    @DisplayName("Quit command works with lowercase")
    void testQuitLowercase() {
        simulator.processCommand("q");
        assertFalse(simulator.isRunning());
    }

    // ==================== History Command Tests ====================

    @Test
    @DisplayName("History command with empty history")
    void testHistoryEmpty() {
        String result = simulator.processCommand("H");
        assertTrue(result.contains("No commands"));
    }

    @Test
    @DisplayName("History command replays commands")
    void testHistoryReplay() {
        simulator.processCommand("I 10");
        simulator.processCommand("D");
        simulator.processCommand("M 3");
        
        String result = simulator.processCommand("H");
        assertTrue(result.contains("Replaying"));
        assertTrue(result.contains("I 10"));
        assertTrue(result.contains("D"));
        assertTrue(result.contains("M 3"));
    }

    @Test
    @DisplayName("History does not include Q, P, H commands")
    void testHistoryExcludesCommands() {
        simulator.processCommand("I 10");
        simulator.processCommand("P");
        simulator.processCommand("C");
        
        List<String> history = simulator.getCommandHistory();
        assertTrue(history.contains("I 10"));
        assertTrue(history.contains("C"));
        assertFalse(history.stream().anyMatch(cmd -> cmd.startsWith("P")));
    }

    // ==================== Error Handling Tests ====================

    @Test
    @DisplayName("Unknown command returns error")
    void testUnknownCommand() {
        String result = simulator.processCommand("X");
        assertTrue(result.contains("Error"));
        assertTrue(result.contains("Unknown"));
    }

    @Test
    @DisplayName("Empty command returns error")
    void testEmptyCommand() {
        String result = simulator.processCommand("");
        assertTrue(result.contains("Error"));
    }

    @Test
    @DisplayName("Null command returns error")
    void testNullCommand() {
        String result = simulator.processCommand(null);
        assertTrue(result.contains("Error"));
    }

    @Test
    @DisplayName("Whitespace-only command returns error")
    void testWhitespaceCommand() {
        String result = simulator.processCommand("   ");
        assertTrue(result.contains("Error"));
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Complete scenario from project description")
    void testCompleteScenario() {
        // Initialize 10x10 floor
        simulator.processCommand("I 10");
        assertTrue(simulator.isInitialized());
        
        // Check initial position
        String status = simulator.processCommand("C");
        assertTrue(status.contains("Position: 0, 0"));
        assertTrue(status.contains("Pen: up"));
        assertTrue(status.contains("Facing: north"));
        
        // Pen down
        simulator.processCommand("D");
        status = simulator.processCommand("C");
        assertTrue(status.contains("Pen: down"));
        
        // Move 4 spaces north
        simulator.processCommand("M 4");
        status = simulator.processCommand("C");
        assertTrue(status.contains("Position: 0, 4"));
        
        // Turn right (now facing east)
        simulator.processCommand("R");
        status = simulator.processCommand("C");
        assertTrue(status.contains("Facing: east"));
        
        // Move 3 spaces east
        simulator.processCommand("M 3");
        status = simulator.processCommand("C");
        assertTrue(status.contains("Position: 3, 4"));
        
        // Print and verify marks
        String floor = simulator.processCommand("P");
        assertTrue(floor.contains("*"));
    }

    @Test
    @DisplayName("Drawing L-shape pattern")
    void testDrawLShape() {
        simulator.processCommand("I 10");
        simulator.processCommand("D");
        
        // Draw vertical line
        simulator.processCommand("M 4");
        
        // Turn and draw horizontal line
        simulator.processCommand("R");
        simulator.processCommand("M 3");
        
        Floor floor = simulator.getFloor();
        
        // Verify vertical line
        for (int y = 0; y <= 4; y++) {
            assertEquals(1, floor.getValue(0, y));
        }
        
        // Verify horizontal line
        for (int x = 0; x <= 3; x++) {
            assertEquals(1, floor.getValue(x, 4));
        }
    }

    @Test
    @DisplayName("Commands are case-insensitive")
    void testCaseInsensitivity() {
        simulator.processCommand("i 10");
        assertTrue(simulator.isInitialized());
        
        simulator.processCommand("d");
        assertTrue(simulator.getRobot().isPenDown());
        
        simulator.processCommand("m 3");
        assertEquals(3, simulator.getRobot().getY());
        
        simulator.processCommand("r");
        assertEquals(Direction.EAST, simulator.getRobot().getDirection());
        
        simulator.processCommand("l");
        assertEquals(Direction.NORTH, simulator.getRobot().getDirection());
        
        simulator.processCommand("u");
        assertFalse(simulator.getRobot().isPenDown());
    }
}
