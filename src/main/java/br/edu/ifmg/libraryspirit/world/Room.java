package br.edu.ifmg.libraryspirit.world;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Inventory;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.characters.Threat;

/**
 * Representa uma sala da biblioteca.
 * Guarda a descrição da sala, as saídas para as salas vizinhas e os itens
 * que estão nela. Cada saída é identificada por uma direção (ex.: "norte").
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.1
 */
public class Room {

    /** Texto que descreve a sala para o jogador. */
    private final String description;

    /**
     * Saídas da sala: direção → sala vizinha.
     * LinkedHashMap mantém a ordem em que as saídas foram adicionadas.
     */
    private final Map<String, Room> exits;

    /** Portas das saídas: direção → porta. Saídas sem porta ficam livres. */
    private final Map<String, Door> doors = new LinkedHashMap<>();

    /** Itens que estão na sala e podem ser pegos. */
    private final Inventory items;

    /** Ameaças da sala, na ordem em que aparecem. */
    private final List<Threat> threats = new ArrayList<>();

    /** Posição da ameaça atual na lista. */
    private int currentThreat = 0;

    /**
     * Cria uma sala sem saídas e sem itens.
     *
     * @param description texto que descreve a sala
     */
    public Room(String description) {
        this.description = description;
        this.exits = new LinkedHashMap<>();
        this.items = new Inventory();
    }

    /**
     * Retorna a descrição da sala.
     *
     * @return o texto que descreve a sala
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define uma saída da sala. Se já existir saída nessa direção, ela é substituída.
     *
     * @param direction direção da saída (ex.: "norte")
     * @param neighbor sala que fica nessa direção
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Retorna a sala vizinha na direção informada.
     *
     * @param direction direção desejada (ex.: "norte")
     * @return a sala vizinha, ou {@code null} se não houver saída nessa direção
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Monta o texto com as saídas disponíveis, na ordem em que foram adicionadas.
     *
     * @return texto no formato "Saídas: norte leste", ou "Saídas: nenhuma"
     */
    public String getExitsDescription() {
        if (exits.isEmpty()) {
            return "Saídas: nenhuma";
        }
        return "Saídas: " + String.join(" ", exits.keySet());
    }

    /**
     * Coloca um item na sala.
     *
     * @param item item a ser colocado
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Tira um item da sala (por exemplo, quando o jogador o pega).
     *
     * @param name nome do item
     * @return o item removido, ou {@code null} se ele não estiver na sala
     */
    public Item removeItem(String name) {
        return items.remove(name);
    }

    /**
     * Informa se a sala tem algum item.
     *
     * @return {@code true} se houver pelo menos um item
     */
    public boolean hasItems() {
        return !items.isEmpty();
    }

    /**
     * Monta o texto com os itens da sala.
     *
     * @return texto como "Itens: chave, vela", ou "Itens: nenhum"
     */
    public String getItemsDescription() {
        if (items.isEmpty()) {
            return "Itens: nenhum";
        }
        return "Itens: " + items.getNames();
    }

    /**
     * Coloca uma porta numa saída já existente.
     *
     * @param direction direção da saída
     * @param door porta da saída
     */
    public void setDoor(String direction, Door door) {
        doors.put(direction, door);
    }

    /**
     * Retorna a porta de uma direção.
     *
     * @param direction direção desejada
     * @return a porta, ou {@code null} se a saída não tiver porta
     */
    public Door getDoor(String direction) {
        return doors.get(direction);
    }

    /**
     * Procura uma porta trancada com enigma cujos itens o jogador já tem.
     *
     * @param player jogador
     * @return a porta, ou {@code null} se não houver
     */
    public Door findRiddleDoor(Player player) {
        for (Door door : doors.values()) {
            if (door.isLocked() && door.hasRiddle() && door.hasRequiredItems(player)) {
                return door;
            }
        }
        return null;
    }

    /**
     * Adiciona uma ameaça à sala. As ameaças aparecem na ordem em que foram adicionadas.
     *
     * @param threat ameaça a ser adicionada
     */
    public void addThreat(Threat threat) {
        threats.add(threat);
    }

    /**
     * Informa se ainda há uma ameaça esperando resposta do jogador.
     *
     * @return {@code true} se existe uma ameaça não enfrentada
     */
    public boolean hasActiveThreat() {
        return currentThreat < threats.size();
    }

    /**
     * Retorna a ameaça que está esperando resposta.
     *
     * @return a ameaça atual, ou {@code null} se não houver nenhuma
     */
    public Threat getThreat() {
        if (!hasActiveThreat()) {
            return null;
        }
        return threats.get(currentThreat);
    }

    /**
     * Marca a ameaça atual como enfrentada e passa para a próxima, se houver.
     */
    public void resolveThreat() {
        if (hasActiveThreat()) {
            currentThreat++;
        }
    }
}
