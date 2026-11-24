package br.edu.ifmg.libraryspirit.ui.gui;

import java.util.Optional;

/**
 * Identifica a cena (ilustração) de cada sala da biblioteca.
 */
public enum SceneId {
    HALL("Hall de Entrada"),
    HORROR("Seção de Terror"),
    ADVENTURE("Seção de Aventura"),
    SCI_FI("Seção de Ficção Científica"),
    READING_ROOM("Sala de Leitura Central");

    private final String roomName;

    SceneId(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return o nome da sala correspondente, como em {@code Room.getName()}
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Procura a cena de uma sala pelo nome.
     *
     * @param roomName nome da sala (pode ser nulo)
     * @return a cena, ou vazio se o nome não for conhecido
     */
    public static Optional<SceneId> fromRoomName(String roomName) {
        for (SceneId id : values()) {
            if (id.roomName.equals(roomName)) {
                return Optional.of(id);
            }
        }
        return Optional.empty();
    }
}
