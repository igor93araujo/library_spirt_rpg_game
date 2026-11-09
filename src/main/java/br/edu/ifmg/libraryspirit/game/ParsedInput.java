package br.edu.ifmg.libraryspirit.game;

/**
 * Resultado da leitura de uma linha digitada pelo jogador.
 * Separa a palavra de comando (ex.: "ir") do argumento (ex.: "norte").
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class ParsedInput {

    /** Primeira palavra digitada, ou texto vazio se a linha estava em branco. */
    private final String commandWord;

    /** Restante da linha depois da primeira palavra, ou texto vazio. */
    private final String argument;

    /**
     * Cria o resultado da leitura.
     *
     * @param commandWord palavra de comando (nunca {@code null})
     * @param argument argumento do comando (nunca {@code null})
     */
    public ParsedInput(String commandWord, String argument) {
        this.commandWord = commandWord;
        this.argument = argument;
    }

    /**
     * Retorna a palavra de comando.
     *
     * @return a palavra de comando, ou texto vazio
     */
    public String getCommandWord() {
        return commandWord;
    }

    /**
     * Retorna o argumento do comando.
     *
     * @return o argumento (ex.: "norte" ou "pagina terror"), ou texto vazio
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Informa se o jogador digitou algo além da palavra de comando.
     *
     * @return {@code true} se existe argumento
     */
    public boolean hasArgument() {
        return !argument.isEmpty();
    }

    /**
     * Informa se a linha digitada estava em branco.
     *
     * @return {@code true} se não há palavra de comando
     */
    public boolean isEmpty() {
        return commandWord.isEmpty();
    }
}
