package br.edu.ifmg.libraryspirit.world;

/**
 * Monta o mapa da biblioteca: cria as salas e liga as saídas entre elas.
 * Separar a construção do mapa do resto do jogo deixa o {@code Main} e o
 * {@code Game} mais simples (princípio da Responsabilidade Única).
 *
 * <pre>
 *               [Seção de Ficção Científica]
 *                           |
 * [Seção de Terror] -- [Hall de Entrada] -- [Seção de Aventura]
 *                           |
 *                [Sala de Leitura Central]
 * </pre>
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class WorldBuilder {

    /**
     * Cria as cinco salas da biblioteca e liga as saídas.
     *
     * @return a sala inicial do jogo (Hall de Entrada)
     */
    public Room build() {
        Room hall = new Room(
                "Você está no Hall de Entrada. Estantes antigas cercam o salão\n"
                + "e páginas soltas flutuam no ar, como se o vento as carregasse.");
        Room horror = new Room(
                "Você está na Seção de Terror. Uma névoa fria cobre o chão,\n"
                + "teias de aranha pendem das estantes altas e algo se move nas sombras.");
        Room adventure = new Room(
                "Você está na Seção de Aventura. Um livro de exploração aberto\n"
                + "transformou a sala numa floresta densa, cheia de raízes e cipós.");
        Room sciFi = new Room(
                "Você está na Seção de Ficção Científica. O teto se abriu em estrelas\n"
                + "holográficas e os computadores antigos piscam como se estivessem vivos.");
        Room readingRoom = new Room(
                "Você está na Sala de Leitura Central. Sob a grande claraboia,\n"
                + "o Grande Tomo repousa no pedestal, cercado por páginas que giram no ar.");

        connect(hall, "oeste", horror, "leste");
        connect(hall, "leste", adventure, "oeste");
        connect(hall, "norte", sciFi, "sul");
        connect(hall, "sul", readingRoom, "norte");

        return hall;
    }

    /**
     * Liga duas salas nos dois sentidos.
     *
     * @param from sala de origem
     * @param direction direção de {@code from} até {@code to}
     * @param to sala de destino
     * @param backDirection direção de {@code to} de volta para {@code from}
     */
    private void connect(Room from, String direction, Room to, String backDirection) {
        from.setExit(direction, to);
        to.setExit(backDirection, from);
    }
}
