package br.edu.ifmg.libraryspirit.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.items.SimpleItem;

/**
 * Testes da classe {@link Room}.
 */
class RoomTest {

    @Test
    void keepsDescription() {
        Room hall = new Room("Hall de Entrada");
        assertEquals("Hall de Entrada", hall.getDescription());
    }

    @Test
    void returnsNeighborInGivenDirection() {
        Room hall = new Room("Hall de Entrada");
        Room horror = new Room("Seção de Terror");
        hall.setExit("oeste", horror);

        assertSame(horror, hall.getExit("oeste"));
    }

    @Test
    void returnsNullWhenThereIsNoExit() {
        Room hall = new Room("Hall de Entrada");
        assertNull(hall.getExit("norte"));
    }

    @Test
    void listsExitsInInsertionOrder() {
        Room hall = new Room("Hall de Entrada");
        hall.setExit("norte", new Room("Seção de Ficção"));
        hall.setExit("oeste", new Room("Seção de Terror"));

        assertEquals("Saídas: norte oeste", hall.getExitsDescription());
    }

    @Test
    void startsWithoutItems() {
        Room hall = new Room("Hall de Entrada");

        assertFalse(hall.hasItems());
        assertEquals("Itens: nenhum", hall.getItemsDescription());
    }

    @Test
    void addsAndRemovesItems() {
        Room hall = new Room("Hall de Entrada");
        Item key = new SimpleItem("chave");
        hall.addItem(key);

        assertEquals("Itens: chave", hall.getItemsDescription());
        assertSame(key, hall.removeItem("chave"));
        assertNull(hall.removeItem("chave"));
        assertFalse(hall.hasItems());
    }

    @Test
    void showsNoExitsWhenEmpty() {
        Room room = new Room("Sala isolada");
        assertEquals("Saídas: nenhuma", room.getExitsDescription());
    }
}
