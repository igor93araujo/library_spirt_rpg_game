package br.edu.ifmg.libraryspirit.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.SimpleItem;
import br.edu.ifmg.libraryspirit.ui.FakeOutput;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Testes da classe {@link LookCommand}.
 */
class LookCommandTest {

    @Test
    void describesCurrentRoomAndExits() {
        FakeOutput output = new FakeOutput();
        Room hall = new Room("Hall de Entrada");
        hall.setExit("oeste", new Room("Seção de Terror"));
        LookCommand look = new LookCommand(output, new Player(hall));

        boolean ends = look.execute("");

        assertFalse(ends);
        assertEquals(List.of("Hall de Entrada", "Saídas: oeste"), output.getMessages());
    }

    @Test
    void showsItemsWhenRoomHasThem() {
        FakeOutput output = new FakeOutput();
        Room hall = new Room("Hall de Entrada");
        hall.addItem(new SimpleItem("chave"));

        new LookCommand(output, new Player(hall)).execute("");

        assertEquals(List.of("Hall de Entrada", "Itens: chave", "Saídas: nenhuma"),
                output.getMessages());
    }
}
