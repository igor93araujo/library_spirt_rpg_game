package br.edu.ifmg.libraryspirit.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.commands.Command;

/**
 * Testes da classe {@link CommandWords}.
 * Os comandos aqui são lambdas simples, só para testar o registro.
 */
class CommandWordsTest {

    @Test
    void returnsRegisteredCommand() {
        CommandWords words = new CommandWords();
        Command help = argument -> false;
        words.register("ajuda", help);

        assertSame(help, words.get("ajuda"));
        assertTrue(words.isCommand("ajuda"));
    }

    @Test
    void unknownWordIsNotCommand() {
        CommandWords words = new CommandWords();

        assertNull(words.get("voar"));
        assertFalse(words.isCommand("voar"));
    }

    @Test
    void listsWordsInRegistrationOrder() {
        CommandWords words = new CommandWords();
        words.register("ajuda", argument -> false);
        words.register("sair", argument -> true);

        assertEquals("ajuda sair", words.getAllWords());
    }
}
