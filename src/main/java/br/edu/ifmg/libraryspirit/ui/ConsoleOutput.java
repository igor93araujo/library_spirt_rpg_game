package br.edu.ifmg.libraryspirit.ui;

/**
 * Implementação de {@link Output} que escreve as mensagens no terminal.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class ConsoleOutput implements Output {

    /**
     * Escreve o texto no terminal sem pular linha.
     *
     * @param text texto a ser exibido
     */
    @Override
    public void print(String text) {
        System.out.print(text);
        System.out.flush();
    }

    /**
     * Escreve a mensagem no terminal e pula linha.
     *
     * @param message texto a ser exibido
     */
    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
