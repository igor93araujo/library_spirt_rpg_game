package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "usar": usa um item do inventário. Ex.: "usar marcador de vida".
 * Chama {@code item.use(player)} (polimorfismo), sem {@code instanceof}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class UseCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador que vai usar o item. */
    private final Player player;

    /**
     * Cria o comando de usar.
     *
     * @param output onde as mensagens serão exibidas
     * @param player jogador que vai usar o item
     */
    public UseCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Usa o item informado e, se ele for consumível, tira do inventário.
     *
     * @param argument nome do item
     * @return sempre {@code false}
     */
    @Override
    public boolean execute(String argument) {
        if (argument.isEmpty()) {
            output.println("Usar o quê?");
            return false;
        }
        Item item = player.getItem(argument);
        if (item == null) {
            output.println("Você não tem nenhum(a) " + argument + ".");
            return false;
        }
        output.println(item.use(player));
        if (item.isConsumable()) {
            player.removeItem(item.getName());
        }
        return false;
    }
}
