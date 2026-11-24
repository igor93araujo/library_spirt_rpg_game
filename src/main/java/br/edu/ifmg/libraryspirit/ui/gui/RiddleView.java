package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Objects;

/**
 * Visão imutável de um enigma pendente numa porta trancada.
 *
 * @param direction direção da porta
 * @param question pergunta do enigma
 */
public record RiddleView(String direction, String question) {

    public RiddleView {
        Objects.requireNonNull(direction, "direction");
        Objects.requireNonNull(question, "question");
    }
}
