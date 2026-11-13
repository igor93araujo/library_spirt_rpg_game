package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Door;
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

        if (player.getCurrentRoom().hasActiveThreat()) {
            output.println("Algo ainda ameaça você! Reaja antes de sair: desviar, pular, abaixar ou esconder.");
            return false;
        }

        Room next = player.getCurrentRoom().getExit(direction);
        if (next == null) {
            output.println("Não há passagem nessa direção.");
            return false;
        }

        Door door = player.getCurrentRoom().getDoor(direction);
        if (door != null && door.isLocked() && !tryOpen(door)) {
            return false;
        }
        player.moveTo(next);
        output.println(next.getDescription());
        output.println(next.getExitsDescription());

        if (next.hasActiveThreat()) {
            output.println();
            output.println(next.getThreat().getDescription());
            output.println("Reaja: desviar, pular, abaixar ou esconder.");
        }

        return false;
    }

    /**
     * Tenta abrir uma porta trancada e avisa o jogador do resultado.
     *
     * @param door porta trancada
     * @return {@code true} se a porta foi aberta
     */
    private boolean tryOpen(Door door) {
        if (door.unlock(player)) {
            output.println("Você destranca a porta.");
            return true;
        }
        if (door.hasRiddle() && door.hasRequiredItems(player)) {
            output.println("A porta está trancada. Uma voz sussurra um enigma:");
            output.println(door.getRiddle().getQuestion());
            output.println("Use 'responder <resposta>'.");
        } else {
            output.println("A porta está trancada.");
        }
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
