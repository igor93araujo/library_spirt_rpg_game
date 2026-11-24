package br.edu.ifmg.libraryspirit.game;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameSetupTest {

    @Test
    void registersAllCommands() {
        GameSetup setup = GameSetup.create(new FakeOutput());
        CommandWords words = setup.getCommandWords();
        List<String> expected = new java.util.ArrayList<>(List.of(
                "ir", "olhar", "pegar", "inventario", "responder", "usar",
                "status", "ajuda", "sair"));
        expected.addAll(Threat.VALID_ACTIONS);
        for (String word : expected) {
            assertTrue(words.isCommand(word), "comando ausente: " + word);
        }
    }

    @Test
    void exposesPlayerAndBossAndCreatesFreshGames() {
        GameSetup a = GameSetup.create(new FakeOutput());
        GameSetup b = GameSetup.create(new FakeOutput());
        assertNotNull(a.getPlayer());
        assertNotNull(a.getGreatTome());
        assertNotSame(a.getPlayer(), b.getPlayer());
    }
}
