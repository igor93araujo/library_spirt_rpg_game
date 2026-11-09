package br.edu.ifmg.libraryspirit.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Saída falsa usada nos testes. Em vez de escrever no terminal,
 * guarda as mensagens numa lista para os testes conferirem.
 */
public class FakeOutput implements Output {

    /** Mensagens recebidas, na ordem. */
    private final List<String> messages = new ArrayList<>();

    @Override
    public void print(String text) {
        messages.add(text);
    }

    @Override
    public void println(String message) {
        messages.add(message);
    }

    /**
     * Retorna as mensagens recebidas.
     *
     * @return lista de mensagens, na ordem em que foram exibidas
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Junta todas as mensagens num texto só, separadas por quebra de linha.
     *
     * @return todas as mensagens
     */
    public String getAllText() {
        return String.join("\n", messages);
    }
}
