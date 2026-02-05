package com.robot;

/**
 * Enum representing the four cardinal directions the robot can face.
 */
public enum Direction {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String displayName;

    Direction(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the direction (lowercase).
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the direction after turning right (clockwise).
     * @return the new direction after turning right
     */
    public Direction turnRight() {
        switch (this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: return this;
        }
    }

    /**
     * Returns the direction after turning left (counter-clockwise).
     * @return the new direction after turning left
     */
    public Direction turnLeft() {
        switch (this) {
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            case EAST: return NORTH;
            default: return this;
        }
    }

    /**
     * Returns the change in X coordinate when moving in this direction.
     * @return delta X (-1, 0, or 1)
     */
    public int getDeltaX() {
        switch (this) {
            case EAST: return 1;
            case WEST: return -1;
            default: return 0;
        }
    }

    /**
     * Returns the change in Y coordinate when moving in this direction.
     * @return delta Y (-1, 0, or 1)
     */
    public int getDeltaY() {
        switch (this) {
            case NORTH: return 1;
            case SOUTH: return -1;
            default: return 0;
        }
    }
}
