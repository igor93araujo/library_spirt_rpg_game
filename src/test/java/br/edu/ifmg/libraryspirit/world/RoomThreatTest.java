package br.edu.ifmg.libraryspirit.world;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.characters.Threat;

/**
 * Testes das ameaças da {@link Room}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class RoomThreatTest {

    @Test
    void roomWithoutThreatHasNoActiveThreat() {
        Room room = new Room("Sala vazia");

        assertFalse(room.hasActiveThreat());
        assertNull(room.getThreat());
    }

    @Test
    void advancesThroughThreatsInOrder() {
        Room room = new Room("Sala com ameaças");
        room.addThreat(new Threat("Uma sombra desliza pela estante em sua direção...", "esconder"));
        room.addThreat(new Threat("Um livro de feitiços voa direto na sua cabeça!", "abaixar"));

        assertTrue(room.hasActiveThreat());
        assertTrue(room.getThreat().isCorrect("esconder"));

        room.resolveThreat();
        assertTrue(room.getThreat().isCorrect("abaixar"));

        room.resolveThreat();
        assertFalse(room.hasActiveThreat());
    }
}