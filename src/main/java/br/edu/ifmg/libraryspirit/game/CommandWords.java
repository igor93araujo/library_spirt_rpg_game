package br.edu.ifmg.libraryspirit.game;

import java.util.LinkedHashMap;
import java.util.Map;

import br.edu.ifmg.libraryspirit.commands.Command;

/**
 * Guarda os comandos válidos do jogo, ligando cada palavra em português
 * (ex.: "ajuda") ao objeto {@link Command} que a executa.
 * Graças a esta classe, o jogo não precisa de um {@code switch} gigante:
 * ele só procura a palavra digitada e chama {@code execute} (polimorfismo).
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class CommandWords {

    /** Palavra digitada → comando. Mantém a ordem de registro. */
    private final Map<String, Command> commands;

    /**
     * Cria a lista de comandos vazia.
     */
    public CommandWords() {
        this.commands = new LinkedHashMap<>();
    }

    /**
     * Registra um comando. Se a palavra já existir, o comando antigo é substituído.
     *
     * @param word palavra que o jogador digita (ex.: "ajuda")
     * @param command comando executado para essa palavra
     */
    public void register(String word, Command command) {
        commands.put(word, command);
    }

    /**
     * Retorna o comando ligado à palavra informada.
     *
     * @param word palavra digitada pelo jogador
     * @return o comando, ou {@code null} se a palavra não for um comando válido
     */
    public Command get(String word) {
        return commands.get(word);
    }

    /**
     * Informa se a palavra é um comando válido.
     *
     * @param word palavra digitada pelo jogador
     * @return {@code true} se existe comando registrado para a palavra
     */
    public boolean isCommand(String word) {
        return commands.containsKey(word);
    }

    /**
     * Monta a lista de todas as palavras de comando, na ordem de registro.
     *
     * @return as palavras separadas por espaço (ex.: "ajuda sair")
     */
    public String getAllWords() {
        return String.join(" ", commands.keySet());
    }
}
