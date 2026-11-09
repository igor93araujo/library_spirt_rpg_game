package br.edu.ifmg.libraryspirit.characters;

import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Representa o jogador: o espírito guardião da biblioteca.
 * Por enquanto guarda apenas a sala onde ele está.
 * Inventário, vidas e escudo serão adicionados nas próximas fases.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Player {

    /** Sala onde o jogador está agora. */
    private Room currentRoom;

    /**
     * Cria o jogador na sala inicial.
     *
     * @param startRoom sala onde o jogo começa
     */
    public Player(Room startRoom) {
        this.currentRoom = startRoom;
    }

    /**
     * Retorna a sala onde o jogador está.
     *
     * @return a sala atual
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Move o jogador para outra sala.
     *
     * @param room sala de destino
     */
    public void moveTo(Room room) {
        this.currentRoom = room;
    }
}
