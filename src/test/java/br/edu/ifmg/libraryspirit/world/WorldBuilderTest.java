package br.edu.ifmg.libraryspirit.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testes da classe {@link WorldBuilder}.
 */
class WorldBuilderTest {

    private Room hall;

    @BeforeEach
    void setUp() {
        hall = new WorldBuilder().build();
    }

    @Test
    void startsInHall() {
        assertTrue(hall.getDescription().contains("Hall de Entrada"));
        assertEquals("Saídas: oeste leste norte sul", hall.getExitsDescription());
    }

    @Test
    void hallLeadsToTheFourRooms() {
        assertTrue(hall.getExit("oeste").getDescription().contains("Seção de Terror"));
        assertTrue(hall.getExit("leste").getDescription().contains("Seção de Aventura"));
        assertTrue(hall.getExit("norte").getDescription().contains("Ficção Científica"));
        assertTrue(hall.getExit("sul").getDescription().contains("Sala de Leitura Central"));
    }

    @Test
    void everyRoomLeadsBackToHall() {
        assertSame(hall, hall.getExit("oeste").getExit("leste"));
        assertSame(hall, hall.getExit("leste").getExit("oeste"));
        assertSame(hall, hall.getExit("norte").getExit("sul"));
        assertSame(hall, hall.getExit("sul").getExit("norte"));
    }

    @Test
    void sectionsHaveOnlyOneExit() {
        Room horror = hall.getExit("oeste");
        assertNotNull(horror);
        assertEquals("Saídas: leste", horror.getExitsDescription());
    }
}
