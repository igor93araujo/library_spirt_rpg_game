package br.edu.ifmg.libraryspirit.world;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Enigma literário: uma pergunta e a resposta esperada.
 * A comparação ignora maiúsculas, acentos e espaços nas pontas.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Riddle {

    /** Pergunta mostrada ao jogador. */
    private final String question;

    /** Resposta esperada. */
    private final String answer;

    /**
     * Cria um enigma.
     *
     * @param question pergunta
     * @param answer resposta esperada
     */
    public Riddle(String question, String answer) {
        this.question = question;
        this.answer = normalize(answer);
    }

    /**
     * Retorna a pergunta.
     *
     * @return o texto da pergunta
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Confere uma resposta.
     *
     * @param attempt resposta digitada
     * @return {@code true} se estiver correta
     */
    public boolean isCorrect(String attempt) {
        return answer.equals(normalize(attempt));
    }

    /**
     * Tira acentos, espaços das pontas e maiúsculas.
     *
     * @param text texto original
     * @return texto normalizado
     */
    private static String normalize(String text) {
        String decomposed = Normalizer.normalize(text.trim(), Normalizer.Form.NFD);
        return decomposed.replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }
}
