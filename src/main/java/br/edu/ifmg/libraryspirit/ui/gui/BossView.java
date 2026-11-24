package br.edu.ifmg.libraryspirit.ui.gui;

import br.edu.ifmg.libraryspirit.items.Theme;

/**
 * Visão imutável do confronto com o Grande Tomo.
 *
 * @param stageTheme tema da etapa atual, ou {@code null} se o chefe foi derrotado
 * @param sealedStages quantidade de etapas já seladas (0 a 3)
 * @param awaitingPage {@code true} se as ameaças da etapa acabaram e falta usar a página
 * @param round rodada atual da etapa (1 a 3), ou 0 se não houver etapa atual
 */
public record BossView(Theme stageTheme, int sealedStages, boolean awaitingPage, int round) {
}
