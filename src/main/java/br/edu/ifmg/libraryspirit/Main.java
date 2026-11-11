package br.edu.ifmg.libraryspirit;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.commands.GoCommand;
import br.edu.ifmg.libraryspirit.commands.HelpCommand;
import br.edu.ifmg.libraryspirit.commands.InventoryCommand;
import br.edu.ifmg.libraryspirit.commands.LookCommand;
import br.edu.ifmg.libraryspirit.commands.QuitCommand;
import br.edu.ifmg.libraryspirit.commands.StatusCommand;
import br.edu.ifmg.libraryspirit.commands.TakeCommand;
import br.edu.ifmg.libraryspirit.commands.UseCommand;
import br.edu.ifmg.libraryspirit.game.CommandWords;
import br.edu.ifmg.libraryspirit.game.Game;
import br.edu.ifmg.libraryspirit.game.Parser;
import br.edu.ifmg.libraryspirit.ui.ConsoleOutput;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.WorldBuilder;

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
        Player player = new Player(new WorldBuilder().build());

        CommandWords commandWords = new CommandWords();
        commandWords.register("ir", new GoCommand(output, player));
        commandWords.register("olhar", new LookCommand(output, player));
        commandWords.register("pegar", new TakeCommand(output, player));
        commandWords.register("inventario", new InventoryCommand(output, player));
        commandWords.register("usar", new UseCommand(output, player));
        commandWords.register("status", new StatusCommand(output, player));
        commandWords.register("ajuda", new HelpCommand(output, commandWords));
        commandWords.register("sair", new QuitCommand(output));

        new Game(new Parser(), output, commandWords, player).play();
    }
}
