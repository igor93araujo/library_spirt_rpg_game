package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "sair": encerra o jogo.
 * Assim como no World of Zuul, só funciona sem argumento,
 * para evitar que o jogador saia sem querer (ex.: "sair sala").
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class QuitCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /**
     * Cria o comando de sair.
     *
     * @param output onde as mensagens serão exibidas
     */
    public QuitCommand(Output output) {
        this.output = output;
    }

    /**
     * Encerra o jogo, se não houver argumento.
     *
     * @param argument deve estar vazio
     * @return {@code true} se o jogo deve terminar, {@code false} se havia argumento
     */
    @Override
    public boolean execute(String argument) {
        if (!argument.isEmpty()) {
            output.println("Sair de quê?");
            return false;
        }
        return true;
    }
}
