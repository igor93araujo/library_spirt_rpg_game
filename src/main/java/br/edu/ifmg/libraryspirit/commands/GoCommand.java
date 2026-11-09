package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Comando "ir": move o jogador para a sala vizinha na direção informada.
 * Ex.: "ir norte".
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class GoCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador que será movido. */
    private final Player player;

    /**
     * Cria o comando de movimento.
     *
     * @param output onde as mensagens serão exibidas
     * @param player jogador que será movido
     */
    public GoCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Move o jogador na direção informada, se houver saída.
     *
     * @param argument direção desejada (ex.: "norte")
     * @return sempre {@code false}, pois andar não termina o jogo
     */
    @Override
    public boolean execute(String argument) {
        if (argument.isEmpty()) {
            output.print("Ir para onde?");
            return false;
        }

        Room next = player.getCurrentRoom().getExit(argument);
        if (next == null) {
            output.print("Não há passagem nessa direção.");
            return false;
        }

        player.moveTo(next);
        output.print(next.getDescription());
        output.print(next.getExitsDescription());
        return false;
    }
}
