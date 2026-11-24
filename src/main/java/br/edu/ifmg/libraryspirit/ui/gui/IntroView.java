package br.edu.ifmg.libraryspirit.ui.gui;

import br.edu.ifmg.libraryspirit.game.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Tela de introdução: título, texto de abertura e botão "Começar".
 */
public class IntroView extends VBox {

    /**
     * @param onStart ação executada ao clicar em "Começar"
     */
    public IntroView(Runnable onStart) {
        super(24);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(40));
        setStyle("-fx-background-color: #1b1426;");

        Label title = new Label(Game.TITLE);
        title.setStyle("-fx-text-fill: #f2d98d; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label text = new Label(Game.welcomeText());
        text.setWrapText(true);
        text.setMaxWidth(760);
        text.setStyle("-fx-text-fill: #e8e0f0; -fx-font-size: 16px;");

        Button start = new Button("Começar");
        start.setDefaultButton(true);
        start.setStyle("-fx-font-size: 18px;");
        start.setOnAction(e -> onStart.run());

        getChildren().addAll(title, text, start);
    }
}
