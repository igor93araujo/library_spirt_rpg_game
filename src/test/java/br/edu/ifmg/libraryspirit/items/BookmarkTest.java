package br.edu.ifmg.libraryspirit.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes de {@link Bookmark}.
 */
class BookmarkTest {

    @Test
    void lifeBookmarkAddsLife() {
        Player player = new Player(new Room("Hall"));
        new Bookmark(Bookmark.Type.LIFE).use(player);
        assertEquals(Player.INITIAL_LIVES + 1, player.getLives());
    }

    @Test
    void shieldBookmarkGivesShield() {
        Player player = new Player(new Room("Hall"));
        new Bookmark(Bookmark.Type.SHIELD).use(player);
        assertTrue(player.hasShield());
    }

    @Test
    void bookmarksAreConsumable() {
        assertTrue(new Bookmark(Bookmark.Type.LIFE).isConsumable());
    }
}
