package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.items.Theme;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da derrota e da vitória contra o Grande Tomo.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class BossDefeatAndVictoryTest {

    private Room hall;
    private Room bossRoom;
    private Player player;
    private FakeOutput output;
    private GreatTome greatTome;

    @BeforeEach
    void setUp() {
        hall = new Room("Hall");
        bossRoom = new Room("Sala Central");
        bossRoom.setBossRoom(true);
        player = new Player(bossRoom);
        output = new FakeOutput();
        greatTome = new GreatTome();
    }

    // ---------- Derrota no chefe (item 32) ----------

    @Test
    void losingAllLivesInBossRestoresLivesAndDoesNotEndGame() {
        boolean finished = false;
        for (int i = 0; i < Player.INITIAL_LIVES; i++) {
            finished = new DodgeCommand(output, player, wrongAction(), greatTome).execute("");
        }

        assertFalse(finished);
        assertEquals(Player.INITIAL_LIVES, player.getLives());
        assertTrue(player.isAlive());
        assertTrue(output.getAllText().contains("O confronto recomeça"));
    }

    @Test
    void losingInBossKeepsPagesInInventory() {
        player.addItem(new MagicPage(Theme.HORROR));
        player.addItem(new MagicPage(Theme.ADVENTURE));
        player.addItem(new MagicPage(Theme.SCI_FI));

        for (int i = 0; i < Player.INITIAL_LIVES; i++) {
            new DodgeCommand(output, player, wrongAction(), greatTome).execute("");
        }

        assertTrue(player.hasItem("pagina do terror"));
        assertTrue(player.hasItem("pagina do aventura"));
        assertTrue(player.hasItem("pagina do ficcao"));
    }

    @Test
    void losingInBossRestartsFromFirstStage() {
        completeCurrentStage();
        greatTome.advanceStage();
        assertEquals(Theme.ADVENTURE, greatTome.getCurrentStage().getTheme());

        for (int i = 0; i < Player.INITIAL_LIVES; i++) {
            new DodgeCommand(output, player, wrongAction(), greatTome).execute("");
        }

        assertEquals(Theme.HORROR, greatTome.getCurrentStage().getTheme());
        assertEquals(1, greatTome.getCurrentStage().getCurrentRound());
    }

    @Test
    void losingOutsideBossStillEndsGame() {
        player.moveTo(hall);
        hall.addThreat(new Threat("Uma sombra se aproxima!", "esconder"));

        boolean finished = false;
        for (int i = 0; i < Player.INITIAL_LIVES; i++) {
            finished = new DodgeCommand(output, player, "pular", greatTome).execute("");
        }

        assertTrue(finished);
        assertFalse(player.isAlive());
    }

    // ---------- Vitória (item 33) ----------

    @Test
    void sealingAllThreeStagesShowsVictoryAndEndsGame() {
        player.addItem(new MagicPage(Theme.HORROR));
        player.addItem(new MagicPage(Theme.ADVENTURE));
        player.addItem(new MagicPage(Theme.SCI_FI));
        UseCommand use = new UseCommand(output, player, greatTome);

        completeCurrentStage();
        assertFalse(use.execute("pagina do terror"));

        completeCurrentStage();
        assertFalse(use.execute("pagina do aventura"));

        completeCurrentStage();
        assertTrue(use.execute("pagina do ficcao"));

        assertTrue(greatTome.isDefeated());
        assertTrue(output.getAllText().contains("TERCEIRO SELO RESTAURADO"));
        assertTrue(output.getAllText().contains("VOCÊ VENCEU"));
    }

    @Test
    void wrongPageDoesNotSealNorEndGame() {
        player.addItem(new MagicPage(Theme.ADVENTURE));
        UseCommand use = new UseCommand(output, player, greatTome);

        completeCurrentStage();
        assertFalse(use.execute("pagina do aventura"));

        assertTrue(output.getAllText().contains("Esta não é a página correta"));
        assertEquals(Theme.HORROR, greatTome.getCurrentStage().getTheme());
        assertFalse(greatTome.isDefeated());
    }

    // ---------- Auxiliares ----------

    /** Conclui todas as rodadas da etapa atual do chefe. */
    private void completeCurrentStage() {
        while (!greatTome.getCurrentStage().isCompleted()) {
            greatTome.getCurrentStage().advanceRound();
        }
    }

    /** Devolve uma ação que NÃO resolve a ameaça atual do chefe. */
    private String wrongAction() {
        Threat threat = greatTome.getCurrentStage().getCurrentThreat();
        for (String action : Threat.VALID_ACTIONS) {
            if (!threat.isCorrect(action)) {
                return action;
            }
        }
        throw new IllegalStateException("Nenhuma ação errada encontrada");
    }
}