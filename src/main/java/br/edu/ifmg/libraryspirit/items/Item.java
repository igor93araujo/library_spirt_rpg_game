package br.edu.ifmg.libraryspirit.items;

import br.edu.ifmg.libraryspirit.characters.Player;

/**
 * Representa um item que pode ser encontrado nas salas e guardado no inventário.
 * É uma classe abstrata: não existe um "item genérico" no jogo, só tipos
 * específicos (páginas mágicas, marcadores, chaves), que herdam desta classe
 * e definem o que acontece quando são usados.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public abstract class Item {

    /** Nome do item, usado nos comandos (ex.: "chave de bronze"). */
    private final String name;

    /** Texto que descreve o item para o jogador. */
    private final String description;

    /**
     * Cria um item. Só pode ser chamado pelas classes filhas.
     *
     * @param name nome do item (ex.: "chave de bronze")
     * @param description texto que descreve o item
     */
    protected Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Retorna o nome do item.
     *
     * @return o nome do item
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna a descrição do item.
     *
     * @return o texto que descreve o item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Informa se este item tem o nome informado, sem diferenciar maiúsculas
     * de minúsculas. Ex.: "Chave de Bronze" e "chave de bronze" são iguais.
     *
     * @param otherName nome a comparar
     * @return {@code true} se os nomes forem iguais
     */
    public boolean hasName(String otherName) {
        return name.equalsIgnoreCase(otherName);
    }

    /**
     * Usa o item. Cada tipo de item define o próprio efeito
     * (ex.: um marcador de vida dá +1 vida ao jogador).
     *
     * @param player jogador que está usando o item
     * @return mensagem que descreve o que aconteceu, para mostrar ao jogador
     */
    public abstract String use(Player player);
}
