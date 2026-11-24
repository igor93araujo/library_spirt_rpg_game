package br.edu.ifmg.libraryspirit.game;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.commands.Command;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Controla a partida: mostra a introdução, lê os comandos do jogador e executa
 * cada um até o jogo terminar. O Game não sabe ler texto (isso é do
 * {@link Parser}), nem imprimir (isso é do {@link Output}), nem o que cada
 * comando faz (isso é de cada {@link Command}). Ele só coordena as partes.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Game {

    /**
     * Título do jogo exibido ao jogador.
     */
    public static final String TITLE = "O Espírito da Biblioteca";

    /**
     * Lê o que o jogador digita.
     */
    private final Parser parser;

    /**
     * Onde as mensagens são exibidas.
     */
    private final Output output;

    /**
     * Comandos válidos do jogo.
     */
    private final CommandWords commandWords;

    /**
     * O jogador da partida.
     */
    private final Player player;

    /**
     * Cria uma partida.
     *
     * @param parser leitor dos comandos digitados
     * @param output onde as mensagens serão exibidas
     * @param commandWords comandos válidos do jogo
     * @param player o jogador, já posicionado na sala inicial
     */
    public Game(Parser parser, Output output, CommandWords commandWords, Player player) {
        this.parser = parser;
        this.output = output;
        this.commandWords = commandWords;
        this.player = player;
    }

    /**
     * Executa a partida: mostra a introdução e repete o ciclo "mostrar prompt →
     * ler comando → executar" até um comando encerrar o jogo ou a entrada
     * terminar. Cada resposta fica entre linhas em branco para facilitar a
     * leitura.
     */
    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            output.print("> ");
            if (!parser.hasNextInput()) {
                output.println();
                break;
            }
            output.println();
            finished = processInput(parser.readInput());
            output.println();
        }

        output.println("Obrigado por jogar. Até a próxima!");
    }

    /**
     * Texto do enredo da introdução (sem o título e sem a sala inicial), com
     * linhas separadas por "\n".
     *
     * @return o texto da introdução
     */
    public static String welcomeText() {
        return String.join("\n",
                "Há cem anos, esta biblioteca guarda as histórias do mundo.",
                "Mas um feitiço antigo se quebrou e as histórias escaparam dos livros.",
                "Três páginas mágicas (Terror, Aventura e Ficção Científica) foram",
                "arrancadas do Grande Tomo, e a biblioteca começa a enlouquecer.",
                "",
                "Só você, o espírito guardião, pode recolhê-las e selar o Grande Tomo",
                "antes que as histórias se percam para sempre.",
                "Digite 'ajuda' se precisar.");
    }

    /**
     * Mostra a introdução do jogo e a sala inicial.
     */
    private void printWelcome() {
        Room room = player.getCurrentRoom();
        output.println();
        output.println();
        output.println("=== " + TITLE + " ===");
        for (String line : welcomeText().split("\n", -1)) {
            output.println(line);
        }
        output.println();
        output.println(room.getDescription());
        output.println(room.getExitsDescription());
        output.println();
    }

    /**
     * Executa o comando digitado pelo jogador.
     *
     * @param input comando e argumento lidos pelo {@link Parser}
     * @return {@code true} se o jogo deve terminar
     */
    public boolean processInput(ParsedInput input) {
        if (input.isEmpty()) {
            return false;
        }
        Command command = commandWords.get(input.getCommandWord());
        if (command == null) {
            output.println("Não entendi. Digite 'ajuda' para ver os comandos.");
            return false;
        }
        return command.execute(input.getArgument());
    }
}
