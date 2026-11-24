package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Desenha o Grande Tomo (livro com 3 espaços de página na capa) e a barra de selos.
 */
final class BossArt {

    private BossArt() {
    }

    /**
     * Desenha o livro.
     *
     * @param sealAnim progresso (0 a 1) da animação de selo; 1 = sem animação
     * @param shake deslocamento horizontal em pixels (animação de recomeço)
     */
    static void drawTome(GraphicsContext g, BossView boss, double cx, double cy, double size,
            double t, double sealAnim, double shake) {
        double w = size;
        double h = size * 0.75;
        double x = cx - w / 2 + shake;
        double y = cy - h / 2 + Math.sin(t * 1.6) * 5;
        boolean defeated = boss.stageTheme() == null;

        // brilho atrás do livro
        double glow = 0.25 + 0.15 * Math.sin(t * 2);
        if (sealAnim < 1) {
            glow += 0.6 * (1 - sealAnim);
        }
        g.setFill(Color.web(defeated ? "#9fe8a8" : "#ffd36b", Math.min(1, glow) * 0.45));
        g.fillOval(x - w * 0.15, y - h * 0.2, w * 1.3, h * 1.4);

        // capa e lombada
        g.setFill(Color.web("#4a2a5e"));
        g.fillRoundRect(x, y, w, h, 16, 16);
        g.setStroke(Color.web("#ffd36b"));
        g.setLineWidth(3);
        g.strokeRoundRect(x + 5, y + 5, w - 10, h - 10, 12, 12);
        g.setFill(Color.web("#2e1a3d"));
        g.fillRoundRect(x, y, w * 0.08, h, 10, 10);

        // olhos do livro
        double ey = y + h * 0.22;
        g.setFill(Color.web(defeated ? "#9fe8a8" : "#ff5a5a"));
        g.fillOval(cx - w * 0.17 + shake, ey, w * 0.09, w * 0.06);
        g.fillOval(cx + w * 0.08 + shake, ey, w * 0.09, w * 0.06);

        // 3 espaços de página
        double slotW = w * 0.2;
        double slotH = h * 0.38;
        double gap = w * 0.06;
        double sx = cx - (3 * slotW + 2 * gap) / 2 + shake;
        double sy = y + h * 0.45;
        for (int i = 0; i < 3; i++) {
            double px = sx + i * (slotW + gap);
            boolean sealed = i < boss.sealedStages();
            boolean current = i == boss.sealedStages() && !defeated;
            g.setFill(Color.web("#1b1026"));
            g.fillRoundRect(px, sy, slotW, slotH, 6, 6);
            if (sealed) {
                // a última página selada "encaixa" vindo de cima
                double drop = (i == boss.sealedStages() - 1) ? (1 - sealAnim) * -slotH * 0.8 : 0;
                g.setFill(Color.web("#f6ecd0"));
                g.fillRoundRect(px + 3, sy + 3 + drop, slotW - 6, slotH - 6, 4, 4);
                g.setStroke(Color.web("#e0b64a"));
                g.setLineWidth(2);
                g.strokeRoundRect(px + 3, sy + 3 + drop, slotW - 6, slotH - 6, 4, 4);
            } else {
                g.setStroke(current ? Color.web(ItemIcon.themeColor(boss.stageTheme()))
                        : Color.web("#6b5a80"));
                g.setLineWidth(current ? 3 : 1.5);
                g.setLineDashes(6, 4);
                g.strokeRoundRect(px, sy, slotW, slotH, 6, 6);
                g.setLineDashes((double[]) null);
            }
        }
    }

    /** Desenha a barra de etapas (3 selos) no canto da cena. */
    static void drawSealBar(GraphicsContext g, BossView boss, double x, double y, double t,
            double sealAnim) {
        double r = 12;
        for (int i = 0; i < 3; i++) {
            double cx = x + r + i * (r * 2 + 10);
            boolean sealed = i < boss.sealedStages();
            g.setFill(Color.web("#15101d", 0.8));
            g.fillOval(cx - r - 3, y - 3, r * 2 + 6, r * 2 + 6);
            if (sealed) {
                double pulse = (i == boss.sealedStages() - 1 && sealAnim < 1) ? (1 - sealAnim) * 6 : 0;
                g.setFill(Color.web("#ffd36b"));
                g.fillOval(cx - r - pulse, y - pulse, (r + pulse) * 2, (r + pulse) * 2);
                g.setStroke(Color.web("#7a5a10"));
                g.setLineWidth(2);
                g.strokeLine(cx - 5, y + r, cx - 1, y + r + 5);
                g.strokeLine(cx - 1, y + r + 5, cx + 6, y + r - 5);
            } else {
                boolean current = i == boss.sealedStages() && boss.stageTheme() != null;
                g.setStroke(current
                        ? Color.web(ItemIcon.themeColor(boss.stageTheme()),
                                0.6 + 0.4 * Math.sin(t * 4))
                        : Color.web("#6b5a80"));
                g.setLineWidth(current ? 3 : 2);
                g.strokeOval(cx - r, y, r * 2, r * 2);
            }
        }
    }
}
