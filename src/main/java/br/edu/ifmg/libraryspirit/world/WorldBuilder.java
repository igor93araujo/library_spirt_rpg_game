package br.edu.ifmg.libraryspirit.world;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.items.Bookmark;
import br.edu.ifmg.libraryspirit.items.Key;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.items.Theme;

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
                "Hall de Entrada",
                "Você está no Hall de Entrada. Estantes antigas cercam o salão, e páginas\n"
                + "soltas flutuam no ar, como se o vento as carregasse. Há quatro passagens,\n"
                + "e algumas parecem fechadas há muito tempo.");
        Room horror = new Room(
                "Seção de Terror",
                "Você está na Seção de Terror. Uma névoa fria cobre o chão, teias de aranha\n"
                + "pendem das estantes altas e algo se move nas sombras. Os livros\n"
                + "sussurram quando você passa.");
        Room adventure = new Room(
                "Seção de Aventura",
                "Você está na Seção de Aventura. Um livro de exploração aberto transformou\n"
                + "a sala numa floresta densa, cheia de raízes e cipós. Ao longe, ouve-se\n"
                + "o ruído de papel dobrando.");
        Room sciFi = new Room(
                "Seção de Ficção Científica",
                "Você está na Seção de Ficção Científica. O teto se abriu em estrelas\n"
                + "holográficas, e os computadores antigos piscam como se estivessem vivos.\n"
                + "Um zumbido metálico ecoa entre as estantes.");
        Room readingRoom = new Room(
                "Sala de Leitura Central",
                "Você está na Sala de Leitura Central. Sob a grande claraboia, o Grande Tomo\n"
                + "repousa no pedestal, cercado por páginas que giram no ar. Há espaços\n"
                + "vazios em sua capa, do tamanho exato de três páginas.");
        readingRoom.setBossRoom(true);

        horror.addItem(new MagicPage(Theme.HORROR));
        horror.addItem(new Bookmark(Bookmark.Type.SHIELD));
        adventure.addItem(new MagicPage(Theme.ADVENTURE));
        adventure.addItem(new Bookmark(Bookmark.Type.LIFE));
        sciFi.addItem(new MagicPage(Theme.SCI_FI));
        sciFi.addItem(new Bookmark(Bookmark.Type.LIFE));
        hall.addItem(new Key("chave de bronze", "Uma chave de bronze, gasta pelo tempo."));
        horror.addItem(new Key("chave de prata", "Uma chave de prata, fria ao toque."));
        connect(hall, "oeste", horror, "leste");
        connect(hall, "leste", adventure, "oeste");
        connect(hall, "norte", sciFi, "sul");
        connect(hall, "sul", readingRoom, "norte");

        lock(hall, "leste", adventure, "oeste", new Door("chave de bronze"));
        lock(hall, "norte", sciFi, "sul", new Door("chave de prata"));
        List<String> pages = new ArrayList<>();
        for (Theme theme : Theme.values()) {
            pages.add(new MagicPage(theme).getName());
        }
        Riddle riddle = new Riddle(
                "Quem escreveu o romance 'Dom Casmurro'?", "machado de assis");
        lock(hall, "sul", readingRoom, "norte", new Door(pages, riddle));

        horror.addThreat(new Threat(
                "Uma sombra desliza pela estante em sua direção...", "esconder"));
        horror.addThreat(new Threat(
                "Um livro de feitiços voa direto na sua cabeça!", "abaixar"));
        adventure.addThreat(new Threat(
                "Raízes brotam do chão sob seus pés!", "pular"));
        adventure.addThreat(new Threat(
                "Cipós grossos balançam das árvores, bem na altura da sua cabeça!", "abaixar"));
        adventure.addThreat(new Threat(
                "Um tigre de origami salta em sua direção, de garras abertas!", "desviar"));
        sciFi.addThreat(new Threat(
                "Um feixe de laser corrompido varre a sala!", "desviar"));
        sciFi.addThreat(new Threat(
                "Um robô de segurança avança, com os olhos vermelhos procurando por você...", "esconder"));

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

    /**
     * Coloca a mesma porta nos dois lados de uma passagem.
     *
     * @param from sala de origem
     * @param direction direção de {@code from} até {@code to}
     * @param to sala de destino
     * @param backDirection direção de {@code to} até {@code from}
     * @param door porta compartilhada
     */
    private void lock(Room from, String direction, Room to, String backDirection, Door door) {
        from.setDoor(direction, door);
        to.setDoor(backDirection, door);
    }
}
