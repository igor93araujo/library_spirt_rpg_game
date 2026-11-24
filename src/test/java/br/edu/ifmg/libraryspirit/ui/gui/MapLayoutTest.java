package br.edu.ifmg.libraryspirit.ui.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.world.Room;
import br.edu.ifmg.libraryspirit.world.WorldBuilder;

class MapLayoutTest {

    @Test
    void everyWorldRoomMapsToASceneWithUniquePosition() {
        Room hall = new WorldBuilder().build();
        Set<Room> rooms = new HashSet<>();
        rooms.add(hall);
        for (String dir : new String[] {"norte", "sul", "leste", "oeste"}) {
            rooms.add(hall.getExit(dir));
        }
        assertEquals(5, rooms.size());

        Set<SceneId> scenes = new HashSet<>();
        Set<MapLayout.Cell> cells = new HashSet<>();
        for (Room room : rooms) {
            SceneId scene = SceneId.fromRoomName(room.getName()).orElseThrow();
            assertTrue(scenes.add(scene));
            assertTrue(cells.add(MapLayout.positionOf(scene)));
        }
    }

    @Test
    void hallIsAtCenterOfCross() {
        assertEquals(new MapLayout.Cell(1, 1), MapLayout.positionOf(SceneId.HALL));
        assertEquals(new MapLayout.Cell(1, 0), MapLayout.positionOf(SceneId.SCI_FI));
        assertEquals(new MapLayout.Cell(1, 2), MapLayout.positionOf(SceneId.READING_ROOM));
        assertEquals(new MapLayout.Cell(0, 1), MapLayout.positionOf(SceneId.HORROR));
        assertEquals(new MapLayout.Cell(2, 1), MapLayout.positionOf(SceneId.ADVENTURE));
    }

    @Test
    void unknownNameHasNoScene() {
        assertTrue(SceneId.fromRoomName("").isEmpty());
        assertTrue(SceneId.fromRoomName(null).isEmpty());
    }
}
