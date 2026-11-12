package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes do {@link DodgeCommand}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class DodgeCommandTest {

    private Room room;
    private Player player;
    private FakeOutput output;

    @BeforeEach
    void setUp() {
        room = new Room("Sala de teste");
        room.addThreat(new Threat("Raízes brotam do chão sob seus pés!", "pular"));
        player = new Player(room);
        output = new FakeOutput();
    }

    private DodgeCommand dodge(String action) {
        return new DodgeCommand(output, player, action);
    }

    @Test
    void correctActionEscapesWithoutDamage() {
        boolean finished = dodge("pular").execute("");

        assertFalse(finished);
        assertEquals(Player.INITIAL_LIVES, player.getLives());
        assertFalse(room.hasActiveThreat());
        assertTrue(output.getAllText().contains("escapou"));
    }

    @Test
    void wrongActionCostsOneLife() {
        dodge("abaixar").execute("");

        assertEquals(Player.INITIAL_LIVES - 1, player.getLives());
        assertFalse(room.hasActiveThreat());
    }

    @Test
    void shieldAbsorbsWrongAction() {
        player.giveShield();

        dodge("abaixar").execute("");

        assertEquals(Player.INITIAL_LIVES, player.getLives());
        assertFalse(player.hasShield());
        assertTrue(output.getAllText().contains("escudo"));
    }

    @Test
    void doesNothingWithoutActiveThreat() {
        room.resolveThreat();

        dodge("pular").execute("");

        assertEquals(Player.INITIAL_LIVES, player.getLives());
        assertTrue(output.getAllText().contains("nada de que escapar"));
    }

    @Test
    void showsNextThreatAfterResolvingTheCurrentOne() {
        room.addThreat(new Threat("Um livro de feitiços voa direto na sua cabeça!", "abaixar"));

        dodge("pular").execute("");

        assertTrue(room.hasActiveThreat());
        assertTrue(output.getAllText().contains("livro de feitiços"));
    }

    @Test
    void endsTheGameWhenLivesRunOut() {
        // 3 ameaças no total, iguais às 3 vidas iniciais
        room.addThreat(new Threat("Outra ameaça", "pular"));
        room.addThreat(new Threat("Mais uma ameaça", "pular"));

        assertFalse(dodge("abaixar").execute(""));
        assertFalse(dodge("abaixar").execute(""));
        assertTrue(dodge("abaixar").execute(""));
        assertFalse(player.isAlive());
    }
}