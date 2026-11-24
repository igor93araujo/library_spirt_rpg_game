package br.edu.ifmg.libraryspirit.ui.gui;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import br.edu.ifmg.libraryspirit.commands.Command;
import br.edu.ifmg.libraryspirit.game.GameSetup;
import br.edu.ifmg.libraryspirit.game.ParsedInput;
import br.edu.ifmg.libraryspirit.game.Parser;

/**
 * Executa o texto de cada comando sobre as classes do jogo e entrega as
 * mensagens ao log. Não depende de JavaFX.
 */
public class GameController {

    private final Consumer<LogEntry> logSink;
    private final Parser parser = new Parser(new ByteArrayInputStream(new byte[0]));
    private final Set<SceneId> visited = EnumSet.noneOf(SceneId.class);

    private List<LogEntry> pending;
    private GameSetup setup;
    private boolean finished;
    private boolean victory;

    /**
     * Cria o controlador com um jogo novo.
     *
     * @param logSink recebe cada mensagem produzida pelos comandos
     */
    public GameController(Consumer<LogEntry> logSink) {
        this.logSink = Objects.requireNonNull(logSink, "logSink");
        start();
    }

    private void start() {
        setup = GameSetup.create(new GuiOutput(this::emit));
        finished = false;
        victory = false;
        visited.clear();
        markVisited();
    }

    /**
     * Executa uma linha digitada (ou gerada por clique).
     *
     * @param line texto do comando
     * @return eventos de animação do comando (vazio por enquanto)
     */
    public List<GameEvent> submit(String line) {
        if (finished) {
            return List.of();
        }
        GameSnapshot before = snapshot();
        boolean ended = false;
        boolean unknown = false;
        pending = new ArrayList<>();
        try {
        ParsedInput input = parser.parse(line == null ? "" : line);
        if (!input.isEmpty()) {
            Command command = setup.getCommandWords().get(input.getCommandWord());
            if (command == null) {
                unknown = true;
                emit(new LogEntry(
                        "Não entendi. Digite 'ajuda' para ver os comandos.", LogEntry.Type.NARRATIVE));
            } else if (command.execute(input.getArgument())) {
                ended = true;
                finished = true;
                victory = setup.getGreatTome().isDefeated();
            }
        }
        markVisited();
        GameSnapshot after = snapshot();
        LogEntry.Type type = classify(input, unknown, before, after);
        flush(type);
        return diff(before, after, ended, victory);
        } finally {
            if (pending != null) {
                flush(LogEntry.Type.NARRATIVE);
            }
        }
    }

    private void emit(String text) {
        emit(new LogEntry(text, LogEntry.Type.NARRATIVE));
    }

    private void emit(LogEntry entry) {
        if (pending != null) {
            pending.add(entry);
        } else {
            logSink.accept(entry);
        }
    }

    private void flush(LogEntry.Type type) {
        List<LogEntry> entries = pending;
        pending = null;
        for (LogEntry e : entries) {
            boolean blank = e.text().isBlank();
            logSink.accept(blank || type == LogEntry.Type.NARRATIVE
                    ? e : new LogEntry(e.text(), type));
        }
    }

    /** Escolhe o tipo do log do comando: dano é WARNING; ação sem efeito é ERROR. */
    private static LogEntry.Type classify(ParsedInput input, boolean unknown,
                                          GameSnapshot before, GameSnapshot after) {
        if (unknown) {
            return LogEntry.Type.ERROR;
        }
        if (after.lives() < before.lives()) {
            return LogEntry.Type.WARNING;
        }
        if (!input.isEmpty()) {
            String word = input.getCommandWord();
            boolean action = "ir".equals(word) || "pegar".equals(word)
                    || "usar".equals(word) || "responder".equals(word);
            if (action && before.equals(after)) {
                return LogEntry.Type.ERROR;
            }
        }
        return LogEntry.Type.NARRATIVE;
    }

    /**
     * Compara os estados antes e depois de um comando e devolve os eventos de animação.
     */
    static List<GameEvent> diff(GameSnapshot before, GameSnapshot after, boolean ended, boolean won) {
        List<GameEvent> events = new ArrayList<>();
        boolean roomChanged = before.scene() != after.scene()
                || !before.roomName().equals(after.roomName());
        if (roomChanged) {
            events.add(GameEvent.ROOM_CHANGED);
        }

        boolean damaged = after.lives() < before.lives();
        if (damaged) {
            events.add(GameEvent.DAMAGE);
        } else if (before.shield() && !after.shield() && after.lives() == before.lives()) {
            events.add(GameEvent.SHIELD_ABSORBED);
        }

        boolean bossReset = isBossReset(before, after);
        if (bossReset) {
            events.add(GameEvent.BOSS_RESET);
        } else if (after.lives() > before.lives()) {
            events.add(GameEvent.LIFE_GAINED);
        }

        if (!roomChanged && after.inventory().size() > before.inventory().size()
                && after.roomItems().size() < before.roomItems().size()) {
            events.add(GameEvent.ITEM_TAKEN);
        }

        if (!roomChanged && !damaged && !bossReset
                && before.threat() != null && !before.threat().equals(after.threat())) {
            events.add(GameEvent.THREAT_CLEARED);
        }

        if (before.boss() != null && after.boss() != null
                && after.boss().sealedStages() > before.boss().sealedStages()) {
            events.add(GameEvent.STAGE_SEALED);
        }

        if (ended) {
            events.add(won ? GameEvent.VICTORY : GameEvent.GAME_OVER);
        }
        return events;
    }

    /** Derrota no chefe: vidas restauradas e a luta volta ao começo. */
    private static boolean isBossReset(GameSnapshot before, GameSnapshot after) {
        BossView b = before.boss();
        BossView a = after.boss();
        if (b == null || a == null || after.lives() <= before.lives()) {
            return false;
        }
        boolean atStart = a.sealedStages() == 0 && a.round() <= 1;
        boolean hadProgress = b.sealedStages() > 0 || b.round() > 1;
        boolean noItemUsed = after.inventory().size() >= before.inventory().size();
        return atStart && (hadProgress || noItemUsed);
    }

    /** @return foto do estado atual */
    public GameSnapshot snapshot() {
        return GameSnapshot.of(setup.getPlayer(), setup.getGreatTome());
    }

    /** @return cenas já visitadas (somente leitura) */
    public Set<SceneId> visited() {
        return Collections.unmodifiableSet(visited);
    }

    /** @return {@code true} se o jogo terminou (vitória, derrota ou sair) */
    public boolean isFinished() {
        return finished;
    }

    /** @return {@code true} se o jogo terminou com a vitória do jogador */
    public boolean isVictory() {
        return victory;
    }

    /** Começa um jogo novo, com as salas visitadas zeradas. */
    public void restart() {
        start();
    }

    private void markVisited() {
        SceneId scene = snapshot().scene();
        if (scene != null) {
            visited.add(scene);
        }
    }
}
