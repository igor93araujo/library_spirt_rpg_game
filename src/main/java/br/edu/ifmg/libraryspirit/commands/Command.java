package br.edu.ifmg.libraryspirit.commands;

/**
 * Representa um comando que o jogador pode digitar (ex.: "ajuda", "ir", "sair").
 * Cada comando do jogo é uma classe que implementa esta interface.
 * Para criar um comando novo, basta criar uma nova classe e registrá-la em
 * {@code CommandWords}, sem alterar o resto do jogo (princípio Aberto/Fechado).
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public interface Command {

    /**
     * Executa o comando.
     *
     * @param argument texto digitado depois da palavra de comando
     *                 (ex.: "norte" em "ir norte"), ou texto vazio
     * @return {@code true} se o jogo deve terminar depois deste comando
     */
    boolean execute(String argument);
}
