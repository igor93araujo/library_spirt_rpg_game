package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Objects;

import br.edu.ifmg.libraryspirit.items.Bookmark;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.items.Key;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.items.Theme;

/**
 * Foto imutável de um item, com o que a interface precisa para desenhá-lo.
 *
 * @param name nome do item
 * @param description descrição do item
 * @param kind tipo do item
 * @param theme tema da página, ou {@code null} se o item não for uma página
 */
public record ItemView(String name, String description, Kind kind, Theme theme) {

    /** Tipo do item, usado para escolher o ícone. */
    public enum Kind { PAGE, BOOKMARK, KEY }

    public ItemView {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(description, "description");
        Objects.requireNonNull(kind, "kind");
    }

    /**
     * Cria a foto de um item do jogo.
     *
     * @param item item a fotografar
     * @return a foto do item
     * @throws IllegalArgumentException se o tipo do item não for conhecido
     */
    public static ItemView of(Item item) {
        if (item instanceof MagicPage page) {
            return new ItemView(item.getName(), item.getDescription(), Kind.PAGE, page.getTheme());
        }
        if (item instanceof Bookmark) {
            return new ItemView(item.getName(), item.getDescription(), Kind.BOOKMARK, null);
        }
        if (item instanceof Key) {
            return new ItemView(item.getName(), item.getDescription(), Kind.KEY, null);
        }
        throw new IllegalArgumentException("Tipo de item desconhecido: " + item.getClass());
    }
}
