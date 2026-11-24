package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/** Painel do enigma da porta: pergunta, campo de resposta e botão. Envia {@code responder <texto>}. */
public class RiddlePane extends VBox {
    private final Label title = new Label();
    private final Label question = new Label();
    private final TextField answer = new TextField();
    private final Consumer<String> commandSink;

    public RiddlePane(Consumer<String> commandSink) {
        super(10);
        this.commandSink = commandSink;
        setAlignment(Pos.CENTER);
        setPadding(new Insets(16));
        setMaxSize(420, USE_PREF_SIZE);
        setStyle("-fx-background-color: rgba(20,14,30,0.92); -fx-background-radius: 12;"
                + " -fx-border-color: #ffd36b; -fx-border-radius: 12;");
        title.setStyle("-fx-text-fill: #ffd36b; -fx-font-weight: bold; -fx-font-size: 16px;");
        question.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        question.setWrapText(true);
        answer.setPromptText("Sua resposta");
        Button send = new Button("Responder");
        send.setDefaultButton(false);
        answer.setOnAction(e -> submit());
        send.setOnAction(e -> submit());
        HBox.setHgrow(answer, Priority.ALWAYS);
        getChildren().addAll(title, question, new HBox(8, answer, send));
        setVisible(false);
    }

    private void submit() {
        String text = answer.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        answer.clear();
        commandSink.accept("responder " + text);
        answer.requestFocus();
    }

    /** Mostra ou esconde o painel conforme o enigma pendente. */
    public void update(RiddleView riddle) {
        if (riddle == null) {
            setVisible(false);
            answer.clear();
            return;
        }
        title.setText("Enigma da porta " + riddle.direction());
        question.setText(riddle.question());
        setVisible(true);
    }
}
