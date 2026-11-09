package br.edu.ifmg.libraryspirit.ui;

/**
 * Implementação de {@link Output} que escreve as mensagens no terminal.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class ConsoleOutput implements Output {

    /**
     * Escreve a mensagem no terminal usando {@code System.out}.
     *
     * @param message texto a ser exibido
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
