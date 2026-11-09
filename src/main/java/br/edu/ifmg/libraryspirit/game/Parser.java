package br.edu.ifmg.libraryspirit.game;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

/**
 * Lê as linhas digitadas pelo jogador e as transforma em {@link ParsedInput}.
 * O Parser só lê e interpreta o texto: ele não sabe quais comandos existem
 * nem o que cada um faz (princípio da Responsabilidade Única).
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Parser {

    /** Leitor da entrada (teclado ou outra fonte, como nos testes). */
    private final Scanner scanner;

    /**
     * Cria um Parser que lê do teclado ({@code System.in}).
     */
    public Parser() {
        this(System.in);
    }

    /**
     * Cria um Parser que lê da fonte informada. Útil nos testes.
     *
     * @param input fonte de onde as linhas serão lidas
     */
    public Parser(InputStream input) {
        this.scanner = new Scanner(input, "UTF-8");
    }

    /**
     * Informa se ainda há linhas para ler.
     * Retorna {@code false} quando a entrada termina (ex.: Ctrl+D no terminal).
     *
     * @return {@code true} se existe uma próxima linha
     */
    public boolean hasNextInput() {
        return scanner.hasNextLine();
    }

    /**
     * Lê a próxima linha digitada e a interpreta.
     * Só deve ser chamado quando {@link #hasNextInput()} retornar {@code true}.
     *
     * @return o comando e o argumento da linha lida
     */
    public ParsedInput readInput() {
        return parse(scanner.nextLine());
    }

    /**
     * Interpreta uma linha de texto. A linha é convertida para minúsculas,
     * espaços extras são removidos, a primeira palavra vira o comando e o
     * restante vira o argumento.
     * Ex.: "  USAR   pagina  terror " → comando "usar", argumento "pagina terror".
     *
     * @param line texto digitado pelo jogador
     * @return o comando e o argumento da linha
     */
    public ParsedInput parse(String line) {
        String cleaned = line.trim().toLowerCase(Locale.ROOT);
        if (cleaned.isEmpty()) {
            return new ParsedInput("", "");
        }
        String[] parts = cleaned.split("\\s+", 2);
        String commandWord = parts[0];
        String argument = parts.length > 1 ? parts[1].replaceAll("\\s+", " ") : "";
        return new ParsedInput(commandWord, argument);
    }
}
