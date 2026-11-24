package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Painel rolável com as mensagens do jogo, coloridas por tipo, que rola
 * automaticamente até a última linha.
 */
public class LogView extends ScrollPane {
    private final VBox lines = new VBox(2);

    public LogView() {
        setContent(lines);
        setFitToWidth(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setPrefHeight(220);
        lines.setStyle("-fx-background-color: #14101c; -fx-padding: 8;");
        setStyle("-fx-background: #14101c; -fx-background-color: #14101c;");
        lines.heightProperty().addListener((o, a, b) -> setVvalue(1.0));
    }

    /** Acrescenta uma linha ao fim do log. */
    public void append(LogEntry entry) {
        Text text = new Text(entry.text().isEmpty() ? " " : entry.text());
        text.setStyle("-fx-fill: " + color(entry.type()) + "; -fx-font-size: 14px;");
        TextFlow flow = new TextFlow(text);
        lines.getChildren().add(flow);
    }

    /** Apaga todas as linhas. */
    public void clear() {
        lines.getChildren().clear();
    }

    private static String color(LogEntry.Type type) {
        return switch (type) {
            case WARNING -> "#ffb347";
            case ERROR -> "#ff6b6b";
            case NARRATIVE -> "#e8e0f0";
        };
    }
}
