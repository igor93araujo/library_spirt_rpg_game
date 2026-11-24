package br.edu.ifmg.libraryspirit.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes do {@link DodgeCommand} durante o combate com o Grande Tomo.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class DodgeCommandBossTest {
    private Player player;
    private FakeOutput output;
    private GreatTome greatTome;

    @BeforeEach
    void setUp() {
        Room bossRoom = new Room("Sala Central");
        bossRoom.setBossRoom(true);
        player = new Player(bossRoom);
        output = new FakeOutput();
        greatTome = new GreatTome();
    }

    private DodgeCommand dodge(String action) {
        return new DodgeCommand(output, player, action, greatTome);
    }

    @Test
    void correctActionAdvancesToNextRound() {
        dodge("esconder").execute("");
        assertEquals(2, greatTome.getCurrentStage().getCurrentRound());
    }

    @Test
    void wrongActionCostsLifeAndKeepsRound() {
        int lives = player.getLives();
        dodge("pular").execute("");
        assertEquals(lives - 1, player.getLives());
        assertEquals(1, greatTome.getCurrentStage().getCurrentRound());
    }

    @Test
    void threeCorrectActionsEndTheStageThreats() {
        dodge("esconder").execute("");
        dodge("abaixar").execute("");
        dodge("desviar").execute("");
        assertTrue(greatTome.getCurrentStage().isCompleted());
        assertTrue(output.getAllText().contains("Agora use a página correspondente"));
    }
}
