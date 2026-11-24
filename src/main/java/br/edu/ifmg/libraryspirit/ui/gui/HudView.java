package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.EnumSet;
import java.util.Set;

import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Faixa superior: corações (vidas), escudo e três slots de páginas (um por tema).
 */
public class HudView extends HBox {
    private static final String EMPTY = "#3a3050";

    private final HBox hearts = new HBox(4);
    private final HBox shieldBox = new HBox();
    private final HBox pages = new HBox(6);

    public HudView() {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(18);
        setPadding(new Insets(8, 12, 8, 12));
        setStyle("-fx-background-color: #231a33;");
        hearts.setAlignment(Pos.CENTER_LEFT);
        shieldBox.setAlignment(Pos.CENTER_LEFT);
        pages.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("O Espírito da Biblioteca");
        title.setStyle("-fx-text-fill: #ffd36b; -fx-font-size: 14px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().addAll(hearts, shieldBox, pages, spacer, title);
    }

    /** Redesenha vidas, escudo e páginas a partir da foto do estado. */
    public void update(GameSnapshot snap) {
        hearts.getChildren().clear();
        for (int i = 0; i < Player.INITIAL_LIVES; i++) {
            hearts.getChildren().add(heart(i < snap.lives()));
        }
        shieldBox.getChildren().setAll(shield(snap.shield()));

        Set<Theme> owned = EnumSet.noneOf(Theme.class);
        for (ItemView item : snap.inventory()) {
            if (item.kind() == ItemView.Kind.PAGE && item.theme() != null) {
                owned.add(item.theme());
            }
        }
        pages.getChildren().clear();
        for (Theme theme : Theme.values()) {
            pages.getChildren().add(pageSlot(theme, owned.contains(theme)));
        }
    }

    private static Shape heart(boolean full) {
        Color color = Color.web(full ? "#e0405a" : EMPTY);
        Circle left = new Circle(7, 7, 7);
        Circle right = new Circle(19, 7, 7);
        Polygon tip = new Polygon(0.5, 10, 25.5, 10, 13, 25);
        Shape h = Shape.union(Shape.union(left, right), tip);
        h.setFill(color);
        return h;
    }

    private static Shape shield(boolean active) {
        Polygon p = new Polygon(0, 0, 22, 0, 22, 14, 11, 26, 0, 14);
        p.setFill(Color.web(active ? "#4a86d8" : EMPTY));
        p.setStroke(Color.web(active ? "#bcd6ff" : "#554a6b"));
        return p;
    }

    private static Rectangle pageSlot(Theme theme, boolean filled) {
        Rectangle r = new Rectangle(22, 28);
        r.setArcWidth(5);
        r.setArcHeight(5);
        r.setFill(Color.web(filled ? color(theme) : EMPTY));
        r.setStroke(Color.web(filled ? "#f6ecd0" : "#554a6b"));
        return r;
    }

    private static String color(Theme theme) {
        return switch (theme) {
            case HORROR -> "#b04a6a";
            case ADVENTURE -> "#5fae5b";
            case SCI_FI -> "#4aa8d8";
        };
    }
}
