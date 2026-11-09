package br.edu.ifmg.libraryspirit.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link Item}, usando o {@link SimpleItem} de teste.
 */
class ItemTest {

    @Test
    void keepsNameAndDescription() {
        Item item = new SimpleItem("chave de bronze");

        assertEquals("chave de bronze", item.getName());
        assertEquals("Um item de teste.", item.getDescription());
    }

    @Test
    void comparesNameIgnoringCase() {
        Item item = new SimpleItem("Chave de Bronze");

        assertTrue(item.hasName("chave de bronze"));
        assertFalse(item.hasName("chave de prata"));
    }

    @Test
    void subclassDefinesWhatUseDoes() {
        Item item = new SimpleItem("vela");

        assertEquals("Você usou vela.", item.use(new Player(new Room("Hall"))));
    }
}
