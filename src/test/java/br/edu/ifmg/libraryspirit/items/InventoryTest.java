package br.edu.ifmg.libraryspirit.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testes da classe {@link Inventory}.
 */
class InventoryTest {

    @Test
    void startsEmpty() {
        Inventory inventory = new Inventory();

        assertTrue(inventory.isEmpty());
        assertEquals("", inventory.getNames());
    }

    @Test
    void findsItemByNameIgnoringCase() {
        Inventory inventory = new Inventory();
        Item key = new SimpleItem("Chave de Bronze");
        inventory.add(key);

        assertSame(key, inventory.find("chave de bronze"));
        assertTrue(inventory.contains("chave de bronze"));
        assertNull(inventory.find("vela"));
    }

    @Test
    void removesItem() {
        Inventory inventory = new Inventory();
        Item key = new SimpleItem("chave");
        inventory.add(key);

        assertSame(key, inventory.remove("chave"));
        assertFalse(inventory.contains("chave"));
        assertNull(inventory.remove("chave"));
    }

    @Test
    void listsNamesInOrder() {
        Inventory inventory = new Inventory();
        inventory.add(new SimpleItem("chave"));
        inventory.add(new SimpleItem("vela"));

        assertEquals("chave, vela", inventory.getNames());
    }
}
