package com.robot;

/**
 * Result of parsing a single simulator command line (command letter + remainder).
 */
public final class ParsedCommand {
    private final String trimmed;
    private final String command;
    private final String argument;

    ParsedCommand(String trimmed, String command, String argument) {
        this.trimmed = trimmed;
        this.command = command;
        this.argument = argument;
    }

    /**
     * @return the full trimmed user input line
     */
    public String getTrimmed() {
        return trimmed;
    }

    /**
     * @return the command character, uppercased (length 1)
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return text after the command letter, trimmed (may be empty)
     */
    public String getArgument() {
        return argument;
    }
}
