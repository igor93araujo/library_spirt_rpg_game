package br.edu.libraryspirit;

/**
 * Ponto de entrada do jogo O Espírito da Biblioteca.
 * Nesta fase é apenas um placeholder: exibe o título do jogo.
 * Na Fase 1 passará a criar o {@code Game} e chamar {@code play()}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 0.1
 */
public class Main {

    /** Título do jogo exibido ao jogador. */
    public static final String GAME_TITLE = "O Espírito da Biblioteca";

    /**
     * Inicia o programa exibindo o título do jogo.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println(GAME_TITLE);
        System.out.println("Jogo em construção. Volte nas próximas fases!");
    }
}
