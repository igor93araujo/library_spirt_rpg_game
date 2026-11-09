package br.edu.ifmg.libraryspirit.ui;

/**
 * Define como o jogo exibe mensagens para o jogador.
 * O resto do jogo depende desta interface, e não de {@code System.out}.
 * Assim dá para trocar a saída (por exemplo, por uma saída falsa nos testes)
 * sem mudar as outras classes (princípio da Inversão de Dependência).
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public interface Output {

    /**
     * Exibe uma mensagem para o jogador, seguida de quebra de linha.
     *
     * @param message texto a ser exibido
     */
    void print(String message);
}
