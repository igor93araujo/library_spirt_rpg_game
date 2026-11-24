package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.boss.SealingStage;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.world.Door;
import br.edu.ifmg.libraryspirit.world.Riddle;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Foto imutável do estado visível do jogo: sala, saídas, itens, vidas e escudo.
 *
 * @param scene cena da sala, ou {@code null} se a sala não tiver uma cena conhecida
 * @param roomName nome da sala
 * @param description descrição da sala
 * @param exits direções com saída, na ordem norte, sul, leste, oeste
 * @param lockedExits direções cuja porta está trancada
 * @param roomItems itens que estão na sala
 * @param inventory itens que o jogador carrega
 * @param lives vidas do jogador
 * @param shield se o escudo está ativo
 * @param threat ameaça esperando reação, ou {@code null}
 * @param boss estado do chefe (só na sala do chefe), ou {@code null}
 * @param riddle enigma pendente numa porta, ou {@code null}
 */
public record GameSnapshot(
        SceneId scene,
        String roomName,
        String description,
        List<String> exits,
        Set<String> lockedExits,
        List<ItemView> roomItems,
        List<ItemView> inventory,
        int lives,
        boolean shield,
        ThreatView threat,
        BossView boss,
        RiddleView riddle) {

    /** Direções consultadas, nesta ordem. */
    public static final List<String> DIRECTIONS = List.of("norte", "sul", "leste", "oeste");

    public GameSnapshot {
        Objects.requireNonNull(roomName, "roomName");
        Objects.requireNonNull(description, "description");
        exits = List.copyOf(exits);
        lockedExits = Set.copyOf(lockedExits);
        roomItems = List.copyOf(roomItems);
        inventory = List.copyOf(inventory);
    }

    /**
     * Monta a foto do estado atual do jogador e da sala onde ele está.
     *
     * @param player jogador
     * @return a foto
     */
    public static GameSnapshot of(Player player) {
        return of(player, null);
    }

    /**
     * Monta a foto do estado atual, incluindo ameaça, enigma e chefe.
     *
     * @param player jogador
     * @param greatTome chefe final, ou {@code null} se não for acompanhado
     * @return a foto
     */
    public static GameSnapshot of(Player player, GreatTome greatTome) {
        Room room = player.getCurrentRoom();
        List<String> exits = new ArrayList<>();
        List<String> locked = new ArrayList<>();
        for (String dir : DIRECTIONS) {
            if (room.getExit(dir) == null) {
                continue;
            }
            exits.add(dir);
            Door door = room.getDoor(dir);
            if (door != null && door.isLocked()) {
                locked.add(dir);
            }
        }
        ThreatView threat = null;
        BossView boss = null;
        if (room.isBossRoom() && greatTome != null) {
            boss = bossView(greatTome);
            SealingStage stage = greatTome.getCurrentStage();
            if (stage != null && stage.hasActiveThreat()) {
                threat = ThreatView.of(stage.getCurrentThreat());
            }
        } else {
            Threat t = room.getThreat();
            if (t != null) {
                threat = ThreatView.of(t);
            }
        }
        RiddleView riddle = null;
        Door riddleDoor = room.findRiddleDoor(player);
        if (riddleDoor != null) {
            Riddle r = riddleDoor.getRiddle();
            String dir = "";
            for (String d : DIRECTIONS) {
                if (room.getDoor(d) == riddleDoor) {
                    dir = d;
                    break;
                }
            }
            riddle = new RiddleView(dir, r.getQuestion());
        }
        return new GameSnapshot(
                SceneId.fromRoomName(room.getName()).orElse(null),
                room.getName(),
                room.getDescription(),
                exits,
                Set.copyOf(locked),
                views(room.getItems()),
                views(player.getItems()),
                player.getLives(),
                player.hasShield(),
                threat,
                boss,
                riddle);
    }

    private static BossView bossView(GreatTome tome) {
        SealingStage stage = tome.getCurrentStage();
        if (stage == null) {
            return new BossView(null, tome.getStages().size(), false, 0);
        }
        return new BossView(
                stage.getTheme(),
                tome.getStages().indexOf(stage),
                stage.isCompleted(),
                Math.min(stage.getCurrentRound(), stage.getThreats().size()));
    }

    private static List<ItemView> views(List<Item> items) {
        List<ItemView> views = new ArrayList<>();
        for (Item item : items) {
            views.add(ItemView.of(item));
        }
        return views;
    }
}
