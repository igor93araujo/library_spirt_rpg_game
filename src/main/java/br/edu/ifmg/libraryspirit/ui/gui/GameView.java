package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

/**
 * Tela principal. Por enquanto: log e campo de comando embaixo. Centro (cena),
 * direita (inventário) e topo (HUD) ficam livres para as próximas tarefas.
 */
public class GameView extends BorderPane {
    private final GameController controller;
    private final LogView log = new LogView();
    private final TextField input = new TextField();
    private final RoomSceneView scene = new RoomSceneView(this::run);
    private final HudView hud = new HudView();
    private final MinimapView minimap = new MinimapView();
    private final InventoryView inventory = new InventoryView(this::run);
    private final javafx.scene.layout.StackPane center = new javafx.scene.layout.StackPane();
    private EndView endView;
    private final Button help = new Button("Ajuda");
    private final Button status = new Button("Status");

    public GameView() {
        this.controller = new GameController(log::append);
        setStyle("-fx-background-color: #1b1426;");

        input.setPromptText("Digite um comando (ex.: ir norte)");
        input.setOnAction(e -> {
            String line = input.getText();
            input.clear();
            run(line);
        });
        help.setOnAction(e -> run("ajuda"));
        status.setOnAction(e -> run("status"));
        HBox.setHgrow(input, Priority.ALWAYS);

        HBox controls = new HBox(8, input, help, status);
        VBox bottom = new VBox(6, log, controls);
        bottom.setPadding(new Insets(8));
        setBottom(bottom);

        center.getChildren().add(scene);
        setCenter(center);
        setRight(inventory);
        setTop(hud);
        setLeft(minimap);

        // Atalhos: setas (com o campo vazio) e WASD (fora do campo de texto).
        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getTarget() instanceof TextField && e.getTarget() != input) {
                return;
            }
            boolean typing = input.isFocused();
            if (controller.isFinished() || (typing && !input.getText().isEmpty())) {
                return;
            }
            if (scene.handleKey(e, !typing)) {
                e.consume();
            }
        });

        run("olhar");
        input.requestFocus();
    }

    /** Executa um comando e atualiza o estado dos controles. */
    public void run(String line) {
        java.util.List<GameEvent> events = controller.submit(line);
        GameSnapshot snap = controller.snapshot();
        scene.update(snap);
        scene.playEvents(events);
        inventory.update(snap.inventory(), snap.boss());
        hud.update(snap);
        minimap.update(snap.scene(), controller.visited());
        boolean over = controller.isFinished();
        input.setDisable(over);
        help.setDisable(over);
        status.setDisable(over);
        if (over && endView == null) {
            endView = new EndView(controller.isVictory(), this::restart,
                    javafx.application.Platform::exit);
            center.getChildren().add(endView);
        }
    }

    /** @return controlador do jogo desta tela */
    public GameController controller() {
        return controller;
    }

    /** Recomeça o jogo e limpa o log. */
    public void restart() {
        if (endView != null) {
            center.getChildren().remove(endView);
            endView = null;
        }
        controller.restart();
        log.clear();
        run("olhar");
    }
}
