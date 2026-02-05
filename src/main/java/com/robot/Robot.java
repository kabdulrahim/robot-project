package com.robot;

/**
 * Represents a robot that can move around on a floor.
 * The robot has a position (x, y), a direction it's facing, and a pen that can be up or down.
 */
public class Robot {
    private int x;
    private int y;
    private Direction direction;
    private boolean penDown;

    /**
     * Creates a new robot at position (0, 0), facing north with pen up.
     */
    public Robot() {
        this.x = 0;
        this.y = 0;
        this.direction = Direction.NORTH;
        this.penDown = false;
    }

    /**
     * Gets the current X position of the robot.
     * @return the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the current Y position of the robot.
     * @return the Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the X position of the robot.
     * @param x the new X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y position of the robot.
     * @param y the new Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the current direction the robot is facing.
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction the robot is facing.
     * @param direction the new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Checks if the pen is down.
     * @return true if pen is down, false if pen is up
     */
    public boolean isPenDown() {
        return penDown;
    }

    /**
     * Puts the pen down (robot will draw when moving).
     */
    public void penDown() {
        this.penDown = true;
    }

    /**
     * Lifts the pen up (robot will not draw when moving).
     */
    public void penUp() {
        this.penDown = false;
    }

    /**
     * Turns the robot right (clockwise).
     */
    public void turnRight() {
        this.direction = this.direction.turnRight();
    }

    /**
     * Turns the robot left (counter-clockwise).
     */
    public void turnLeft() {
        this.direction = this.direction.turnLeft();
    }

    /**
     * Resets the robot to initial state: position (0, 0), facing north, pen up.
     */
    public void reset() {
        this.x = 0;
        this.y = 0;
        this.direction = Direction.NORTH;
        this.penDown = false;
    }

    /**
     * Returns a string representation of the robot's current state.
     * @return status string in format "Position: x, y - Pen: up/down - Facing: direction"
     */
    public String getStatus() {
        String penStatus = penDown ? "down" : "up";
        return String.format("Position: %d, %d - Pen: %s - Facing: %s",
                x, y, penStatus, direction.getDisplayName());
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
