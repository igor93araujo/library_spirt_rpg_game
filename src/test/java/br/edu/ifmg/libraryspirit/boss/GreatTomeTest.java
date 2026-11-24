package br.edu.ifmg.libraryspirit.boss;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.edu.ifmg.libraryspirit.items.Theme;

/**
 * Testes do {@link GreatTome}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
class GreatTomeTest {
    @Test
    void hasThreeStagesInOrder() {
        List<SealingStage> stages = new GreatTome().getStages();
        assertEquals(3, stages.size());
        assertEquals(Theme.HORROR, stages.get(0).getTheme());
        assertEquals(Theme.ADVENTURE, stages.get(1).getTheme());
        assertEquals(Theme.SCI_FI, stages.get(2).getTheme());
    }

    @Test
    void eachStageHasThreeRounds() {
        for (SealingStage stage : new GreatTome().getStages()) {
            assertEquals(3, stage.getThreats().size());
        }
    }

    @Test
    void stageOnlyAdvancesWhenAllRoundsAreDone() {
        GreatTome tome = new GreatTome();
        SealingStage first = tome.getCurrentStage();
        first.advanceRound();
        tome.advanceStage();
        assertEquals(first, tome.getCurrentStage());
        first.advanceRound();
        first.advanceRound();
        tome.advanceStage();
        assertEquals(Theme.ADVENTURE, tome.getCurrentStage().getTheme());
    }

    @Test
    void isDefeatedAfterThreeStagesAndResetRestartsFight() {
        GreatTome tome = new GreatTome();
        while (!tome.isDefeated()) {
            SealingStage stage = tome.getCurrentStage();
            while (!stage.isCompleted()) {
                stage.advanceRound();
            }
            tome.advanceStage();
        }
        assertTrue(tome.isDefeated());
        tome.resetStages();
        assertEquals(Theme.HORROR, tome.getCurrentStage().getTheme());
        assertEquals(1, tome.getCurrentStage().getCurrentRound());
    }
}
