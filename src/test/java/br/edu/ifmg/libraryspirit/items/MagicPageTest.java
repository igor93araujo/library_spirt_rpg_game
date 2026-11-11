package br.edu.ifmg.libraryspirit.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes de {@link MagicPage} e {@link Theme}.
 */
class MagicPageTest {

    @Test
    void pageHasThemeAndName() {
        MagicPage page = new MagicPage(Theme.SCI_FI);
        assertEquals(Theme.SCI_FI, page.getTheme());
        assertTrue(page.hasName("Pagina do Ficcao"));
    }

    @Test
    void usingPageHasNoEffect() {
        Player player = new Player(new Room("Hall"));
        new MagicPage(Theme.HORROR).use(player);
        assertEquals(Player.INITIAL_LIVES, player.getLives());
    }
}
