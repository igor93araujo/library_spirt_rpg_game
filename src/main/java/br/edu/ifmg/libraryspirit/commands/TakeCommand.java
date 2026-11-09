package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "pegar": tira um item da sala atual e guarda no inventário do jogador.
 * Ex.: "pegar chave de bronze".
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class TakeCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador que vai pegar o item. */
    private final Player player;

    /**
     * Cria o comando de pegar.
     *
     * @param output onde as mensagens serão exibidas
     * @param player jogador que vai pegar o item
     */
    public TakeCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Move o item informado da sala para o inventário do jogador.
     *
     * @param argument nome do item (ex.: "chave de bronze")
     * @return sempre {@code false}, pois pegar não termina o jogo
     */
    @Override
    public boolean execute(String argument) {
        if (argument.isEmpty()) {
            output.println("Pegar o quê?");
            return false;
        }

        Item item = player.getCurrentRoom().removeItem(argument);
        if (item == null) {
            output.println("Não há nenhum(a) " + argument + " aqui.");
            return false;
        }

        player.addItem(item);
        output.println("Você pegou: " + item.getName() + ".");
        output.println(item.getDescription());
        return false;
    }
}
