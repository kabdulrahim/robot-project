package com.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    void setUp() {
        history = new CommandHistory();
    }

    @Test
    void startsEmpty() {
        assertTrue(history.isEmpty());
        assertTrue(history.getEntries().isEmpty());
    }

    @Test
    void addAndSnapshot() {
        history.add("I 10");
        history.add("D");
        List<String> snap = history.snapshot();
        assertEquals(Arrays.asList("I 10", "D"), snap);
        snap.add("X");
        assertEquals(2, history.getEntries().size());
    }

    @Test
    void removeLast() {
        history.add("a");
        history.add("b");
        history.removeLast();
        assertEquals(List.of("a"), history.getEntries());
        history.removeLast();
        assertTrue(history.isEmpty());
        history.removeLast();
        assertTrue(history.isEmpty());
    }

    @Test
    void clearAndReplaceWith() {
        history.add("I 5");
        List<String> saved = history.snapshot();
        history.clear();
        assertTrue(history.isEmpty());
        history.replaceWith(saved);
        assertEquals(List.of("I 5"), history.getEntries());
    }
}
