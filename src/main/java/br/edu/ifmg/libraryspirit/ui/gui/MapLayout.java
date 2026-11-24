package br.edu.ifmg.libraryspirit.ui.gui;

/**
 * Posição de cada cena na grade do minimapa (cruz com o Hall no centro).
 * Colunas crescem para leste e linhas crescem para o sul.
 */
public final class MapLayout {

    /** Posição em uma grade 3x3. */
    public record Cell(int column, int row) { }

    public static final int COLUMNS = 3;
    public static final int ROWS = 3;

    private MapLayout() {
    }

    /**
     * @param scene cena da sala
     * @return a célula da sala no minimapa
     */
    public static Cell positionOf(SceneId scene) {
        return switch (scene) {
            case HALL -> new Cell(1, 1);
            case HORROR -> new Cell(0, 1);
            case ADVENTURE -> new Cell(2, 1);
            case SCI_FI -> new Cell(1, 0);
            case READING_ROOM -> new Cell(1, 2);
        };
    }
}
