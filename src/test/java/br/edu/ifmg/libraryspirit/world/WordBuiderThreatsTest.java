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
}