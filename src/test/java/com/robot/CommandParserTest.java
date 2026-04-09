package com.robot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parseNullReturnsNull() {
        assertNull(CommandParser.parse(null));
    }

    @Test
    void parseBlankReturnsNull() {
        assertNull(CommandParser.parse(""));
        assertNull(CommandParser.parse("   "));
    }

    @Test
    void parseSingleLetter() {
        ParsedCommand p = CommandParser.parse("q");
        assertNotNull(p);
        assertEquals("q", p.getTrimmed());
        assertEquals("Q", p.getCommand());
        assertEquals("", p.getArgument());
    }

    @Test
    void parseCommandWithArgument() {
        ParsedCommand p = CommandParser.parse("m  5");
        assertNotNull(p);
        assertEquals("m  5", p.getTrimmed());
        assertEquals("M", p.getCommand());
        assertEquals("5", p.getArgument());
    }

    @Test
    void parseNoSpaceAfterLetter() {
        ParsedCommand p = CommandParser.parse("M5");
        assertNotNull(p);
        assertEquals("M5", p.getTrimmed());
        assertEquals("M", p.getCommand());
        assertEquals("5", p.getArgument());
    }
}
