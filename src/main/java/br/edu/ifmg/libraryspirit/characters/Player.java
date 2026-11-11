package br.edu.ifmg.libraryspirit.characters;

import br.edu.ifmg.libraryspirit.items.Inventory;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Representa o jogador: o espírito guardião da biblioteca.
 * Guarda a sala onde ele está e os itens que ele carrega.
 * Também controla as vidas e o escudo.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.2
 */
public class Player {

    /** Sala onde o jogador está agora. */
    private Room currentRoom;

    /** Itens que o jogador carrega. */
    private final Inventory inventory;

    /** Vidas iniciais do jogador. */
    public static final int INITIAL_LIVES = 3;

    /** Vidas que o jogador tem agora. */
    private int lives;

    /** Indica se o escudo está ativo. */
    private boolean shield;

    /**
     * Cria o jogador na sala inicial, sem itens.
     *
     * @param startRoom sala onde o jogo começa
     */
    public Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.inventory = new Inventory();
        this.lives = INITIAL_LIVES;
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

    /**
     * Retorna as vidas atuais.
     *
     * @return número de vidas
     */
    public int getLives() {
        return lives;
    }

    /**
     * Informa se o jogador ainda tem vidas.
     *
     * @return {@code true} se as vidas forem maiores que zero
     */
    public boolean isAlive() {
        return lives > 0;
    }

    /**
     * Aplica 1 de dano. Se o escudo estiver ativo, ele absorve o dano e se desfaz.
     *
     * @return {@code true} se o jogador perdeu uma vida, {@code false} se o escudo absorveu
     */
    public boolean takeDamage() {
        if (shield) {
            shield = false;
            return false;
        }
        if (lives > 0) {
            lives--;
        }
        return true;
    }

    /**
     * Dá uma vida extra ao jogador.
     */
    public void addLife() {
        lives++;
    }

    /**
     * Restaura as vidas para o valor inicial.
     */
    public void restoreLives() {
        lives = INITIAL_LIVES;
    }

    /**
     * Ativa o escudo, que absorve o próximo dano.
     */
    public void giveShield() {
        shield = true;
    }

    /**
     * Informa se o escudo está ativo.
     *
     * @return {@code true} se o escudo está ativo
     */
    public boolean hasShield() {
        return shield;
    }

    /**
     * Monta o texto de status do jogador.
     *
     * @return texto com vidas, escudo e inventário
     */
    public String getStatusDescription() {
        return "Vidas: " + lives + "\n"
                + "Escudo: " + (shield ? "ativo" : "inativo") + "\n"
                + getInventoryDescription();
    }
}
