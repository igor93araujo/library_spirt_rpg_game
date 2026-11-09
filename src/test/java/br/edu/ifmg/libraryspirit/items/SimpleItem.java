package br.edu.ifmg.libraryspirit.items;

import br.edu.ifmg.libraryspirit.characters.Player;

/**
 * Item simples usado só nos testes, já que {@link Item} é abstrata.
 */
public class SimpleItem extends Item {

    /**
     * Cria um item de teste.
     *
     * @param name nome do item
     */
    public SimpleItem(String name) {
        super(name, "Um item de teste.");
    }

    @Override
    public String use(Player player) {
        return "Você usou " + getName() + ".";
    }
}
