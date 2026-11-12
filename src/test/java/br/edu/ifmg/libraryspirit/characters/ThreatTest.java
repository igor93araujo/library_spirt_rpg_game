package br.edu.ifmg.libraryspirit.characters;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testes da classe {@link Threat}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class ThreatTest {

    @Test
    void acceptsOnlyTheCorrectAction() {
        Threat threat = new Threat("Um livro voa na sua cabeça!", "abaixar");

        assertTrue(threat.isCorrect("abaixar"));
        assertFalse(threat.isCorrect("pular"));
    }

    @Test
    void keepsTheDescription() {
        Threat threat = new Threat("Raízes brotam do chão!", "pular");

        assertTrue(threat.getDescription().contains("Raízes"));
    }

    @Test
    void rejectsInvalidAction() {
        assertThrows(IllegalArgumentException.class,
                () -> new Threat("Uma sombra desliza pela estante em sua direção...", "correr"));
    }
}