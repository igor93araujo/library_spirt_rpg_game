package br.edu.ifmg.libraryspirit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Teste de fumaça: confirma que o {@link Main} monta o jogo e roda uma partida.
 * O teclado é trocado por um texto pronto para o teste não ficar esperando.
 */
class MainTest {

    @Test
    void mainRunsAndQuits() {
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("ir oeste\nsair\n".getBytes(StandardCharsets.UTF_8)));
        try {
            assertDoesNotThrow(() -> Main.main(new String[0]));
        } finally {
            System.setIn(originalIn);
        }
    }
}
