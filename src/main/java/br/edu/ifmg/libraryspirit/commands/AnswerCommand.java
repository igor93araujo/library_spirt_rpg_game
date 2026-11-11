package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Door;

/**
 * Comando "responder": responde ao enigma de uma porta trancada da sala atual.
 * Ex.: "responder machado de assis".
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class AnswerCommand implements Command {

    /** Onde as mensagens são exibidas. */
    private final Output output;

    /** Jogador que responde. */
    private final Player player;

    /**
     * Cria o comando de responder.
     *
     * @param output onde as mensagens serão exibidas
     * @param player jogador que responde
     */
    public AnswerCommand(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    /**
     * Tenta abrir a porta com enigma da sala atual.
     *
     * @param argument resposta do jogador
     * @return sempre {@code false}
     */
    @Override
    public boolean execute(String argument) {
        if (argument.isEmpty()) {
            output.println("Responder o quê?");
            return false;
        }
        Door door = player.getCurrentRoom().findRiddleDoor(player);
        if (door == null) {
            output.println("Não há nenhum enigma para responder aqui.");
            return false;
        }
        if (door.answer(player, argument)) {
            output.println("Resposta certa! A porta se destranca.");
        } else {
            output.println("Resposta errada. A porta continua trancada.");
        }
        return false;
    }
}
