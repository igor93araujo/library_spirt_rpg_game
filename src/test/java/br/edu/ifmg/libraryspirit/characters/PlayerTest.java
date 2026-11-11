package br.edu.ifmg.libraryspirit.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.items.SimpleItem;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link Player}.
 */
class PlayerTest {

    @Test
    void startsInStartRoom() {
        Room hall = new Room("Hall de Entrada");
        assertSame(hall, new Player(hall).getCurrentRoom());
    }

    @Test
    void movesToAnotherRoom() {
        Room hall = new Room("Hall de Entrada");
        Room horror = new Room("Seção de Terror");
        Player player = new Player(hall);

        player.moveTo(horror);

        assertSame(horror, player.getCurrentRoom());
    }

    @Test
    void startsWithEmptyInventory() {
        Player player = new Player(new Room("Hall"));

        assertFalse(player.hasItem("chave"));
        assertEquals("Você não carrega nada.", player.getInventoryDescription());
    }

    @Test
    void addsFindsAndRemovesItems() {
        Player player = new Player(new Room("Hall"));
        Item key = new SimpleItem("chave");

        player.addItem(key);
        assertTrue(player.hasItem("chave"));
        assertSame(key, player.getItem("chave"));
        assertEquals("Você carrega: chave", player.getInventoryDescription());

        assertSame(key, player.removeItem("chave"));
        assertNull(player.getItem("chave"));
    }

    @Test
    void startsWithThreeLivesAndNoShield() {
        Player player = new Player(new Room("Hall"));
        assertEquals(3, player.getLives());
        assertFalse(player.hasShield());
        assertTrue(player.isAlive());
    }

    @Test
    void damageRemovesLife() {
        Player player = new Player(new Room("Hall"));
        assertTrue(player.takeDamage());
        assertEquals(2, player.getLives());
    }

    @Test
    void shieldAbsorbsOneDamage() {
        Player player = new Player(new Room("Hall"));
        player.giveShield();
        assertFalse(player.takeDamage());
        assertEquals(3, player.getLives());
        assertFalse(player.hasShield());
    }

    @Test
    void diesWithoutLivesAndRestores() {
        Player player = new Player(new Room("Hall"));
        player.takeDamage();
        player.takeDamage();
        player.takeDamage();
        assertFalse(player.isAlive());
        player.restoreLives();
        assertEquals(3, player.getLives());
    }
}
