package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Set;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Minimapa em cruz (posições de {@link MapLayout}). A sala atual fica destacada
 * e as salas visitadas ficam acesas; as demais aparecem apagadas.
 */
public class MinimapView extends VBox {
    private static final double CELL = 40;
    private static final double GAP = 6;
    private static final double SIDE = MapLayout.COLUMNS * CELL + (MapLayout.COLUMNS - 1) * GAP;

    private final Canvas canvas = new Canvas(SIDE, SIDE);

    public MinimapView() {
        setPadding(new Insets(10));
        setSpacing(8);
        setPrefWidth(150);
        setMinWidth(150);
        setStyle("-fx-background-color: #231a33;");
        Label title = new Label("Minimapa");
        title.setStyle("-fx-text-fill: #ffd36b; -fx-font-size: 14px; -fx-font-weight: bold;");
        getChildren().addAll(title, canvas);
    }

    /**
     * Redesenha o mapa.
     *
     * @param current cena atual (pode ser {@code null})
     * @param visited cenas já visitadas
     */
    public void update(SceneId current, Set<SceneId> visited) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, SIDE, SIDE);
        for (SceneId scene : SceneId.values()) {
            MapLayout.Cell c = MapLayout.positionOf(scene);
            double x = c.column() * (CELL + GAP);
            double y = c.row() * (CELL + GAP);
            boolean here = scene == current;
            boolean seen = visited.contains(scene);
            g.setFill(Color.web(here ? "#ffd36b" : seen ? "#6b5a94" : "#2c2340"));
            g.fillRoundRect(x, y, CELL, CELL, 8, 8);
            g.setStroke(Color.web(here ? "#fff3c4" : "#554a6b"));
            g.setLineWidth(here ? 3 : 1);
            g.strokeRoundRect(x, y, CELL, CELL, 8, 8);
            g.setFill(Color.web(here ? "#3a2a10" : "#d8cfee"));
            g.fillText(label(scene), x + CELL / 2 - 4, y + CELL / 2 + 4);
        }
    }

    private static String label(SceneId scene) {
        return switch (scene) {
            case HALL -> "H";
            case HORROR -> "T";
            case ADVENTURE -> "A";
            case SCI_FI -> "F";
            case READING_ROOM -> "L";
        };
    }
}
