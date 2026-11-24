package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Objects;

/**
 * Uma linha do log da interface: texto e tipo (usado para escolher a cor).
 *
 * @param text texto da linha (pode ser vazio, para linha em branco)
 * @param type tipo da mensagem
 */
public record LogEntry(String text, Type type) {

    /** Tipo da mensagem, usado só para apresentação. */
    public enum Type { NARRATIVE, WARNING, ERROR }

    public LogEntry {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(type, "type");
    }
}
