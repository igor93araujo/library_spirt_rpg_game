package br.edu.ifmg.libraryspirit.items;

/**
 * Temas das seções da biblioteca. Cada {@link MagicPage} pertence a um tema,
 * e o Grande Tomo exige a página certa em cada etapa do selamento.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public enum Theme {
    /** Seção de Terror. */
    HORROR("terror"),
    /** Seção de Aventura. */
    ADVENTURE("aventura"),
    /** Seção de Ficção Científica. */
    SCI_FI("ficcao");

    /** Nome do tema como o jogador digita (sem acentos). */
    private final String displayName;

    Theme(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retorna o nome do tema em português, como o jogador digita.
     *
     * @return o nome do tema (ex.: "terror")
     */
    public String getDisplayName() {
        return displayName;
    }
}
