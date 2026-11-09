package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.SimpleItem;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link InventoryCommand}.
 */
class InventoryCommandTest {

    @Test
    void showsEmptyInventory() {
        FakeOutput output = new FakeOutput();
        Player player = new Player(new Room("Hall"));

        boolean ends = new InventoryCommand(output, player).execute("");

        assertFalse(ends);
        assertEquals("Você não carrega nada.", output.getAllText());
    }

    @Test
    void listsCarriedItems() {
        FakeOutput output = new FakeOutput();
        Player player = new Player(new Room("Hall"));
        player.addItem(new SimpleItem("chave"));
        player.addItem(new SimpleItem("vela"));

        new InventoryCommand(output, player).execute("");

        assertEquals("Você carrega: chave, vela", output.getAllText());
    }
}
