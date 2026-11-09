package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "inventario": mostra os itens que o jogador carrega.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class InventoryCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador cujo inventário será mostrado. */
    private final Player player;

    /**
     * Cria o comando de inventário.
     *
     * @param output onde o inventário será exibido
     * @param player jogador cujo inventário será mostrado
     */
    public InventoryCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Mostra o inventário do jogador. O argumento é ignorado.
     *
     * @param argument ignorado
     * @return sempre {@code false}, pois ver o inventário não termina o jogo
     */
    @Override
    public boolean execute(String argument) {
        output.println(player.getInventoryDescription());
        return false;
    }
}
