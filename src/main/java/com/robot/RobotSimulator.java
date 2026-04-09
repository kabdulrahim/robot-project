package com.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class that runs the robot simulator.
 * Handles user input and executes commands.
 * Command lines are parsed by {@link CommandParser}; history is stored in {@link CommandHistory}.
 */
public class RobotSimulator {
    private Robot robot;
    private Floor floor;
    private final CommandHistory commandHistory;
    private boolean initialized;
    private boolean running;

    /**
     * Creates a new robot simulator.
     */
    public RobotSimulator() {
        this.robot = new Robot();
        this.floor = null;
        this.commandHistory = new CommandHistory();
        this.initialized = false;
        this.running = true;
    }

    /**
     * Gets the robot.
     * @return the robot
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Gets the floor.
     * @return the floor, or null if not initialized
     */
    public Floor getFloor() {
        return floor;
    }

    /**
     * Checks if the system is initialized.
     * @return true if initialized, false otherwise
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Checks if the simulator is still running.
     * @return true if running, false if quit
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Gets the command history.
     * @return list of executed commands
     */
    public List<String> getCommandHistory() {
        return new ArrayList<>(commandHistory.getEntries());
    }

    /**
     * Initializes the system with an N x N floor.
     * Resets the robot to position (0, 0), pen up, facing north.
     * @param size the size of the floor
     * @return result message
     */
    public String initialize(int size) {
        if (size < 1) {
            return "Error: Floor size must be at least 1";
        }
        this.floor = new Floor(size);
        this.robot.reset();
        this.initialized = true;
        return String.format("Initialized %d x %d floor", size, size);
    }

    /**
     * Puts the pen up.
     * @return result message
     */
    public String penUp() {
        robot.penUp();
        return "Pen is now up";
    }

    /**
     * Puts the pen down.
     * @return result message
     */
    public String penDown() {
        robot.penDown();
        return "Pen is now down";
    }

    /**
     * Turns the robot right.
     * @return result message
     */
    public String turnRight() {
        robot.turnRight();
        return "Turned right";
    }

    /**
     * Turns the robot left.
     * @return result message
     */
    public String turnLeft() {
        robot.turnLeft();
        return "Turned left";
    }

    /**
     * Moves the robot forward by the specified number of spaces.
     * If pen is down, marks the floor cells along the path.
     * @param spaces number of spaces to move
     * @return result message
     */
    public String move(int spaces) {
        if (!initialized) {
            return "Error: System not initialized. Use 'I n' to initialize.";
        }
        if (spaces < 0) {
            return "Error: Number of spaces must be non-negative";
        }

        int deltaX = robot.getDirection().getDeltaX();
        int deltaY = robot.getDirection().getDeltaY();

        // Mark starting position if pen is down
        if (robot.isPenDown()) {
            floor.mark(robot.getX(), robot.getY());
        }

        for (int i = 0; i < spaces; i++) {
            int newX = robot.getX() + deltaX;
            int newY = robot.getY() + deltaY;

            // Check if new position is valid
            if (!floor.isValidPosition(newX, newY)) {
                return String.format("Error: Cannot move beyond floor boundary. Stopped at position (%d, %d)",
                        robot.getX(), robot.getY());
            }

            // Move the robot
            robot.setX(newX);
            robot.setY(newY);

            // Mark the floor if pen is down
            if (robot.isPenDown()) {
                floor.mark(newX, newY);
            }
        }

        return String.format("Moved %d spaces", spaces);
    }

    /**
     * Prints the current floor.
     * @return the floor as a string
     */
    public String printFloor() {
        if (!initialized) {
            return "Error: System not initialized. Use 'I n' to initialize.";
        }
        return floor.print();
    }

    /**
     * Gets the current status of the robot.
     * @return status string
     */
    public String getStatus() {
        return robot.getStatus();
    }

    /**
     * Quits the simulator.
     * @return goodbye message
     */
    public String quit() {
        running = false;
        return "Goodbye!";
    }

