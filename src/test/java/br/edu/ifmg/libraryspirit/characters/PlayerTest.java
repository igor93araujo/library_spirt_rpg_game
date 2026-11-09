package br.edu.ifmg.libraryspirit.characters;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

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
}
