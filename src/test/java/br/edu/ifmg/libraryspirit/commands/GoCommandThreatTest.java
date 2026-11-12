package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes do {@link GoCommand} com ameaças nas salas.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class GoCommandThreatTest {

    private Room hall;
    private Room horror;
    private Player player;
    private FakeOutput output;
    private GoCommand go;

    @BeforeEach
    void setUp() {
        hall = new Room("Hall");
        horror = new Room("Terror");
        hall.setExit("oeste", horror);
        horror.setExit("leste", hall);
        horror.addThreat(new Threat("Uma sombra desliza pela estante em sua direção...", "esconder"));
        player = new Player(hall);
        output = new FakeOutput();
        go = new GoCommand(output, player);
    }

    @Test
    void showsThreatWhenEnteringRoom() {
        go.execute("oeste");

        assertTrue(output.getAllText().contains("Uma sombra desliza"));
    }

    @Test
    void blocksLeavingWhileThreatIsActive() {
        go.execute("oeste");
        go.execute("leste");

        assertSame(horror, player.getCurrentRoom());
    }

    @Test
    void allowsLeavingAfterThreatIsResolved() {
        go.execute("oeste");
        horror.resolveThreat();
        go.execute("leste");

        assertSame(hall, player.getCurrentRoom());
    }
}