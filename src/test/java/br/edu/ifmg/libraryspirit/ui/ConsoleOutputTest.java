package br.edu.ifmg.libraryspirit.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Testes da classe {@link ConsoleOutput}.
 */
class ConsoleOutputTest {

    @Test
    void printsMessageToConsole() {
        PrintStream original = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
        try {
            ConsoleOutput output = new ConsoleOutput();
            output.print("> ");
            output.println("Olá, espírito!");
            output.println();
        } finally {
            System.setOut(original);
        }

        String nl = System.lineSeparator();
        assertEquals("> Olá, espírito!" + nl + nl, captured.toString(StandardCharsets.UTF_8));
    }
}
