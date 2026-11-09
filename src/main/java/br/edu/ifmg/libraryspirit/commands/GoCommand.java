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
     * Aceita a direção sozinha ("ir norte") ou com "para" ("ir para norte").
     *
     * @param argument direção desejada (ex.: "norte" ou "para norte")
     * @return sempre {@code false}, pois andar não termina o jogo
     */
    @Override
    public boolean execute(String argument) {
        String direction = removePara(argument);
        if (direction.isEmpty()) {
            output.println("Ir para onde?");
            return false;
        }

        Room next = player.getCurrentRoom().getExit(direction);
        if (next == null) {
            output.println("Não há passagem nessa direção.");
            return false;
        }

        player.moveTo(next);
        output.println(next.getDescription());
        output.println(next.getExitsDescription());
        return false;
    }

    /**
     * Remove a palavra "para" do começo do argumento, se existir.
     * Ex.: "para norte" → "norte"; "para" → ""; "norte" → "norte".
     *
     * @param argument texto digitado depois de "ir"
     * @return só a direção
     */
    private String removePara(String argument) {
        if (argument.equals("para")) {
            return "";
        }
        if (argument.startsWith("para ")) {
            return argument.substring("para ".length());
        }
        return argument;
    }
}
