# Robot Simulator

A Java program that simulates a robot walking around a room, controlled via command-line interface. The robot holds a pen that can be up or down - when down, it traces shapes on an N x N floor as it moves.

## Project Structure

```
robot-project/
├── pom.xml                              # Maven build file
├── README.md                            # This file
└── src/
    ├── main/java/com/robot/
    │   ├── Direction.java               # Enum for cardinal directions
    │   ├── Robot.java                   # Robot state and behavior
    │   ├── Floor.java                   # N x N grid representation
    │   └── RobotSimulator.java          # Main class with command handling
    └── test/java/com/robot/
        ├── DirectionTest.java           # Tests for Direction enum
        ├── RobotTest.java               # Tests for Robot class
        ├── FloorTest.java               # Tests for Floor class
        └── RobotSimulatorTest.java      # Tests for RobotSimulator class
```

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Building the Project

```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Package into JAR
mvn package

# Clean build artifacts
mvn clean
```

## Running the Application

### Using Maven

```bash
mvn exec:java
```

### Using the JAR file

```bash
java -jar target/robot-simulator-1.0-SNAPSHOT.jar
```

## Commands

| Command | Description |
|---------|-------------|
| `I n` or `i n` | Initialize the system with an n x n floor. Robot starts at [0, 0], pen up, facing north. |
| `U` or `u` | Pen up - robot moves without drawing |
| `D` or `d` | Pen down - robot draws while moving |
| `R` or `r` | Turn right (clockwise) |
| `L` or `l` | Turn left (counter-clockwise) |
| `M s` or `m s` | Move forward s spaces (s is a non-negative integer) |
| `P` or `p` | Print the floor with indices (asterisks for marked cells) |
| `C` or `c` | Print current position, pen state, and facing direction |
| `H` or `h` | Replay all commands in history since program start |
| `Q` or `q` | Quit the program |

### Command Input Format

- Commands are case-insensitive
- For `M` and `I` commands, the number can follow immediately or with a space:
  - `M5` or `M 5` - both are valid
  - `I10` or `I 10` - both are valid

## Example Session

```
Robot Simulator
Commands: I n (initialize), U (pen up), D (pen down), R (turn right),
          L (turn left), M s (move), P (print), C (status), H (history), Q (quit)

Enter command: I 10
Initialized 10 x 10 floor

Enter command: C
Position: 0, 0 - Pen: up - Facing: north

Enter command: D
Pen is now down

Enter command: M 4
Moved 4 spaces

Enter command: C
Position: 0, 4 - Pen: down - Facing: north

Enter command: R
Turned right

Enter command: M 3
Moved 3 spaces

Enter command: C
Position: 3, 4 - Pen: down - Facing: east

Enter command: P
  9 
  8 
  7 
  6 
  5 
  4 * * * * 
  3 * 
  2 * 
  1 * 
  0 * 
    0 1 2 3 4 5 6 7 8 9 

Enter command: Q
Goodbye!
```

## Coordinate System

- The floor is an N x N grid with coordinates starting at (0, 0) in the bottom-left corner
- X increases to the right (east)
- Y increases upward (north)
- The robot initially starts at position (0, 0), facing north

## Floor Display

When printing the floor:
- Marked cells (where the robot moved with pen down) show as `*`
- Unmarked cells show as blank spaces
- Row indices (Y) are shown on the left
- Column indices (X) are shown at the bottom

## Testing

The project includes comprehensive JUnit 5 tests covering:
- Direction enum operations (turning, deltas)
- Robot state management (position, direction, pen)
- Floor operations (marking, bounds checking, printing)
- Command parsing and execution
- Error handling
- Integration scenarios

Run all tests:
```bash
mvn test
```

Run a specific test class:
```bash
mvn test -Dtest=RobotSimulatorTest
```

## Author

COEN-6761 Project
