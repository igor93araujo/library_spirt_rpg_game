package br.edu.ifmg.libraryspirit.commands;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;
/**
 * Testes do {@link GoCommand} ao entrar na sala do chefe final.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class GoCommandBossTest {
    private Room hall;
    private Room bossRoom;
    private Player player;
    private FakeOutput output;
    private GreatTome greatTome;
    private GoCommand go;
    @BeforeEach
    void setUp() {
        hall = new Room("Hall");
        bossRoom = new Room("Sala Central");
        bossRoom.setBossRoom(true);
        hall.setExit("sul", bossRoom);
        bossRoom.setExit("norte", hall);
        player = new Player(hall);
        output = new FakeOutput();
        greatTome = new GreatTome();
        go = new GoCommand(output, player, greatTome);
    }
    @Test
    void showsCurrentThreatWhenEnteringBossRoom() {
        go.execute("sul");
        assertTrue(output.getAllText().contains("O GRANDE TOMO DESPERTOU"));
        assertTrue(output.getAllText().contains("Uma sombra escorre do livro"));
    }
    @Test
    void blocksLeavingBossRoomWhileBossThreatIsActive() {
        go.execute("sul");
        go.execute("norte");
        assertSame(bossRoom, player.getCurrentRoom());
        assertTrue(output.getAllText().contains("O Grande Tomo ainda ameaça você"));
    }
    @Test
    void allowsLeavingBossRoomAfterStageThreatsEnded() {
        go.execute("sul");
        finishStageRounds();
        go.execute("norte");
        assertSame(hall, player.getCurrentRoom());
    }
    @Test
    void doesNotFailWhenReenteringAfterStageThreatsEnded() {
        go.execute("sul");
        finishStageRounds();
        go.execute("norte");
        assertDoesNotThrow(() -> go.execute("sul"));
        assertSame(bossRoom, player.getCurrentRoom());
        assertTrue(output.getAllText().contains("As ameaças terminaram"));
    }
    @Test
    void doesNotFailWhenReenteringAfterBossDefeated() {
        go.execute("sul");
        while (!greatTome.isDefeated()) {
            finishStageRounds();
            greatTome.advanceStage();
        }
        go.execute("norte");
        output.getMessages().clear();
        assertDoesNotThrow(() -> go.execute("sul"));
        assertSame(bossRoom, player.getCurrentRoom());
        assertFalse(output.getAllText().contains("O GRANDE TOMO DESPERTOU"));
    }
    private void finishStageRounds() {
        while (!greatTome.getCurrentStage().isCompleted()) {
            greatTome.getCurrentStage().advanceRound();
        }
    }
}
