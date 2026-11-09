package br.edu.ifmg.libraryspirit.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Testes da classe {@link Parser}.
 */
class ParserTest {

    private Parser parserFor(String text) {
        return new Parser(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void separatesCommandAndArgument() {
        ParsedInput input = parserFor("").parse("ir norte");

        assertEquals("ir", input.getCommandWord());
        assertEquals("norte", input.getArgument());
        assertTrue(input.hasArgument());
    }

    @Test
    void commandWithoutArgument() {
        ParsedInput input = parserFor("").parse("ajuda");

        assertEquals("ajuda", input.getCommandWord());
        assertFalse(input.hasArgument());
    }

    @Test
    void ignoresCaseAndExtraSpaces() {
        ParsedInput input = parserFor("").parse("  USAR   Pagina   Terror ");

        assertEquals("usar", input.getCommandWord());
        assertEquals("pagina terror", input.getArgument());
    }

    @Test
    void blankLineIsEmpty() {
        assertTrue(parserFor("").parse("   ").isEmpty());
    }

    @Test
    void readsLinesInOrderUntilInputEnds() {
        Parser parser = parserFor("ir norte\npegar chave\n");

        assertEquals("ir", parser.readInput().getCommandWord());
        assertEquals("pegar", parser.readInput().getCommandWord());
        assertFalse(parser.hasNextInput());
    }
}
