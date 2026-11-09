package br.edu.ifmg.libraryspirit;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.commands.GoCommand;
import br.edu.ifmg.libraryspirit.commands.HelpCommand;
import br.edu.ifmg.libraryspirit.commands.QuitCommand;
import br.edu.ifmg.libraryspirit.game.CommandWords;
import br.edu.ifmg.libraryspirit.game.Game;
import br.edu.ifmg.libraryspirit.game.Parser;
import br.edu.ifmg.libraryspirit.ui.ConsoleOutput;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Ponto de entrada do jogo O Espírito da Biblioteca.
 * Cria as peças do jogo, liga umas às outras e inicia a partida.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Main {

    /**
     * Monta o jogo e inicia a partida.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Output output = new ConsoleOutput();

        // Mapa temporário com duas salas. Será trocado pelo WorldBuilder (item 11).
        Room hall = new Room("Você está no Hall de Entrada. Estantes antigas cercam o salão.");
        Room horror = new Room("Você está na Seção de Terror. Uma névoa fria cobre o chão.");
        hall.setExit("oeste", horror);
        horror.setExit("leste", hall);

        Player player = new Player(hall);

        CommandWords commandWords = new CommandWords();
        commandWords.register("ir", new GoCommand(output, player));
        commandWords.register("ajuda", new HelpCommand(output, commandWords));
        commandWords.register("sair", new QuitCommand(output));

        new Game(new Parser(), output, commandWords, player).play();
    }
}
