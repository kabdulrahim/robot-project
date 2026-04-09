package com.robot;

/**
 * Parses raw command lines into a command letter and argument text.
 */
public final class CommandParser {

    private CommandParser() {
    }

    /**
     * Parses user input into command and argument.
     *
     * @param input raw line (may be null or blank)
     * @return parsed command, or {@code null} if input is null, empty, or whitespace-only
     */
    public static ParsedCommand parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        String trimmed = input.trim();
        String command = trimmed.substring(0, 1).toUpperCase();
        String argument = trimmed.length() > 1 ? trimmed.substring(1).trim() : "";
        return new ParsedCommand(trimmed, command, argument);
    }
}
