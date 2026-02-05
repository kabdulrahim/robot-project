package com.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Floor class.
 */
class FloorTest {

    private Floor floor;

    @BeforeEach
    void setUp() {
        floor = new Floor(10);
    }

    @Test
    @DisplayName("Constructor creates floor with correct size")
    void testConstructorSize() {
        assertEquals(10, floor.getSize());
    }

    @Test
    @DisplayName("Constructor initializes all cells to 0")
    void testConstructorInitializesZeros() {
        for (int x = 0; x < floor.getSize(); x++) {
            for (int y = 0; y < floor.getSize(); y++) {
                assertEquals(0, floor.getValue(x, y));
            }
        }
    }

    @Test
    @DisplayName("Constructor throws exception for size less than 1")
    void testConstructorInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> new Floor(0));
        assertThrows(IllegalArgumentException.class, () -> new Floor(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20, 100})
    @DisplayName("Constructor works for various valid sizes")
    void testConstructorValidSizes(int size) {
        Floor testFloor = new Floor(size);
        assertEquals(size, testFloor.getSize());
    }

    @Test
    @DisplayName("mark() sets cell to 1")
    void testMark() {
        floor.mark(5, 5);
        assertEquals(1, floor.getValue(5, 5));
    }

    @Test
    @DisplayName("mark() can be called multiple times on same cell")
    void testMarkMultipleTimes() {
        floor.mark(3, 3);
        floor.mark(3, 3);
        assertEquals(1, floor.getValue(3, 3));
    }

    @Test
    @DisplayName("mark() throws exception for invalid coordinates")
    void testMarkInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> floor.mark(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.mark(0, -1));
        assertThrows(IllegalArgumentException.class, () -> floor.mark(10, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.mark(0, 10));
    }

    @Test
    @DisplayName("getValue() returns correct values")
    void testGetValue() {
        assertEquals(0, floor.getValue(0, 0));
        floor.mark(0, 0);
        assertEquals(1, floor.getValue(0, 0));
    }

    @Test
    @DisplayName("getValue() throws exception for invalid coordinates")
    void testGetValueInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> floor.getValue(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.getValue(0, -1));
        assertThrows(IllegalArgumentException.class, () -> floor.getValue(10, 0));
        assertThrows(IllegalArgumentException.class, () -> floor.getValue(0, 10));
    }

    @Test
    @DisplayName("isValidPosition() returns true for valid positions")
    void testIsValidPositionTrue() {
        assertTrue(floor.isValidPosition(0, 0));
        assertTrue(floor.isValidPosition(9, 9));
        assertTrue(floor.isValidPosition(5, 5));
        assertTrue(floor.isValidPosition(0, 9));
        assertTrue(floor.isValidPosition(9, 0));
    }

    @Test
    @DisplayName("isValidPosition() returns false for invalid positions")
    void testIsValidPositionFalse() {
        assertFalse(floor.isValidPosition(-1, 0));
        assertFalse(floor.isValidPosition(0, -1));
        assertFalse(floor.isValidPosition(10, 0));
        assertFalse(floor.isValidPosition(0, 10));
        assertFalse(floor.isValidPosition(-1, -1));
        assertFalse(floor.isValidPosition(10, 10));
    }

    @Test
    @DisplayName("reset() clears all marks")
    void testReset() {
        // Mark some cells
        floor.mark(0, 0);
        floor.mark(5, 5);
        floor.mark(9, 9);

        // Reset
        floor.reset();

        // Verify all cells are 0
        for (int x = 0; x < floor.getSize(); x++) {
            for (int y = 0; y < floor.getSize(); y++) {
                assertEquals(0, floor.getValue(x, y));
            }
        }
    }

    @Test
    @DisplayName("print() returns string representation")
    void testPrint() {
        floor.mark(0, 0);
        String output = floor.print();
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertTrue(output.contains("*"));
    }

    @Test
    @DisplayName("print() shows asterisk for marked cells")
    void testPrintMarkedCells() {
        Floor smallFloor = new Floor(3);
        smallFloor.mark(0, 0);
        smallFloor.mark(1, 1);
        smallFloor.mark(2, 2);
        
        String output = smallFloor.print();
        
        // Count asterisks - should be 3
        long asteriskCount = output.chars().filter(ch -> ch == '*').count();
        assertEquals(3, asteriskCount);
    }

    @Test
    @DisplayName("print() includes axis labels")
    void testPrintAxisLabels() {
        String output = floor.print();
        
        // Should contain row numbers 0-9
        assertTrue(output.contains("0"));
        assertTrue(output.contains("9"));
    }

    @Test
    @DisplayName("getGrid() returns internal array")
    void testGetGrid() {
        int[][] grid = floor.getGrid();
        assertNotNull(grid);
        assertEquals(10, grid.length);
        assertEquals(10, grid[0].length);
    }

    @Test
    @DisplayName("Floor size 1 works correctly")
    void testFloorSizeOne() {
        Floor singleCellFloor = new Floor(1);
        assertEquals(1, singleCellFloor.getSize());
        assertTrue(singleCellFloor.isValidPosition(0, 0));
        assertFalse(singleCellFloor.isValidPosition(1, 0));
        assertFalse(singleCellFloor.isValidPosition(0, 1));
        
        singleCellFloor.mark(0, 0);
        assertEquals(1, singleCellFloor.getValue(0, 0));
    }

    @Test
    @DisplayName("Corner positions are accessible")
    void testCornerPositions() {
        // Top-left
        floor.mark(0, 9);
        assertEquals(1, floor.getValue(0, 9));
        
        // Top-right
        floor.mark(9, 9);
        assertEquals(1, floor.getValue(9, 9));
        
        // Bottom-left
        floor.mark(0, 0);
        assertEquals(1, floor.getValue(0, 0));
        
        // Bottom-right
        floor.mark(9, 0);
        assertEquals(1, floor.getValue(9, 0));
    }

    @Test
    @DisplayName("Multiple marks create expected pattern")
    void testMarkPattern() {
        // Draw a diagonal
        for (int i = 0; i < 5; i++) {
            floor.mark(i, i);
        }
        
        // Verify diagonal is marked
        for (int i = 0; i < 5; i++) {
            assertEquals(1, floor.getValue(i, i));
        }
        
        // Verify other cells are not marked
        assertEquals(0, floor.getValue(0, 1));
        assertEquals(0, floor.getValue(1, 0));
    }
}
