package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.function.Consumer;

import br.edu.ifmg.libraryspirit.items.Theme;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Ícones de itens desenhados em código: página (cor por tema), marcador e chave.
 * Também cria o botão clicável com dica (Tooltip) usado na cena e no inventário.
 */
public final class ItemIcon {

    private ItemIcon() {
    }

    /**
     * Cria um botão com o ícone do item e a descrição na dica.
     *
     * @param item item a mostrar
     * @param size lado do ícone em pixels
     * @param commandPrefix verbo do comando enviado ao clicar (ex.: {@code pegar})
     * @param commandSink recebe o texto completo (ex.: {@code pegar chave de bronze})
     * @return o botão
     */
    public static Button button(ItemView item, double size, String commandPrefix,
            Consumer<String> commandSink) {
        Button b = new Button();
        b.setGraphic(draw(item, size));
        b.setFocusTraversable(false);
        b.setStyle("-fx-background-color: rgba(20,14,30,0.65); -fx-background-radius: 10;"
                + " -fx-border-color: #ffd36b; -fx-border-radius: 10; -fx-cursor: hand;");
        Tooltip tip = new Tooltip(item.name() + "\n" + item.description());
        tip.setWrapText(true);
        tip.setMaxWidth(260);
        tip.setShowDelay(Duration.millis(200));
        b.setTooltip(tip);
        // O nome do item vai inteiro, com espaços: o Parser separa só a primeira palavra.
        b.setOnAction(e -> commandSink.accept(commandPrefix + " " + item.name()));
        return b;
    }

    /** Desenha o ícone do item num canvas quadrado. */
    public static Canvas draw(ItemView item, double size) {
        Canvas c = new Canvas(size, size);
        GraphicsContext g = c.getGraphicsContext2D();
        switch (item.kind()) {
            case PAGE -> page(g, size, themeColor(item.theme()));
            case BOOKMARK -> bookmark(g, size, item.name().toLowerCase().contains("escudo"));
            case KEY -> key(g, size, item.name().toLowerCase().contains("prata")
                    ? "#cfd8e3" : "#c07a35");
        }
        return c;
    }

    static String themeColor(Theme theme) {
        if (theme == null) {
            return "#f2e6c8";
        }
        return switch (theme) {
            case HORROR -> "#b04a6a";
            case ADVENTURE -> "#5fae5b";
            case SCI_FI -> "#4aa8d8";
        };
    }

    private static void page(GraphicsContext g, double s, String accent) {
        g.setFill(Color.web("#f6ecd0"));
        g.fillRoundRect(s * 0.2, s * 0.1, s * 0.6, s * 0.8, 6, 6);
        g.setStroke(Color.web(accent));
        g.setLineWidth(2);
        g.strokeRoundRect(s * 0.2, s * 0.1, s * 0.6, s * 0.8, 6, 6);
        g.setFill(Color.web(accent));
        g.fillOval(s * 0.38, s * 0.22, s * 0.24, s * 0.24);
        for (int i = 0; i < 3; i++) {
            g.fillRect(s * 0.30, s * 0.55 + i * s * 0.08, s * 0.40, 2);
        }
    }

    private static void bookmark(GraphicsContext g, double s, boolean shield) {
        g.setFill(Color.web(shield ? "#4a86d8" : "#4fbf6b"));
        g.fillPolygon(
                new double[] {s * 0.3, s * 0.7, s * 0.7, s * 0.5, s * 0.3},
                new double[] {s * 0.08, s * 0.08, s * 0.92, s * 0.72, s * 0.92}, 5);
        g.setFill(Color.web("#ffffff", 0.85));
        g.fillOval(s * 0.42, s * 0.2, s * 0.16, s * 0.16);
    }

    private static void key(GraphicsContext g, double s, String color) {
        g.setStroke(Color.web(color));
        g.setLineWidth(Math.max(3, s * 0.09));
        g.strokeOval(s * 0.12, s * 0.3, s * 0.3, s * 0.3);
        g.strokeLine(s * 0.42, s * 0.45, s * 0.88, s * 0.45);
        g.strokeLine(s * 0.72, s * 0.45, s * 0.72, s * 0.62);
        g.strokeLine(s * 0.86, s * 0.45, s * 0.86, s * 0.58);
    }
}
