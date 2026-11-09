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
     * Exibe um texto sem pular linha no final.
     * Útil para o prompt "&gt; ", onde o jogador digita na mesma linha.
     *
     * @param text texto a ser exibido
     */
    void print(String text);

    /**
     * Exibe uma mensagem para o jogador, seguida de quebra de linha.
     *
     * @param message texto a ser exibido
     */
    void println(String message);

    /**
     * Exibe uma linha em branco, para separar blocos de texto.
     * Método {@code default}: já vem pronto para todas as implementações.
     */
    default void println() {
        println("");
    }
}
