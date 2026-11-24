package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.application.Application;

/**
 * Ponto de entrada da interface gráfica. Não herda de Application para
 * funcionar fora do module path.
 */
public final class GuiLauncher {

    private GuiLauncher() {
    }

    public static void main(String[] args) {
        Application.launch(LibraryApp.class, args);
    }
}
