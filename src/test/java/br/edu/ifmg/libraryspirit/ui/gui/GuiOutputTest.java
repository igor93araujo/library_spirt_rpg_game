package br.edu.ifmg.libraryspirit.ui.gui;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuiOutputTest {

    @Test
    void deliversInOrderAndEmptyPrintlnIsBlankLine() {
        List<LogEntry> got = new ArrayList<>();
        GuiOutput out = new GuiOutput(got::add);

        out.println("a");
        out.println();
        out.println("b");

        assertEquals(List.of(
                new LogEntry("a", LogEntry.Type.NARRATIVE),
                new LogEntry("", LogEntry.Type.NARRATIVE),
                new LogEntry("b", LogEntry.Type.NARRATIVE)), got);
    }
}
