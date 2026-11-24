package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Tela de vitória ou de fim de jogo, com "Jogar novamente" e "Sair".
 */
public class EndView extends VBox {

    /**
     * @param victory   {@code true} para vitória, {@code false} para fim de jogo
     * @param onRestart ação de "Jogar novamente"
     * @param onExit    ação de "Sair"
     */
    public EndView(boolean victory, Runnable onRestart, Runnable onExit) {
        super(20);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(40));
        setStyle("-fx-background-color: rgba(27,20,38,0.92);");

        Label title = new Label(victory ? "Vitória!" : "Fim de jogo");
        title.setStyle("-fx-text-fill: " + (victory ? "#f2d98d" : "#e07a7a")
                + "; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label text = new Label(victory
                ? "O Grande Tomo foi selado e a biblioteca voltou à paz."
                : "A aventura terminou. Tente novamente!");
        text.setWrapText(true);
        text.setMaxWidth(600);
        text.setStyle("-fx-text-fill: #e8e0f0; -fx-font-size: 16px;");

        Button again = new Button("Jogar novamente");
        again.setDefaultButton(true);
        again.setOnAction(e -> onRestart.run());
        Button exit = new Button("Sair");
        exit.setOnAction(e -> onExit.run());

        HBox buttons = new HBox(12, again, exit);
        buttons.setAlignment(Pos.CENTER);
        getChildren().addAll(title, text, buttons);
    }
}
