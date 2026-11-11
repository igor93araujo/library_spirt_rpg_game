package br.edu.ifmg.libraryspirit.items;

import br.edu.ifmg.libraryspirit.characters.Player;

/**
 * Marcador de livro mágico. Pode ser de vida (+1 vida) ou de escudo
 * (absorve o próximo dano). Some do inventário depois de usado.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Bookmark extends Item {

    /** Tipos de marcador. */
    public enum Type {
        /** Dá +1 vida. */
        LIFE,
        /** Absorve o próximo dano. */
        SHIELD
    }

    /** Tipo deste marcador. */
    private final Type type;

    /**
     * Cria um marcador.
     *
     * @param type tipo do marcador
     */
    public Bookmark(Type type) {
        super(type == Type.LIFE ? "marcador de vida" : "marcador de escudo",
                type == Type.LIFE
                        ? "Um marcador vermelho que pulsa como um coração."
                        : "Um marcador azul que reluz como um escudo.");
        this.type = type;
    }

    /**
     * Retorna o tipo do marcador.
     *
     * @return o tipo
     */
    public Type getType() {
        return type;
    }

    /**
     * Aplica o efeito do marcador no jogador.
     *
     * @param player jogador que usa o marcador
     * @return mensagem para o jogador
     */
    @Override
    public String use(Player player) {
        if (type == Type.LIFE) {
            player.addLife();
            return "Você ganhou uma vida! Vidas: " + player.getLives() + ".";
        }
        player.giveShield();
        return "Um escudo mágico o envolve e vai absorver o próximo dano.";
    }

    /**
     * Marcadores são consumidos ao serem usados.
     *
     * @return sempre {@code true}
     */
    @Override
    public boolean isConsumable() {
        return true;
    }
}
