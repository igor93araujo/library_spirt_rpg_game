package br.edu.ifmg.libraryspirit.ui.gui;

import br.edu.ifmg.libraryspirit.ui.Output;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link Output} que entrega cada mensagem, como {@link LogEntry} narrativa,
 * a um consumidor (o log da interface gráfica).
 */
public class GuiOutput implements Output {

    private final Consumer<LogEntry> sink;

    public GuiOutput(Consumer<LogEntry> sink) {
        this.sink = Objects.requireNonNull(sink, "sink");
    }

    @Override
    public void print(String text) {
        deliver(text);
    }

    @Override
    public void println(String message) {
        deliver(message);
    }

    private void deliver(String text) {
        sink.accept(new LogEntry(text == null ? "" : text, LogEntry.Type.NARRATIVE));
    }
}
