package br.edu.ifmg.libraryspirit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Teste de fumaça: confirma que o JUnit 5 está configurado
 * e que a classe {@link Main} compila e executa.
 */
class MainTest {

    @Test
    void gameTitleIsCorrect() {
        assertEquals("O Espírito da Biblioteca", Main.GAME_TITLE);
    }

    @Test
    void mainRunsWithoutErrors() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
