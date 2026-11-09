package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.game.CommandWords;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;

/**
 * Testes da classe {@link HelpCommand}.
 */
class HelpCommandTest {

    @Test
    void listsAvailableCommandsAndDoesNotEndGame() {
        FakeOutput output = new FakeOutput();
        CommandWords words = new CommandWords();
        words.register("ajuda", new HelpCommand(output, words));
        words.register("sair", new QuitCommand(output));

        boolean ends = words.get("ajuda").execute("");

        assertFalse(ends);
        assertTrue(output.getAllText().contains("Comandos: ajuda sair"));
    }
}
