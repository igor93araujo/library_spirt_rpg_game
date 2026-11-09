package br.edu.ifmg.libraryspirit.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.commands.HelpCommand;
import br.edu.ifmg.libraryspirit.commands.QuitCommand;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link Game}.
 * O jogador é simulado com um texto pronto no lugar do teclado.
 */
class GameTest {

    private FakeOutput output;

    /** Roda uma partida com as linhas informadas como entrada. */
    private void playWith(String typed) {
        output = new FakeOutput();
        Parser parser = new Parser(new ByteArrayInputStream(typed.getBytes(StandardCharsets.UTF_8)));
        CommandWords words = new CommandWords();
        words.register("ajuda", new HelpCommand(output, words));
        words.register("sair", new QuitCommand(output));
        Player player = new Player(new Room("Você está no Hall de Entrada."));

        new Game(parser, output, words, player).play();
    }

    @Test
    void showsTitleAndStartRoom() {
        playWith("sair\n");

        assertTrue(output.getAllText().contains(Game.TITLE));
        assertTrue(output.getAllText().contains("Você está no Hall de Entrada."));
    }

    @Test
    void executesCommandsUntilQuit() {
        playWith("ajuda\nsair\najuda\n");

        String text = output.getAllText();
        assertTrue(text.contains("Comandos: ajuda sair"));
        // O segundo "ajuda" não roda, porque o jogo já terminou.
        assertEquals(text.indexOf("Comandos:"), text.lastIndexOf("Comandos:"));
    }

    @Test
    void warnsAboutUnknownCommand() {
        playWith("voar\nsair\n");

        assertTrue(output.getAllText().contains("Não entendi."));
    }

    @Test
    void ignoresBlankLines() {
        playWith("\n   \nsair\n");

        assertFalse(output.getAllText().contains("Não entendi."));
    }

    @Test
    void endsWhenInputEndsAndSaysGoodbye() {
        playWith("");

        List<String> messages = output.getMessages();
        assertEquals("Obrigado por jogar. Até a próxima!", messages.get(messages.size() - 1));
    }
}
