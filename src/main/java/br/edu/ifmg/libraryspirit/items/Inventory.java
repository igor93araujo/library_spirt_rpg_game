package br.edu.ifmg.libraryspirit.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda uma coleção de itens. É usado tanto pelo jogador (o que ele carrega)
 * quanto pelas salas (o que está no chão). Assim a lógica de guardar,
 * procurar e remover itens fica num lugar só, sem repetir código.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Inventory {

    /** Itens guardados, na ordem em que foram adicionados. */
    private final List<Item> items;

    /**
     * Cria um inventário vazio.
     */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adiciona um item.
     *
     * @param item item a ser guardado
     */
    public void add(Item item) {
        items.add(item);
    }

    /**
     * Procura um item pelo nome, sem diferenciar maiúsculas de minúsculas.
     *
     * @param name nome do item (ex.: "chave de bronze")
     * @return o item encontrado, ou {@code null} se não existir
     */
    public Item find(String name) {
        for (Item item : items) {
            if (item.hasName(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Remove um item pelo nome.
     *
     * @param name nome do item
     * @return o item removido, ou {@code null} se não existir
     */
    public Item remove(String name) {
        Item item = find(name);
        if (item != null) {
            items.remove(item);
        }
        return item;
    }

    /**
     * Informa se existe um item com o nome informado.
     *
     * @param name nome do item
     * @return {@code true} se o item estiver guardado
     */
    public boolean contains(String name) {
        return find(name) != null;
    }

    /**
     * Informa se o inventário está vazio.
     *
     * @return {@code true} se não houver nenhum item
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Monta a lista com os nomes dos itens, separados por vírgula.
     *
     * @return texto como "chave de bronze, vela", ou texto vazio se não houver itens
     */
    public String getNames() {
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(item.getName());
        }
        return String.join(", ", names);
    }
}
