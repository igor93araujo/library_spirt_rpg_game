package br.edu.ifmg.libraryspirit.ui.gui;

import br.edu.ifmg.libraryspirit.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Aplicação JavaFX do jogo.
 */
public class LibraryApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle(Game.TITLE);
        Scene scene = new Scene(new BorderPane(), 960, 640);
        scene.setRoot(new IntroView(() -> showGame(scene)));
        stage.setScene(scene);
        stage.show();
    }

    private void showGame(Scene scene) {
        scene.setRoot(new GameView());
    }
}
