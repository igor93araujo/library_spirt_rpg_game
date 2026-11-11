package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Bookmark;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.items.Theme;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes de {@link UseCommand} e {@link StatusCommand}.
 */
class UseCommandTest {

    private final FakeOutput output = new FakeOutput();
    private final Player player = new Player(new Room("Hall"));

    @Test
    void asksWhatToUseWhenEmpty() {
        assertFalse(new UseCommand(output, player).execute(""));
        assertEquals("Usar o quê?", output.getAllText());
    }

    @Test
    void warnsWhenItemMissing() {
        new UseCommand(output, player).execute("vela");
        assertEquals("Você não tem nenhum(a) vela.", output.getAllText());
    }

    @Test
    void usesBookmarkAndConsumesIt() {
        player.addItem(new Bookmark(Bookmark.Type.LIFE));
        new UseCommand(output, player).execute("marcador de vida");
        assertEquals(Player.INITIAL_LIVES + 1, player.getLives());
        assertFalse(player.hasItem("marcador de vida"));
    }

    @Test
    void keepsNonConsumableItem() {
        player.addItem(new MagicPage(Theme.HORROR));
        new UseCommand(output, player).execute("pagina do terror");
        assertTrue(player.hasItem("pagina do terror"));
    }

    @Test
    void statusShowsLivesAndShield() {
        player.giveShield();
        new StatusCommand(output, player).execute("");
        assertTrue(output.getAllText().contains("Vidas: 3"));
        assertTrue(output.getAllText().contains("Escudo: ativo"));
    }
}
