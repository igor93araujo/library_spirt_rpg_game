package br.edu.ifmg.libraryspirit.world;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa uma sala da biblioteca.
 * Guarda a descrição da sala e as saídas para as salas vizinhas.
 * Cada saída é identificada por uma direção (ex.: "norte", "leste").
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Room {

    /** Texto que descreve a sala para o jogador. */
    private final String description;

    /**
     * Saídas da sala: direção → sala vizinha.
     * LinkedHashMap mantém a ordem em que as saídas foram adicionadas.
     */
    private final Map<String, Room> exits;

    /**
     * Cria uma sala sem saídas.
     *
     * @param description texto que descreve a sala
     */
    public Room(String description) {
        this.description = description;
        this.exits = new LinkedHashMap<>();
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
}
