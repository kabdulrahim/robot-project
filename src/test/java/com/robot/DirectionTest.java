package com.robot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Direction enum.
 */
class DirectionTest {

    @Test
    @DisplayName("Direction NORTH has correct display name")
    void testNorthDisplayName() {
        assertEquals("north", Direction.NORTH.getDisplayName());
    }

    @Test
    @DisplayName("Direction EAST has correct display name")
    void testEastDisplayName() {
        assertEquals("east", Direction.EAST.getDisplayName());
    }

    @Test
    @DisplayName("Direction SOUTH has correct display name")
    void testSouthDisplayName() {
        assertEquals("south", Direction.SOUTH.getDisplayName());
    }

    @Test
    @DisplayName("Direction WEST has correct display name")
    void testWestDisplayName() {
        assertEquals("west", Direction.WEST.getDisplayName());
    }

    @Test
    @DisplayName("Turn right from NORTH goes to EAST")
    void testTurnRightFromNorth() {
        assertEquals(Direction.EAST, Direction.NORTH.turnRight());
    }

    @Test
    @DisplayName("Turn right from EAST goes to SOUTH")
    void testTurnRightFromEast() {
        assertEquals(Direction.SOUTH, Direction.EAST.turnRight());
    }

    @Test
    @DisplayName("Turn right from SOUTH goes to WEST")
    void testTurnRightFromSouth() {
        assertEquals(Direction.WEST, Direction.SOUTH.turnRight());
    }

    @Test
    @DisplayName("Turn right from WEST goes to NORTH")
    void testTurnRightFromWest() {
        assertEquals(Direction.NORTH, Direction.WEST.turnRight());
    }

    @Test
    @DisplayName("Turn left from NORTH goes to WEST")
    void testTurnLeftFromNorth() {
        assertEquals(Direction.WEST, Direction.NORTH.turnLeft());
    }

    @Test
    @DisplayName("Turn left from WEST goes to SOUTH")
    void testTurnLeftFromWest() {
        assertEquals(Direction.SOUTH, Direction.WEST.turnLeft());
    }

    @Test
    @DisplayName("Turn left from SOUTH goes to EAST")
    void testTurnLeftFromSouth() {
        assertEquals(Direction.EAST, Direction.SOUTH.turnLeft());
    }

    @Test
    @DisplayName("Turn left from EAST goes to NORTH")
    void testTurnLeftFromEast() {
        assertEquals(Direction.NORTH, Direction.EAST.turnLeft());
    }

    @Test
    @DisplayName("Four right turns return to original direction")
    void testFourRightTurnsReturnToOriginal() {
        Direction direction = Direction.NORTH;
        direction = direction.turnRight();
        direction = direction.turnRight();
        direction = direction.turnRight();
        direction = direction.turnRight();
        assertEquals(Direction.NORTH, direction);
    }

    @Test
    @DisplayName("Four left turns return to original direction")
    void testFourLeftTurnsReturnToOriginal() {
        Direction direction = Direction.NORTH;
        direction = direction.turnLeft();
        direction = direction.turnLeft();
        direction = direction.turnLeft();
        direction = direction.turnLeft();
        assertEquals(Direction.NORTH, direction);
    }

    @Test
    @DisplayName("NORTH has correct delta values (0, 1)")
    void testNorthDelta() {
        assertEquals(0, Direction.NORTH.getDeltaX());
        assertEquals(1, Direction.NORTH.getDeltaY());
    }

    @Test
    @DisplayName("EAST has correct delta values (1, 0)")
    void testEastDelta() {
        assertEquals(1, Direction.EAST.getDeltaX());
        assertEquals(0, Direction.EAST.getDeltaY());
    }

    @Test
    @DisplayName("SOUTH has correct delta values (0, -1)")
    void testSouthDelta() {
        assertEquals(0, Direction.SOUTH.getDeltaX());
        assertEquals(-1, Direction.SOUTH.getDeltaY());
    }

    @Test
    @DisplayName("WEST has correct delta values (-1, 0)")
    void testWestDelta() {
        assertEquals(-1, Direction.WEST.getDeltaX());
        assertEquals(0, Direction.WEST.getDeltaY());
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    @DisplayName("All directions have non-null display names")
    void testAllDirectionsHaveDisplayNames(Direction direction) {
        assertNotNull(direction.getDisplayName());
        assertFalse(direction.getDisplayName().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    @DisplayName("Turn right then left returns to same direction")
    void testTurnRightThenLeft(Direction direction) {
        assertEquals(direction, direction.turnRight().turnLeft());
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    @DisplayName("Turn left then right returns to same direction")
    void testTurnLeftThenRight(Direction direction) {
        assertEquals(direction, direction.turnLeft().turnRight());
    }
}
