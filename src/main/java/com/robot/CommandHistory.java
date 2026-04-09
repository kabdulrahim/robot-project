package com.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Records executed commands for replay and inspection.
 */
public final class CommandHistory {
    private final List<String> entries = new ArrayList<>();

    /**
     * Appends a command line exactly as entered (after trim at parse time).
     */
    public void add(String trimmedCommand) {
        entries.add(trimmedCommand);
    }

    /**
     * Removes the last entry if the last processed command was invalid.
     */
    public void removeLast() {
        if (!entries.isEmpty()) {
            entries.remove(entries.size() - 1);
        }
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * @return an immutable copy of the current history (for callers that only read)
     */
    public List<String> getEntries() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }

    /**
     * @return a mutable copy of all entries (for replay snapshot)
     */
    public List<String> snapshot() {
        return new ArrayList<>(entries);
    }

    public void clear() {
        entries.clear();
    }

    /**
     * Replaces all entries (used after replay to restore pre-replay history).
     */
    public void replaceWith(List<String> other) {
        entries.clear();
        entries.addAll(other);
    }
}
