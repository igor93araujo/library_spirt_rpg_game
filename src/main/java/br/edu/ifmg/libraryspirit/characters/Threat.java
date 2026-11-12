package br.edu.ifmg.libraryspirit.characters;

import java.util.List;

/**
 * Representa uma ameaça que surge numa sala. Guarda o texto que descreve
 * o perigo (com uma pista) e a ação que o jogador deve digitar para escapar.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Threat {

    /** Ações que o jogador pode usar contra uma ameaça. */
    public static final List<String> VALID_ACTIONS =
            List.of("desviar", "pular", "abaixar", "esconder");

    /** Texto exibido ao jogador quando a ameaça aparece. */
    private final String description;

    /** Ação que faz o jogador escapar sem dano. */
    private final String correctAction;

    /**
     * Cria uma ameaça.
     *
     * @param description texto que descreve o perigo
     * @param correctAction ação correta (desviar, pular, abaixar ou esconder)
     * @throws IllegalArgumentException se a ação não for uma das quatro válidas
     */
    public Threat(String description, String correctAction) {
        if (!VALID_ACTIONS.contains(correctAction)) {
            throw new IllegalArgumentException("Ação inválida: " + correctAction);
        }
        this.description = description;
        this.correctAction = correctAction;
    }

    /**
     * Retorna o texto que descreve a ameaça.
     *
     * @return a descrição da ameaça
     */
    public String getDescription() {
        return description;
    }

    /**
     * Informa se a ação escolhida é a correta.
     *
     * @param action ação digitada pelo jogador
     * @return {@code true} se a ação escapa da ameaça
     */
    public boolean isCorrect(String action) {
        return correctAction.equals(action);
    }
}