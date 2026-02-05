package com.robot;

/**
 * Represents the floor (N x N grid) that the robot walks on.
 * Cells can be marked (1) when the robot moves over them with pen down.
 */
public class Floor {
    private int[][] grid;
    private int size;

    /**
     * Creates a new floor with the specified size.
     * All cells are initialized to 0.
     * @param size the size of the floor (N x N)
     * @throws IllegalArgumentException if size is less than 1
     */
    public Floor(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Floor size must be at least 1");
        }
        this.size = size;
        this.grid = new int[size][size];
    }

    /**
     * Gets the size of the floor.
     * @return the size (N for an N x N floor)
     */
    public int getSize() {
        return size;
    }

    /**
     * Marks a cell on the floor (sets it to 1).
     * @param x the X coordinate
     * @param y the Y coordinate
     * @throws IllegalArgumentException if coordinates are out of bounds
     */
    public void mark(int x, int y) {
        validateCoordinates(x, y);
        grid[y][x] = 1;
    }

    /**
     * Gets the value at a specific cell.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the value at the cell (0 or 1)
     * @throws IllegalArgumentException if coordinates are out of bounds
     */
    public int getValue(int x, int y) {
        validateCoordinates(x, y);
        return grid[y][x];
    }

    /**
     * Checks if a position is within the floor bounds.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return true if position is valid, false otherwise
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    /**
     * Validates that coordinates are within bounds.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @throws IllegalArgumentException if coordinates are out of bounds
     */
    private void validateCoordinates(int x, int y) {
        if (!isValidPosition(x, y)) {
            throw new IllegalArgumentException(
                    String.format("Coordinates (%d, %d) are out of bounds for floor size %d", x, y, size));
        }
    }

    /**
     * Resets the floor (sets all cells to 0).
     */
    public void reset() {
        grid = new int[size][size];
    }

    /**
     * Returns a string representation of the floor.
     * Marked cells (1) are displayed as asterisks (*).
     * Unmarked cells (0) are displayed as spaces.
     * The floor is printed with Y-axis going from top (N-1) to bottom (0).
     * @return the floor as a string with indices
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        
        // Print from top (N-1) to bottom (0) to match the visual representation
        for (int y = size - 1; y >= 0; y--) {
            // Print Y-axis label
            sb.append(String.format("%3d ", y));
            
            // Print row content
            for (int x = 0; x < size; x++) {
                if (grid[y][x] == 1) {
                    sb.append("* ");
                } else {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        
        // Print X-axis labels
        sb.append("    "); // Spacing to align with Y-axis labels
        for (int x = 0; x < size; x++) {
            sb.append(String.format("%-2d", x));
        }
        sb.append("\n");
        
        return sb.toString();
    }

    /**
     * Returns the internal grid array (for testing purposes).
     * @return the grid array
     */
    public int[][] getGrid() {
        return grid;
    }
}
