package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Comando "olhar": descreve de novo a sala onde o jogador está e suas saídas.
 * Útil quando a descrição já sumiu da tela.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class LookCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador cuja sala será descrita. */
    private final Player player;

    /**
     * Cria o comando de olhar.
     *
     * @param output onde a descrição será exibida
     * @param player jogador cuja sala será descrita
     */
    public LookCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Mostra a descrição, os itens (se houver) e as saídas da sala atual.
     * O argumento é ignorado.
     *
     * @param argument ignorado
     * @return sempre {@code false}, pois olhar não termina o jogo
     */
    @Override
    public boolean execute(String argument) {
        Room room = player.getCurrentRoom();
        output.println(room.getDescription());
        if (room.hasItems()) {
            output.println(room.getItemsDescription());
        }
        output.println(room.getExitsDescription());
        return false;
    }
}
