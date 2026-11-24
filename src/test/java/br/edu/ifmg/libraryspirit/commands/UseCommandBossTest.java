package br.edu.ifmg.libraryspirit.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.items.Theme;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;
/**
 * Testes do {@link UseCommand} com as páginas mágicas e o Grande Tomo.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class UseCommandBossTest {
    private Room hall;
    private Room bossRoom;
    private Player player;
    private FakeOutput output;
    private GreatTome greatTome;
    private UseCommand use;
    @BeforeEach
    void setUp() {
        hall = new Room("Hall");
        bossRoom = new Room("Sala Central");
        bossRoom.setBossRoom(true);
        player = new Player(hall);
        player.addItem(new MagicPage(Theme.HORROR));
        output = new FakeOutput();
        greatTome = new GreatTome();
        use = new UseCommand(output, player, greatTome);
    }
    @Test
    void pageOutsideBossRoomKeepsDefaultMessage() {
        use.execute("pagina do terror");
        assertEquals("A página brilha, mas ainda não é o momento de usá-la.", output.getAllText());
    }
    @Test
    void pageInBossRoomBeforeThreatIsOvercomeIsRefused() {
        player.moveTo(bossRoom);
        use.execute("pagina do terror");
        assertTrue(output.getAllText().contains("A ameaça ainda não foi superada"));
        assertFalse(greatTome.getCurrentStage().isCompleted());
    }
    @Test
    void correctPageInBossRoomSealsStageAndAdvances() {
        player.moveTo(bossRoom);
        greatTome.getCurrentStage().advanceRound();
        use.execute("pagina do terror");
        assertTrue(output.getAllText().contains("PRIMEIRO SELO RESTAURADO"));
        assertEquals(Theme.ADVENTURE, greatTome.getCurrentStage().getTheme());
    }
}
