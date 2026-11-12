package br.edu.ifmg.libraryspirit.world;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testes das ameaças montadas pelo {@link WorldBuilder}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class WorldBuilderThreatsTest {

    @Test
    void themedRoomsHaveThreats() {
        Room hall = new WorldBuilder().build();

        assertTrue(hall.getExit("oeste").hasActiveThreat());
        assertTrue(hall.getExit("leste").hasActiveThreat());
        assertTrue(hall.getExit("norte").hasActiveThreat());
    }

    @Test
    void hallAndReadingRoomHaveNoThreats() {
        Room hall = new WorldBuilder().build();

        assertFalse(hall.hasActiveThreat());
        assertFalse(hall.getExit("sul").hasActiveThreat());
    }

    @Test
    void adventureRoomHasThreeThreatsInOrder() {
        Room adventure = new WorldBuilder().build().getExit("leste");

        assertTrue(adventure.getThreat().isCorrect("pular"));
        adventure.resolveThreat();
        assertTrue(adventure.getThreat().isCorrect("abaixar"));
        adventure.resolveThreat();
        assertTrue(adventure.getThreat().isCorrect("desviar"));
        adventure.resolveThreat();
        assertFalse(adventure.hasActiveThreat());
    }

    @Test
    void sciFiRoomHasTwoThreatsInOrder() {
        Room sciFi = new WorldBuilder().build().getExit("norte");

        assertTrue(sciFi.getThreat().isCorrect("desviar"));
        sciFi.resolveThreat();
        assertTrue(sciFi.getThreat().isCorrect("esconder"));
        sciFi.resolveThreat();
        assertFalse(sciFi.hasActiveThreat());
    }
}