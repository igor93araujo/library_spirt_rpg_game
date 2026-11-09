package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.game.CommandWords;
import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "ajuda": lembra o jogador do objetivo e lista os comandos disponíveis.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class HelpCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Comandos do jogo, usados para montar a lista de palavras. */
    private final CommandWords commandWords;

    /**
     * Cria o comando de ajuda.
     *
     * @param output onde a ajuda será exibida
     * @param commandWords comandos do jogo que serão listados
     */
    public HelpCommand(Output output, CommandWords commandWords) {
        this.output = output;
        this.commandWords = commandWords;
    }

    /**
     * Exibe a ajuda. O argumento é ignorado.
     *
     * @param argument ignorado
     * @return sempre {@code false}, pois pedir ajuda não termina o jogo
     */
    @Override
    public boolean execute(String argument) {
        output.print("Você é o espírito guardião da biblioteca.");
        output.print("Recolha as páginas mágicas e sele o Grande Tomo.");
        output.print("");
        output.print("Comandos: " + commandWords.getAllWords());
        return false;
    }
}
