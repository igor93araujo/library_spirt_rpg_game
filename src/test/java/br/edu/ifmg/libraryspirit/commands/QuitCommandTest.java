package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.ui.FakeOutput;

/**
 * Testes da classe {@link QuitCommand}.
 */
class QuitCommandTest {

    @Test
    void endsGameWithoutArgument() {
        FakeOutput output = new FakeOutput();

        assertTrue(new QuitCommand(output).execute(""));
        assertTrue(output.getMessages().isEmpty());
    }

    @Test
    void doesNotEndGameWithArgument() {
        FakeOutput output = new FakeOutput();

        assertFalse(new QuitCommand(output).execute("sala"));
        assertEquals("Sair de quê?", output.getAllText());
    }
}
