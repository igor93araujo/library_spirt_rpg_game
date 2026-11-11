package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "status": mostra vidas, escudo e inventário do jogador.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class StatusCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador cujo status será mostrado. */
    private final Player player;

    /**
     * Cria o comando de status.
     *
     * @param output onde o status será exibido
     * @param player jogador cujo status será mostrado
     */
    public StatusCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Mostra o status do jogador. O argumento é ignorado.
     *
     * @param argument ignorado
     * @return sempre {@code false}
     */
    @Override
    public boolean execute(String argument) {
        output.println(player.getStatusDescription());
        return false;
    }
}
