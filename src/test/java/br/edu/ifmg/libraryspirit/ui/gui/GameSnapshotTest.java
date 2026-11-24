package br.edu.ifmg.libraryspirit.ui.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Bookmark;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.items.Theme;
import br.edu.ifmg.libraryspirit.world.WorldBuilder;

class GameSnapshotTest {

    @Test
    void initialHallSnapshot() {
        Player player = new Player(new WorldBuilder().build());
        GameSnapshot s = GameSnapshot.of(player);

        assertEquals(SceneId.HALL, s.scene());
        assertEquals("Hall de Entrada", s.roomName());
        assertEquals(List.of("norte", "sul", "leste", "oeste"), s.exits());
        assertEquals(Set.of("norte", "sul", "leste"), s.lockedExits());
        assertEquals(1, s.roomItems().size());
        ItemView key = s.roomItems().get(0);
        assertEquals("chave de bronze", key.name());
        assertEquals(ItemView.Kind.KEY, key.kind());
        assertTrue(s.inventory().isEmpty());
        assertEquals(3, s.lives());
        assertFalse(s.shield());
    }

    @Test
    void reflectsInventoryShieldAndPageTheme() {
        Player player = new Player(new WorldBuilder().build());
        player.addItem(new MagicPage(Theme.HORROR));
        player.addItem(new Bookmark(Bookmark.Type.SHIELD));
        player.giveShield();
        player.takeDamage();
        player.giveShield();

        GameSnapshot s = GameSnapshot.of(player);

        assertTrue(s.shield());
        assertEquals(ItemView.Kind.PAGE, s.inventory().get(0).kind());
        assertEquals(Theme.HORROR, s.inventory().get(0).theme());
        assertEquals(ItemView.Kind.BOOKMARK, s.inventory().get(1).kind());
        assertNull(s.inventory().get(1).theme());
    }
}
