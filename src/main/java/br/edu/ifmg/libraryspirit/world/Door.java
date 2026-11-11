package br.edu.ifmg.libraryspirit.world;

import java.util.List;

import br.edu.ifmg.libraryspirit.characters.Player;

/**
 * Porta entre duas salas. Pode começar trancada e exigir itens no inventário
 * (chaves, páginas) e/ou a resposta de um enigma. A mesma porta é usada nos
 * dois lados da passagem.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Door {

    /** Indica se a porta está trancada. */
    private boolean locked;

    /** Nomes dos itens que o jogador precisa carregar. */
    private final List<String> requiredItems;

    /** Enigma a resolver, ou {@code null} se não houver. */
    private final Riddle riddle;

    /**
     * Cria uma porta trancada.
     *
     * @param requiredItems nomes dos itens exigidos (pode ser vazio)
     * @param riddle enigma exigido, ou {@code null}
     */
    public Door(List<String> requiredItems, Riddle riddle) {
        this.locked = true;
        this.requiredItems = List.copyOf(requiredItems);
        this.riddle = riddle;
    }

    /**
     * Cria uma porta trancada que só exige um item.
     *
     * @param requiredItem nome do item exigido (ex.: "chave de bronze")
     */
    public Door(String requiredItem) {
        this(List.of(requiredItem), null);
    }

    /**
     * Informa se a porta está trancada.
     *
     * @return {@code true} se estiver trancada
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Informa se a porta tem enigma.
     *
     * @return {@code true} se houver enigma
     */
    public boolean hasRiddle() {
        return riddle != null;
    }

    /**
     * Retorna o enigma da porta.
     *
     * @return o enigma, ou {@code null}
     */
    public Riddle getRiddle() {
        return riddle;
    }

    /**
     * Informa se o jogador carrega todos os itens exigidos.
     *
     * @param player jogador
     * @return {@code true} se tiver todos
     */
    public boolean hasRequiredItems(Player player) {
        for (String name : requiredItems) {
            if (!player.hasItem(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tenta destrancar a porta sem enigma: precisa de todos os itens e de
     * a porta não ter enigma pendente.
     *
     * @param player jogador que tenta abrir
     * @return {@code true} se a porta está destrancada depois da tentativa
     */
    public boolean unlock(Player player) {
        if (!locked) {
            return true;
        }
        if (riddle == null && hasRequiredItems(player)) {
            locked = false;
        }
        return !locked;
    }

    /**
     * Tenta destrancar a porta respondendo ao enigma. Exige também os itens.
     *
     * @param player jogador que responde
     * @param answer resposta digitada
     * @return {@code true} se a porta foi destrancada
     */
    public boolean answer(Player player, String answer) {
        if (locked && riddle != null && hasRequiredItems(player) && riddle.isCorrect(answer)) {
            locked = false;
        }
        return !locked;
    }
}
