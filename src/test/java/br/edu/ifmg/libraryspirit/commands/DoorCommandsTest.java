package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Key;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Door;
import br.edu.ifmg.libraryspirit.world.Riddle;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes de {@link GoCommand} com portas e de {@link AnswerCommand}.
 */
class DoorCommandsTest {

    private FakeOutput output;
    private Room hall;
    private Room other;
    private Player player;

    @BeforeEach
    void setUp() {
        output = new FakeOutput();
        hall = new Room("Hall");
        other = new Room("Outra");
        hall.setExit("leste", other);
        player = new Player(hall);
    }

    @Test
    void lockedDoorBlocksMovement() {
        hall.setDoor("leste", new Door("chave"));
        new GoCommand(output, player).execute("leste");
        assertSame(hall, player.getCurrentRoom());
        assertTrue(output.getAllText().contains("A porta está trancada."));
    }

    @Test
    void keyOpensDoorAndPlayerPasses() {
        hall.setDoor("leste", new Door("chave"));
        player.addItem(new Key("chave", "x"));
        new GoCommand(output, player).execute("leste");
        assertSame(other, player.getCurrentRoom());
    }

    @Test
    void riddleFlowOpensDoor() {
        hall.setDoor("leste", new Door(List.of(), new Riddle("Qual?", "sim")));
        GoCommand go = new GoCommand(output, player);
        go.execute("leste");
        assertTrue(output.getAllText().contains("Qual?"));
        new AnswerCommand(output, player).execute("não");
        go.execute("leste");
        assertSame(hall, player.getCurrentRoom());
        new AnswerCommand(output, player).execute("sim");
        go.execute("leste");
        assertSame(other, player.getCurrentRoom());
    }

    @Test
    void answerWithoutRiddleWarns() {
        new AnswerCommand(output, player).execute("x");
        assertTrue(output.getAllText().contains("Não há nenhum enigma"));
    }
}
