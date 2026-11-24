package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Sprites das 7 ameaças, desenhados por código e animados por um tempo em segundos.
 */
final class ThreatArt {

    /** Tipos de sprite, escolhidos pela descrição da ameaça. */
    enum Kind { SHADOW, SPELLBOOK, ROOTS, VINES, TIGER, LASER, ROBOT }

    private ThreatArt() {
    }

    /** Escolhe o sprite a partir da descrição (o modelo não expõe um id). */
    static Kind kindOf(String description) {
        String d = description.toLowerCase();
        if (d.contains("sombra")) {
            return Kind.SHADOW;
        }
        if (d.contains("feitiço") || d.contains("feitico")) {
            return Kind.SPELLBOOK;
        }
        if (d.contains("raíz") || d.contains("raiz")) {
            return Kind.ROOTS;
        }
        if (d.contains("cipó") || d.contains("cipo")) {
            return Kind.VINES;
        }
        if (d.contains("tigre")) {
            return Kind.TIGER;
        }
        if (d.contains("laser")) {
            return Kind.LASER;
        }
        if (d.contains("robô") || d.contains("robo")) {
            return Kind.ROBOT;
        }
        return Kind.SHADOW;
    }

    /** Desenha o sprite centrado em (cx, cy), com tamanho aproximado {@code s}. */
    static void draw(GraphicsContext g, Kind kind, double cx, double cy, double s, double t) {
        g.save();
        g.translate(cx, cy);
        switch (kind) {
            case SHADOW -> shadow(g, s, t);
            case SPELLBOOK -> spellbook(g, s, t);
            case ROOTS -> roots(g, s, t);
            case VINES -> vines(g, s, t);
            case TIGER -> tiger(g, s, t);
            case LASER -> laser(g, s, t);
            case ROBOT -> robot(g, s, t);
        }
        g.restore();
    }

    private static void shadow(GraphicsContext g, double s, double t) {
        double sway = Math.sin(t * 2) * s * 0.08;
        g.setFill(Color.rgb(15, 5, 30, 0.85));
        g.fillOval(-s * 0.4 + sway, -s * 0.5, s * 0.8, s * 0.9);
        g.fillPolygon(
                new double[] {-s * 0.4 + sway, -s * 0.2, 0, s * 0.2, s * 0.4 + sway},
                new double[] {s * 0.0, s * 0.5, s * 0.3, s * 0.55, 0}, 5);
        g.setFill(Color.rgb(255, 80, 80));
        g.fillOval(-s * 0.2 + sway, -s * 0.2, s * 0.12, s * 0.12);
        g.fillOval(s * 0.08 + sway, -s * 0.2, s * 0.12, s * 0.12);
    }

    private static void spellbook(GraphicsContext g, double s, double t) {
        g.rotate(Math.sin(t * 6) * 12);
        g.setFill(Color.rgb(110, 40, 140));
        g.fillRoundRect(-s * 0.35, -s * 0.28, s * 0.7, s * 0.56, 8, 8);
        g.setFill(Color.rgb(240, 225, 190));
        g.fillRect(-s * 0.3, -s * 0.22, s * 0.6, s * 0.44);
        double flap = Math.abs(Math.sin(t * 10)) * s * 0.25;
        g.setFill(Color.rgb(140, 60, 170));
        g.fillPolygon(new double[] {-s * 0.35, -s * 0.6, -s * 0.35},
                new double[] {-s * 0.2, -s * 0.2 - flap, s * 0.1}, 3);
        g.fillPolygon(new double[] {s * 0.35, s * 0.6, s * 0.35},
                new double[] {-s * 0.2, -s * 0.2 - flap, s * 0.1}, 3);
        g.setStroke(Color.GOLD);
        g.strokeOval(-s * 0.1, -s * 0.1, s * 0.2, s * 0.2);
    }

    private static void roots(GraphicsContext g, double s, double t) {
        g.setStroke(Color.rgb(90, 60, 30));
        g.setLineWidth(Math.max(3, s * 0.07));
        for (int i = -2; i <= 2; i++) {
            double h = s * (0.35 + 0.1 * Math.sin(t * 3 + i));
            double x = i * s * 0.18;
            g.strokeLine(x, s * 0.4, x + Math.sin(t * 2 + i) * s * 0.08, s * 0.4 - h);
        }
        g.setLineWidth(1);
    }

    private static void vines(GraphicsContext g, double s, double t) {
        g.setStroke(Color.rgb(50, 130, 60));
        g.setLineWidth(Math.max(3, s * 0.06));
        for (int i = -1; i <= 1; i++) {
            double x = i * s * 0.25;
            double prevX = x;
            double prevY = -s * 0.5;
            for (int k = 1; k <= 8; k++) {
                double y = -s * 0.5 + k * s * 0.08;
                double nx = x + Math.sin(t * 3 + k * 0.6 + i) * s * 0.06 * k / 4;
                g.strokeLine(prevX, prevY, nx, y);
                prevX = nx;
                prevY = y;
            }
        }
        g.setLineWidth(1);
    }

    private static void tiger(GraphicsContext g, double s, double t) {
        double hop = Math.abs(Math.sin(t * 4)) * s * 0.15;
        g.translate(0, -hop);
        g.setFill(Color.rgb(250, 245, 230));
        g.fillPolygon(new double[] {-s * 0.45, 0, s * 0.45, s * 0.3, -s * 0.3},
                new double[] {s * 0.1, -s * 0.35, s * 0.1, s * 0.35, s * 0.35}, 5);
        g.setStroke(Color.rgb(230, 120, 30));
        g.setLineWidth(3);
        for (int i = -2; i <= 2; i++) {
            g.strokeLine(i * s * 0.12, -s * 0.05, i * s * 0.14, s * 0.2);
        }
        g.setLineWidth(1);
        g.setFill(Color.rgb(200, 30, 30));
        g.fillOval(-s * 0.12, -s * 0.12, s * 0.08, s * 0.08);
        g.fillOval(s * 0.04, -s * 0.12, s * 0.08, s * 0.08);
    }

    private static void laser(GraphicsContext g, double s, double t) {
        double y = Math.sin(t * 2.5) * s * 0.3;
        g.setGlobalAlpha(0.35);
        g.setStroke(Color.RED);
        g.setLineWidth(s * 0.14);
        g.strokeLine(-s * 0.7, y, s * 0.7, y);
        g.setGlobalAlpha(1);
        g.setStroke(Color.rgb(255, 180, 180));
        g.setLineWidth(s * 0.04);
        g.strokeLine(-s * 0.7, y, s * 0.7, y);
        g.setLineWidth(1);
        g.setFill(Color.DARKGRAY);
        g.fillRect(-s * 0.78, y - s * 0.08, s * 0.1, s * 0.16);
    }

    private static void robot(GraphicsContext g, double s, double t) {
        double bob = Math.sin(t * 5) * s * 0.03;
        g.translate(0, bob);
        g.setFill(Color.rgb(120, 130, 150));
        g.fillRoundRect(-s * 0.25, -s * 0.05, s * 0.5, s * 0.45, 8, 8);
        g.fillRoundRect(-s * 0.2, -s * 0.4, s * 0.4, s * 0.32, 8, 8);
        g.setFill(Math.sin(t * 8) > 0 ? Color.RED : Color.DARKRED);
        g.fillOval(-s * 0.13, -s * 0.3, s * 0.1, s * 0.1);
        g.fillOval(s * 0.03, -s * 0.3, s * 0.1, s * 0.1);
        g.setStroke(Color.GRAY);
        g.strokeLine(0, -s * 0.4, 0, -s * 0.5);
    }
}
