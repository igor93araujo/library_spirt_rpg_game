package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.SimpleItem;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link TakeCommand}.
 */
class TakeCommandTest {

    private FakeOutput output;
    private Room hall;
    private Player player;
    private TakeCommand take;

    @BeforeEach
    void setUp() {
        output = new FakeOutput();
        hall = new Room("Hall de Entrada");
        hall.addItem(new SimpleItem("chave de bronze"));
        player = new Player(hall);
        take = new TakeCommand(output, player);
    }

    @Test
    void movesItemFromRoomToPlayer() {
        boolean ends = take.execute("chave de bronze");

        assertFalse(ends);
        assertTrue(player.hasItem("chave de bronze"));
        assertFalse(hall.hasItems());
        assertTrue(output.getAllText().contains("Você pegou: chave de bronze."));
    }

    @Test
    void warnsWhenItemIsNotInRoom() {
        take.execute("vela");

        assertFalse(player.hasItem("vela"));
        assertTrue(hall.hasItems());
        assertEquals("Não há nenhum(a) vela aqui.", output.getAllText());
    }

    @Test
    void asksWhatWhenArgumentIsMissing() {
        take.execute("");

        assertEquals("Pegar o quê?", output.getAllText());
    }
}
