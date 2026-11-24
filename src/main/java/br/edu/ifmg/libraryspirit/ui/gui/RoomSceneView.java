package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Map;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Cena da sala: um {@link Canvas} com a arte e setas clicáveis para as saídas.
 * Saídas trancadas mostram um cadeado. Cada clique vira o comando {@code ir <direção>}.
 */
public class RoomSceneView extends StackPane {

    private static final Map<String, String> ARROWS = Map.of(
            "norte", "▲", "sul", "▼", "leste", "►", "oeste", "◄");

    private final Canvas canvas = new Canvas();
    private final Consumer<String> commandSink;
    private final Map<String, Button> buttons;
    private final javafx.scene.layout.HBox itemBox = new javafx.scene.layout.HBox(12);
    private final javafx.scene.layout.HBox reactBox = new javafx.scene.layout.HBox(8);
    private final Region flash = new Region();
    private final RiddlePane riddlePane;
    private GameSnapshot current;
    private long sealStart = Long.MIN_VALUE;
    private long resetStart = Long.MIN_VALUE;
    private final javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
        @Override
        public void handle(long now) {
            redraw();
        }
    };

    /**
     * @param commandSink recebe o texto do comando (ex.: {@code ir norte})
     */
    public RoomSceneView(Consumer<String> commandSink) {
        this.commandSink = commandSink;
        this.riddlePane = new RiddlePane(commandSink);
        setMinSize(200, 150);
        setPrefSize(700, 420);
        setStyle("-fx-background-color: #15101d;");

        // O canvas acompanha o tamanho do painel sem forçar um tamanho mínimo.
        Region holder = new Region();
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        canvas.widthProperty().addListener(o -> redraw());
        canvas.heightProperty().addListener(o -> redraw());
        canvas.setManaged(false);
        getChildren().addAll(holder, canvas);

        buttons = Map.of(
                "norte", arrow("norte", Pos.TOP_CENTER),
                "sul", arrow("sul", Pos.BOTTOM_CENTER),
                "leste", arrow("leste", Pos.CENTER_RIGHT),
                "oeste", arrow("oeste", Pos.CENTER_LEFT));

        itemBox.setAlignment(Pos.CENTER);
        itemBox.setPickOnBounds(false);
        itemBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(itemBox, Pos.BOTTOM_CENTER);
        StackPane.setMargin(itemBox, new Insets(0, 0, 70, 0));
        getChildren().add(itemBox);

        flash.setMouseTransparent(true);
        flash.setOpacity(0);
        flash.setManaged(false);
        flash.prefWidthProperty().bind(widthProperty());
        flash.prefHeightProperty().bind(heightProperty());
        getChildren().add(flash);
        reactBox.setAlignment(Pos.CENTER);
        reactBox.setPickOnBounds(false);
        reactBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        reactBox.setVisible(false);
        for (String action : br.edu.ifmg.libraryspirit.characters.Threat.VALID_ACTIONS) {
            Button b = new Button(action.substring(0, 1).toUpperCase() + action.substring(1));
            b.setFocusTraversable(false);
            b.setStyle("-fx-background-color: rgba(120,30,30,0.9); -fx-text-fill: white;"
                    + " -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
            b.setOnAction(e -> commandSink.accept(action));
            reactBox.getChildren().add(b);
        }
        StackPane.setAlignment(reactBox, Pos.TOP_CENTER);
        StackPane.setMargin(reactBox, new Insets(56, 0, 0, 0));
        getChildren().add(reactBox);
        StackPane.setAlignment(riddlePane, Pos.CENTER);
        getChildren().add(riddlePane);
        setFocusTraversable(true);
        canvas.setOnMouseClicked(e -> requestFocus());
    }

    private Button arrow(String direction, Pos pos) {
        Button b = new Button(ARROWS.get(direction));
        b.setFocusTraversable(false);
        b.setStyle(style(false));
        b.setVisible(false);
        b.setOnAction(e -> commandSink.accept("ir " + direction));
        StackPane.setAlignment(b, pos);
        StackPane.setMargin(b, new Insets(12));
        getChildren().add(b);
        return b;
    }

    private static String style(boolean locked) {
        return "-fx-background-color: " + (locked ? "rgba(60,30,30,0.80)" : "rgba(20,14,30,0.75)")
                + "; -fx-text-fill: #ffd36b; -fx-font-size: 18px; -fx-background-radius: 20;"
                + " -fx-cursor: hand;";
    }

    /** Atualiza a cena (arte e setas) a partir do estado atual. */
    public void update(GameSnapshot snapshot) {
        boolean sceneChanged = current != null && current.scene() != snapshot.scene();
        this.current = snapshot;
        if (sceneChanged) {
            fadeIn();
        }
        for (String dir : GameSnapshot.DIRECTIONS) {
            Button b = buttons.get(dir);
            boolean exit = snapshot.exits().contains(dir);
            boolean locked = snapshot.lockedExits().contains(dir);
            b.setVisible(exit);
            b.setText(locked ? ARROWS.get(dir) + " 🔒" : ARROWS.get(dir));
            b.setStyle(style(locked));
            b.setTooltip(new javafx.scene.control.Tooltip(
                    "Ir para " + dir + (locked ? " (trancada)" : "")));
        }
        itemBox.getChildren().clear();
        for (ItemView item : snapshot.roomItems()) {
            itemBox.getChildren().add(ItemIcon.button(item, 52, "pegar", commandSink));
        }
        riddlePane.update(snapshot.riddle());
        boolean threat = snapshot.threat() != null;
        reactBox.setVisible(threat);
        if (threat || snapshot.boss() != null) {
            timer.start();
        } else {
            timer.stop();
        }
        redraw();
    }

    /** Mostra efeitos visuais para os eventos de um comando. */
    public void playEvents(java.util.List<GameEvent> events) {
        for (GameEvent ev : events) {
            switch (ev) {
                case DAMAGE -> pulse("rgba(220,30,30,0.55)");
                case SHIELD_ABSORBED -> pulse("rgba(70,150,255,0.55)");
                case THREAT_CLEARED -> pulse("rgba(80,220,120,0.45)");
                case STAGE_SEALED -> {
                    sealStart = System.nanoTime();
                    pulse("rgba(255,211,107,0.55)");
                    timer.start();
                }
                case BOSS_RESET -> {
                    resetStart = System.nanoTime();
                    pulse("rgba(220,30,30,0.55)");
                    timer.start();
                }
                default -> { }
            }
        }
    }

    private void pulse(String color) {
        flash.setStyle("-fx-background-color: " + color + ";");
        flash.setOpacity(1);
        javafx.animation.FadeTransition ft =
                new javafx.animation.FadeTransition(javafx.util.Duration.millis(450), flash);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    /** Efeito de fade ao trocar de sala. */
    private void fadeIn() {
        javafx.animation.FadeTransition ft =
                new javafx.animation.FadeTransition(javafx.util.Duration.millis(350), canvas);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    private void redraw() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, w, h);
        SceneId sceneId = current == null ? null : current.scene();
        java.util.Optional<javafx.scene.image.Image> bg = ImageLoader.scene(sceneId);
        if (bg.isPresent()) {
            g.drawImage(bg.get(), 0, 0, w, h);
        } else {
            Art.drawScene(g, sceneId, w, h);
        }
        long now = System.nanoTime();
        double t = now / 1e9;
        BossView boss = current == null ? null : current.boss();
        if (boss != null) {
            double sealAnim = progress(now, sealStart, 0.9);
            double shake = 0;
            double r = progress(now, resetStart, 0.6);
            if (r < 1) {
                shake = Math.sin(r * 40) * 10 * (1 - r);
            }
            BossArt.drawTome(g, boss, w / 2, h * 0.28, Math.min(w, h) * 0.5, t, sealAnim, shake);
            BossArt.drawSealBar(g, boss, 16, 16, t, sealAnim);
        }
        if (current != null && current.threat() != null) {
            ThreatArt.draw(g, ThreatArt.kindOf(current.threat().description()),
                    w / 2, h * (boss != null ? 0.68 : 0.5),
                    Math.min(w, h) * (boss != null ? 0.32 : 0.45), t);
        }
    }

    private static double progress(long now, long start, double seconds) {
        if (start == Long.MIN_VALUE) {
            return 1;
        }
        return Math.min(1, (now - start) / 1e9 / seconds);
    }

    /**
     * Traduz uma tecla em movimento. Setas sempre valem; W/A/S/D só se
     * {@code allowLetters} for verdadeiro (quando o jogador não está digitando).
     *
     * @return {@code true} se a tecla foi tratada
     */
    public boolean handleKey(KeyEvent event, boolean allowLetters) {
        KeyCode code = event.getCode();
        String dir = switch (code) {
            case UP -> "norte";
            case DOWN -> "sul";
            case RIGHT -> "leste";
            case LEFT -> "oeste";
            case W -> allowLetters ? "norte" : null;
            case S -> allowLetters ? "sul" : null;
            case D -> allowLetters ? "leste" : null;
            case A -> allowLetters ? "oeste" : null;
            default -> null;
        };
        if (dir == null) {
            return false;
        }
        commandSink.accept("ir " + dir);
        return true;
    }
}
