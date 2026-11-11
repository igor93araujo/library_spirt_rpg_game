package br.edu.ifmg.libraryspirit.world;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Key;

/**
 * Testes de {@link Door} e {@link Riddle}.
 */
class DoorTest {

    private final Player player = new Player(new Room("Hall"));

    @Test
    void startsLockedAndStaysLockedWithoutKey() {
        Door door = new Door("chave");
        assertTrue(door.isLocked());
        assertFalse(door.unlock(player));
    }

    @Test
    void opensWithKey() {
        player.addItem(new Key("chave", "x"));
        Door door = new Door("chave");
        assertTrue(door.unlock(player));
        assertFalse(door.isLocked());
    }

    @Test
    void riddleDoorIgnoresPlainUnlock() {
        Door door = new Door(List.of(), new Riddle("Pergunta?", "Resposta"));
        assertFalse(door.unlock(player));
    }

    @Test
    void riddleAcceptsAnswerIgnoringCaseAndAccents() {
        Door door = new Door(List.of(), new Riddle("Pergunta?", "Ficção"));
        assertFalse(door.answer(player, "errado"));
        assertTrue(door.answer(player, "  FICCAO "));
    }

    @Test
    void riddleDoorNeedsItemsToo() {
        Door door = new Door(List.of("chave"), new Riddle("P?", "r"));
        assertFalse(door.answer(player, "r"));
        player.addItem(new Key("chave", "x"));
        assertTrue(door.answer(player, "r"));
    }
}
