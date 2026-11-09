package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link GoCommand}.
 */
class GoCommandTest {

    private FakeOutput output;
    private Room hall;
    private Room horror;
    private Player player;
    private GoCommand go;

    @BeforeEach
    void setUp() {
        output = new FakeOutput();
        hall = new Room("Hall de Entrada");
        horror = new Room("Seção de Terror");
        hall.setExit("oeste", horror);
        player = new Player(hall);
        go = new GoCommand(output, player);
    }

    @Test
    void movesToNeighborAndShowsIt() {
        boolean ends = go.execute("oeste");

        assertFalse(ends);
        assertSame(horror, player.getCurrentRoom());
        assertTrue(output.getAllText().contains("Seção de Terror"));
    }

    @Test
    void acceptsParaBeforeDirection() {
        go.execute("para oeste");

        assertSame(horror, player.getCurrentRoom());
    }

    @Test
    void asksDirectionWhenOnlyParaIsTyped() {
        go.execute("para");

        assertSame(hall, player.getCurrentRoom());
        assertEquals("Ir para onde?", output.getAllText());
    }

    @Test
    void staysWhenThereIsNoExit() {
        go.execute("norte");

        assertSame(hall, player.getCurrentRoom());
        assertEquals("Não há passagem nessa direção.", output.getAllText());
    }

    @Test
    void asksDirectionWhenArgumentIsMissing() {
        go.execute("");

        assertSame(hall, player.getCurrentRoom());
        assertEquals("Ir para onde?", output.getAllText());
    }
}