    /**
     * Replays all commands in the history.
     * @return replay output
     */
    public String replayHistory() {
        if (commandHistory.isEmpty()) {
            return "No commands in history";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Replaying History ===\n");

        List<String> savedHistory = commandHistory.snapshot();

        // Reset for replay
        this.robot = new Robot();
        this.floor = null;
        this.initialized = false;
        commandHistory.clear();

        // Replay all commands
        for (String command : savedHistory) {
            sb.append("> ").append(command).append("\n");
            String result = processCommand(command, false);
            if (result != null && !result.isEmpty()) {
                sb.append(result).append("\n");
            }
        }

        sb.append("=== End of Replay ===");

        commandHistory.replaceWith(savedHistory);

        return sb.toString();
    }

    /**
     * Processes a command string and executes the appropriate action.
     * @param input the command string
     * @return the result message
     */
    public String processCommand(String input) {
        return processCommand(input, true);
    }

    /**
     * Processes a command string and executes the appropriate action.
     * @param input the command string
     * @param addToHistory whether to add to command history
     * @return the result message
     */
    public String processCommand(String input, boolean addToHistory) {
        ParsedCommand parsed = CommandParser.parse(input);
        if (parsed == null) {
            return "Error: Empty command";
        }

        String command = parsed.getCommand();
        String argument = parsed.getArgument();

        // Add to history (except for H, Q, and P commands)
        if (addToHistory && !command.equals("H") && !command.equals("Q") && !command.equals("P")) {
            commandHistory.add(parsed.getTrimmed());
        }

        switch (command) {
            case "U":
                return penUp();

            case "D":
                return penDown();

            case "R":
                return turnRight();

            case "L":
                return turnLeft();

            case "M":
                return handleMoveCommand(argument);

            case "P":
                return printFloor();

            case "C":
                return getStatus();

            case "Q":
                return quit();

            case "I":
                return handleInitializeCommand(argument);

            case "H":
                return replayHistory();

            default:
                if (addToHistory) {
                    commandHistory.removeLast();
                }
                return "Error: Unknown command '" + command + "'. Valid commands: U, D, R, L, M, P, C, Q, I, H";
        }
    }

    /**
     * Handles the move command with argument parsing.
     * @param argument the argument string (number of spaces)
     * @return result message
     */
    private String handleMoveCommand(String argument) {
        if (argument.isEmpty()) {
            return "Error: Move command requires a number of spaces (e.g., 'M 5')";
        }

        try {
            int spaces = Integer.parseInt(argument);
            if (spaces < 0) {
                return "Error: Number of spaces must be non-negative";
            }
            return move(spaces);
        } catch (NumberFormatException e) {
            return "Error: Invalid number format for move command";
        }
    }

    /**
     * Handles the initialize command with argument parsing.
     * @param argument the argument string (floor size)
     * @return result message
     */
    private String handleInitializeCommand(String argument) {
        if (argument.isEmpty()) {
            return "Error: Initialize command requires a floor size (e.g., 'I 10')";
        }

        try {
            int size = Integer.parseInt(argument);
            if (size < 1) {
                return "Error: Floor size must be at least 1";
            }
            return initialize(size);
        } catch (NumberFormatException e) {
            return "Error: Invalid number format for initialize command";
        }
    }

    /**
     * Runs the interactive command loop.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Robot Simulator");
        System.out.println("Commands: I n (initialize), U (pen up), D (pen down), R (turn right),");
        System.out.println("          L (turn left), M s (move), P (print), C (status), H (history), Q (quit)");
        System.out.println();

        while (running) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            String result = processCommand(input);
            if (result != null && !result.isEmpty()) {
                System.out.println(result);
            }
            System.out.println();
        }

        scanner.close();
    }

    /**
     * Main entry point.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        RobotSimulator simulator = new RobotSimulator();
        simulator.run();
    }
}
