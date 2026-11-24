package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Inventário lateral. Cada ícone é clicável e envia {@code usar <item>}.
 */
public class InventoryView extends VBox {

    private final FlowPane grid = new FlowPane(8, 8);
    private final Consumer<String> commandSink;

    /**
     * @param commandSink recebe o texto do comando (ex.: {@code usar chave de bronze})
     */
    public InventoryView(Consumer<String> commandSink) {
        this.commandSink = commandSink;
        setPadding(new Insets(10));
        setSpacing(8);
        setPrefWidth(150);
        setMinWidth(150);
        setStyle("-fx-background-color: #231a33;");
        Label title = new Label("Inventário");
        title.setStyle("-fx-text-fill: #ffd36b; -fx-font-size: 14px; -fx-font-weight: bold;");
        grid.setPrefWrapLength(130);
        getChildren().addAll(title, grid);
    }

    /** Redesenha os ícones a partir da lista de itens do jogador. */
    public void update(List<ItemView> items) {
        update(items, null);
    }

    /**
     * Redesenha os ícones; se {@code boss} não for nulo, destaca a página do tema da etapa atual.
     */
    public void update(List<ItemView> items, BossView boss) {
        grid.getChildren().clear();
        if (items.isEmpty()) {
            Label empty = new Label("(vazio)");
            empty.setStyle("-fx-text-fill: #8a7fa0;");
            grid.getChildren().add(empty);
            return;
        }
        for (ItemView item : items) {
            javafx.scene.control.Button b = ItemIcon.button(item, 44, "usar", commandSink);
            if (boss != null && boss.stageTheme() != null && item.kind() == ItemView.Kind.PAGE
                    && item.theme() == boss.stageTheme()) {
                b.setStyle("-fx-background-color: rgba(255,211,107,0.35); -fx-background-radius: 10;"
                        + " -fx-border-color: #ffd36b; -fx-border-width: 3; -fx-border-radius: 10;"
                        + " -fx-cursor: hand;");
                b.setEffect(new javafx.scene.effect.DropShadow(14, javafx.scene.paint.Color.GOLD));
            }
            grid.getChildren().add(b);
        }
    }
}
