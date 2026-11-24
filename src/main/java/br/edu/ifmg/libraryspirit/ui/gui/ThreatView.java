package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Objects;

import br.edu.ifmg.libraryspirit.characters.Threat;

/**
 * Visão imutável de uma ameaça ativa. Não expõe a ação correta.
 *
 * @param description texto que descreve o perigo
 */
public record ThreatView(String description) {

    public ThreatView {
        Objects.requireNonNull(description, "description");
    }

    /**
     * Cria a visão de uma ameaça.
     *
     * @param threat ameaça do modelo
     * @return a visão
     */
    public static ThreatView of(Threat threat) {
        return new ThreatView(threat.getDescription());
    }
}
