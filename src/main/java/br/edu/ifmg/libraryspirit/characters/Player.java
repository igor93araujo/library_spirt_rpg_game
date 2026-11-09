package br.edu.ifmg.libraryspirit.characters;

import br.edu.ifmg.libraryspirit.items.Inventory;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Representa o jogador: o espírito guardião da biblioteca.
 * Guarda a sala onde ele está e os itens que ele carrega.
 * Vidas e escudo serão adicionados nas próximas fases.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.1
 */
public class Player {

    /** Sala onde o jogador está agora. */
    private Room currentRoom;

    /** Itens que o jogador carrega. */
    private final Inventory inventory;

    /**
     * Cria o jogador na sala inicial, sem itens.
     *
     * @param startRoom sala onde o jogo começa
     */
    public Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.inventory = new Inventory();
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

    /**
     * Guarda um item no inventário do jogador.
     *
     * @param item item a ser guardado
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Tira um item do inventário do jogador.
     *
     * @param name nome do item
     * @return o item removido, ou {@code null} se o jogador não o tiver
     */
    public Item removeItem(String name) {
        return inventory.remove(name);
    }

    /**
     * Procura um item no inventário do jogador.
     *
     * @param name nome do item
     * @return o item, ou {@code null} se o jogador não o tiver
     */
    public Item getItem(String name) {
        return inventory.find(name);
    }

    /**
     * Informa se o jogador carrega o item.
     *
     * @param name nome do item
     * @return {@code true} se o item estiver no inventário
     */
    public boolean hasItem(String name) {
        return inventory.contains(name);
    }

    /**
     * Monta o texto que lista o inventário do jogador.
     *
     * @return texto como "Você carrega: chave, vela", ou aviso de inventário vazio
     */
    public String getInventoryDescription() {
        if (inventory.isEmpty()) {
            return "Você não carrega nada.";
        }
        return "Você carrega: " + inventory.getNames();
    }
}
