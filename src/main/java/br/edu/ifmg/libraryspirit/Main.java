package br.edu.ifmg.libraryspirit;

import br.edu.ifmg.libraryspirit.game.Game;
import br.edu.ifmg.libraryspirit.game.GameSetup;
import br.edu.ifmg.libraryspirit.game.Parser;
import br.edu.ifmg.libraryspirit.ui.ConsoleOutput;
import br.edu.ifmg.libraryspirit.ui.Output;

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
        GameSetup setup = GameSetup.create(output);
        new Game(new Parser(), output, setup.getCommandWords(), setup.getPlayer()).play();
    }
}
