package br.edu.ifmg.libraryspirit.ui.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * Desenhos feitos em código (sem arquivos de imagem). Cada método desenha a cena
 * inteira na área {@code w x h}.
 */
public final class Art {

    private Art() {
    }

    /**
     * Desenha o fundo da cena indicada. Cenas ainda sem arte própria recebem um fundo neutro.
     *
     * @param g contexto do canvas
     * @param scene cena (pode ser nula)
     * @param w largura
     * @param h altura
     */
    public static void drawScene(GraphicsContext g, SceneId scene, double w, double h) {
        if (scene == null) {
            drawPlain(g, w, h);
            return;
        }
        switch (scene) {
            case HALL -> drawHall(g, w, h);
            case HORROR -> drawHorror(g, w, h);
            case ADVENTURE -> drawAdventure(g, w, h);
            case SCI_FI -> drawSciFi(g, w, h);
            case READING_ROOM -> drawReadingRoom(g, w, h);
            default -> drawPlain(g, w, h);
        }
    }

    private static void vGradient(GraphicsContext g, double x, double y, double w, double h,
            String top, String bottom) {
        g.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(top)), new Stop(1, Color.web(bottom))));
        g.fillRect(x, y, w, h);
    }

    private static void glow(GraphicsContext g, double cx, double cy, double r, String color,
            double alpha) {
        g.setFill(new RadialGradient(0, 0, cx, cy, r, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(color, alpha)), new Stop(1, Color.web(color, 0))));
        g.fillOval(cx - r, cy - r, r * 2, r * 2);
    }

    /** Terror: noite roxa/vermelha, estantes tortas, teias, olhos brilhando e lua pálida. */
    public static void drawHorror(GraphicsContext g, double w, double h) {
        double floorY = h * 0.74;
        vGradient(g, 0, 0, w, floorY, "#1a0c1f", "#3a1020");
        vGradient(g, 0, floorY, w, h - floorY, "#1c1018", "#08040a");
        // lua pálida
        glow(g, w * 0.5, h * 0.22, w * 0.18, "#d9d4ff", 0.35);
        g.setFill(Color.web("#e8e4ff", 0.85));
        g.fillOval(w * 0.5 - w * 0.04, h * 0.22 - w * 0.04, w * 0.08, w * 0.08);
        // estantes tortas
        for (int i = 0; i < 2; i++) {
            double x = i == 0 ? w * 0.04 : w * 0.80;
            g.save();
            g.translate(x + w * 0.08, floorY);
            g.rotate(i == 0 ? -4 : 5);
            g.setFill(Color.web("#120810"));
            g.fillRect(-w * 0.08, -(floorY - h * 0.1), w * 0.16, floorY - h * 0.1);
            g.setFill(Color.web("#4a1522"));
            for (int r = 0; r < 5; r++) {
                double ry = -(floorY - h * 0.1) + r * (floorY - h * 0.1) / 5.0;
                g.fillRect(-w * 0.08, ry + (floorY - h * 0.1) / 5.0 - 4, w * 0.16, 4);
                for (int b = 0; b < 6; b++) {
                    g.setFill(Color.web(b % 2 == 0 ? "#2a0d18" : "#3b1a2e"));
                    g.fillRect(-w * 0.075 + b * w * 0.024, ry + 6, w * 0.018,
                            (floorY - h * 0.1) / 5.0 - 12 - (b * 3) % 7);
                }
                g.setFill(Color.web("#4a1522"));
            }
            g.restore();
        }
        // teias de aranha nos cantos
        g.setStroke(Color.web("#cfc8e0", 0.45));
        g.setLineWidth(1);
        for (int i = 1; i <= 5; i++) {
            double r = w * 0.04 * i;
            g.strokeArc(-r, -r, r * 2, r * 2, 270, 90, javafx.scene.shape.ArcType.OPEN);
            g.strokeArc(w - r, -r, r * 2, r * 2, 180, 90, javafx.scene.shape.ArcType.OPEN);
        }
        for (int i = 0; i <= 4; i++) {
            double a = Math.toRadians(i * 22.5);
            g.strokeLine(0, 0, Math.cos(a) * w * 0.2, Math.sin(a) * w * 0.2);
            g.strokeLine(w, 0, w - Math.cos(a) * w * 0.2, Math.sin(a) * w * 0.2);
        }
        // olhos brilhando nas sombras
        double[][] eyes = {{0.28, 0.55}, {0.31, 0.56}, {0.70, 0.62}, {0.73, 0.61}, {0.45, 0.45}, {0.48, 0.45}};
        for (double[] e : eyes) {
            glow(g, w * e[0], h * e[1], w * 0.015, "#ff2a2a", 0.8);
            g.setFill(Color.web("#ff5a5a"));
            g.fillOval(w * e[0] - 2, h * e[1] - 2, 4, 4);
        }
        // névoa
        glow(g, w * 0.5, floorY, w * 0.5, "#8a5aa0", 0.18);
        // vinheta
        g.setFill(new RadialGradient(0, 0, w * 0.5, h * 0.5, w * 0.7, false, CycleMethod.NO_CYCLE,
                new Stop(0.5, Color.web("#000000", 0)), new Stop(1, Color.web("#000000", 0.6))));
        g.fillRect(0, 0, w, h);
    }

    /** Aventura: floresta/selva verde com raios de sol, folhas, cipós e mapa antigo. */
    public static void drawAdventure(GraphicsContext g, double w, double h) {
        double floorY = h * 0.75;
        vGradient(g, 0, 0, w, floorY, "#1f4a2a", "#5a8a3a");
        vGradient(g, 0, floorY, w, h - floorY, "#5a3d1f", "#2e1d0e");
        // troncos
        double[] trunks = {0.06, 0.20, 0.78, 0.92};
        for (int i = 0; i < trunks.length; i++) {
            double tw = w * (0.04 + 0.01 * (i % 2));
            g.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#3a2410")), new Stop(1, Color.web("#5e3d1c"))));
            g.fillRect(w * trunks[i] - tw / 2, 0, tw, floorY + 6);
        }
        // cipós
        g.setStroke(Color.web("#2f6a2a"));
        g.setLineWidth(3);
        for (int i = 0; i < 5; i++) {
            double x = w * (0.28 + i * 0.1);
            g.strokeLine(x, 0, x + 6 * (i % 2 == 0 ? 1 : -1), h * (0.18 + 0.05 * (i % 3)));
        }
        // folhagem no topo
        g.setFill(Color.web("#14381c", 0.9));
        for (int i = 0; i < 14; i++) {
            g.fillOval(w * i / 13.0 - w * 0.05, -h * 0.06, w * 0.1, h * 0.14 + (i % 3) * h * 0.02);
        }
        // raios de sol
        g.setFill(Color.web("#fff2a0", 0.14));
        for (int i = 0; i < 4; i++) {
            double x = w * (0.32 + i * 0.1);
            g.fillPolygon(new double[]{x, x + w * 0.04, x + w * 0.16, x + w * 0.10},
                    new double[]{0, 0, floorY, floorY}, 4);
        }
        glow(g, w * 0.5, 0, w * 0.4, "#fff2a0", 0.35);
        // arbustos
        g.setFill(Color.web("#2f6a2a"));
        for (int i = 0; i < 8; i++) {
            g.fillOval(w * (0.08 + i * 0.12), floorY - h * 0.04, w * 0.1, h * 0.09);
        }
        // mapa do tesouro desenrolado
        double mx = w * 0.40, my = h * 0.78, mw = w * 0.20, mh = h * 0.16;
        g.setFill(Color.web("#e8d49a"));
        g.fillRoundRect(mx, my, mw, mh, 8, 8);
        g.setStroke(Color.web("#8a6a2a"));
        g.setLineWidth(2);
        g.strokeRoundRect(mx, my, mw, mh, 8, 8);
        g.setStroke(Color.web("#a33a2a"));
        g.setLineDashes(6, 5);
        g.strokePolyline(new double[]{mx + mw * 0.1, mx + mw * 0.35, mx + mw * 0.55, mx + mw * 0.85},
                new double[]{my + mh * 0.8, my + mh * 0.35, my + mh * 0.65, my + mh * 0.25}, 4);
        g.setLineDashes((double[]) null);
        g.setStroke(Color.web("#a33a2a"));
        g.strokeLine(mx + mw * 0.80, my + mh * 0.15, mx + mw * 0.90, my + mh * 0.35);
        g.strokeLine(mx + mw * 0.90, my + mh * 0.15, mx + mw * 0.80, my + mh * 0.35);
    }

    /** Ficção científica: painéis metálicos, neon ciano/magenta, janela espacial e holograma. */
    public static void drawSciFi(GraphicsContext g, double w, double h) {
        double floorY = h * 0.74;
        vGradient(g, 0, 0, w, floorY, "#0a1428", "#162a4a");
        vGradient(g, 0, floorY, w, h - floorY, "#1a2236", "#0a0e1a");
        // janela com espaço e planeta
        double wx = w * 0.30, wy = h * 0.08, ww = w * 0.40, wh = h * 0.38;
        g.setFill(Color.web("#02030a"));
        g.fillRoundRect(wx, wy, ww, wh, 16, 16);
        g.setFill(Color.WHITE);
        for (int i = 0; i < 40; i++) {
            double sx = wx + 8 + ((i * 53) % 100) / 100.0 * (ww - 16);
            double sy = wy + 8 + ((i * 37) % 100) / 100.0 * (wh - 16);
            g.fillOval(sx, sy, 1.5 + i % 2, 1.5 + i % 2);
        }
        g.setFill(new RadialGradient(0, 0, wx + ww * 0.7, wy + wh * 0.6, ww * 0.2, false,
                CycleMethod.NO_CYCLE, new Stop(0, Color.web("#ff9a5a")), new Stop(1, Color.web("#7a2a5a"))));
        g.fillOval(wx + ww * 0.5, wy + wh * 0.3, ww * 0.4, ww * 0.4);
        g.setStroke(Color.web("#5adfff"));
        g.setLineWidth(3);
        g.strokeRoundRect(wx, wy, ww, wh, 16, 16);
        // painéis laterais
        g.setStroke(Color.web("#2a3a5a"));
        g.setLineWidth(2);
        for (int i = 1; i < 8; i++) {
            double x = w * i / 8.0;
            if (x < wx - 10 || x > wx + ww + 10) {
                g.strokeLine(x, 0, x, floorY);
            }
        }
        // estantes de datacards com neon
        for (int i = 0; i < 2; i++) {
            double x = i == 0 ? w * 0.04 : w * 0.80;
            double y = h * 0.12, sw = w * 0.16, sh = floorY - y;
            g.setFill(Color.web("#10182a"));
            g.fillRect(x, y, sw, sh);
            for (int r = 0; r < 5; r++) {
                double ry = y + 8 + r * (sh - 16) / 5.0;
                g.setFill(Color.web(r % 2 == 0 ? "#5adfff" : "#ff4adf", 0.85));
                g.fillRect(x + 6, ry + (sh - 16) / 5.0 - 6, sw - 12, 3);
                for (int b = 0; b < 8; b++) {
                    g.setFill(Color.web("#24344f"));
                    g.fillRect(x + 8 + b * (sw - 16) / 8.0, ry + 4, (sw - 16) / 8.0 - 3,
                            (sh - 16) / 5.0 - 14);
                }
            }
            g.setStroke(Color.web("#5adfff"));
            g.setLineWidth(2);
            g.strokeRect(x, y, sw, sh);
        }
        // linhas de grade no chão
        g.setStroke(Color.web("#5adfff", 0.35));
        g.setLineWidth(1);
        for (int i = 0; i <= 10; i++) {
            g.strokeLine(w * 0.5, floorY, w * i / 10.0, h);
        }
        for (int i = 1; i < 5; i++) {
            double y = floorY + (h - floorY) * i * i / 25.0;
            g.strokeLine(0, y, w, y);
        }
        // holograma
        glow(g, w * 0.5, floorY + h * 0.05, w * 0.12, "#5adfff", 0.5);
        g.setStroke(Color.web("#8af0ff", 0.9));
        g.setLineWidth(2);
        double hx = w * 0.5, hy = h * 0.62;
        g.strokeRect(hx - w * 0.03, hy - w * 0.03, w * 0.06, w * 0.06);
        g.strokeOval(hx - w * 0.045, hy - w * 0.045, w * 0.09, w * 0.09);
        g.strokeLine(hx - w * 0.03, hy - w * 0.03, hx + w * 0.03, hy + w * 0.03);
    }

    /** Sala de Leitura: clima solene e dourado, mesa central, abóbada, lustre e vitral. */
    public static void drawReadingRoom(GraphicsContext g, double w, double h) {
        double floorY = h * 0.72;
        vGradient(g, 0, 0, w, floorY, "#2a1f4a", "#6a4a7a");
        vGradient(g, 0, floorY, w, h - floorY, "#7a5a3a", "#3a2614");
        // colunas
        for (int i = 0; i < 4; i++) {
            double x = w * (0.08 + i * 0.28);
            g.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#cbbf9f")), new Stop(0.5, Color.web("#efe6c8")),
                    new Stop(1, Color.web("#a89a78"))));
            g.fillRect(x, h * 0.06, w * 0.04, floorY - h * 0.06);
            g.fillRect(x - 4, h * 0.04, w * 0.04 + 8, 10);
            g.fillRect(x - 4, floorY - 8, w * 0.04 + 8, 10);
        }
        // vitral circular
        double cx = w * 0.5, cy = h * 0.20, r = h * 0.14;
        String[] cols = {"#c93a3a", "#3a6ac9", "#e0b64a", "#3aa35a", "#9a3ac9", "#e07a3a"};
        for (int i = 0; i < 6; i++) {
            g.setFill(Color.web(cols[i], 0.85));
            g.fillArc(cx - r, cy - r, r * 2, r * 2, i * 60, 60, javafx.scene.shape.ArcType.ROUND);
        }
        g.setStroke(Color.web("#2a1f14"));
        g.setLineWidth(4);
        g.strokeOval(cx - r, cy - r, r * 2, r * 2);
        glow(g, cx, cy, r * 3, "#ffe9a0", 0.30);
        // lustre
        g.setStroke(Color.web("#3a2a14"));
        g.setLineWidth(2);
        g.strokeLine(cx, cy + r, cx, h * 0.40);
        g.setStroke(Color.web("#e0b64a"));
        g.setLineWidth(4);
        g.strokeLine(cx - w * 0.05, h * 0.42, cx + w * 0.05, h * 0.42);
        for (int i = -2; i <= 2; i++) {
            glow(g, cx + i * w * 0.02, h * 0.40, 14, "#ffd36b", 0.9);
        }
        // tapete
        g.setFill(Color.web("#7a1f2a"));
        g.fillPolygon(new double[]{w * 0.38, w * 0.62, w * 0.80, w * 0.20},
                new double[]{floorY, floorY, h * 0.97, h * 0.97}, 4);
        g.setStroke(Color.web("#e0b64a"));
        g.setLineWidth(2);
        g.strokePolygon(new double[]{w * 0.38, w * 0.62, w * 0.80, w * 0.20},
                new double[]{floorY, floorY, h * 0.97, h * 0.97}, 4);
        // mesa central com livro aberto
        double tx = w * 0.36, ty = h * 0.58, tw = w * 0.28, th = h * 0.07;
        g.setFill(Color.web("#3a2412"));
        g.fillRect(tx + 8, ty + th, 10, h * 0.14);
        g.fillRect(tx + tw - 18, ty + th, 10, h * 0.14);
        g.setFill(Color.web("#6b4426"));
        g.fillRoundRect(tx, ty, tw, th, 8, 8);
        g.setFill(Color.web("#f6ecd0"));
        g.fillPolygon(new double[]{cx - w * 0.08, cx, cx + w * 0.08, cx + w * 0.08, cx, cx - w * 0.08},
                new double[]{ty - h * 0.05, ty - h * 0.03, ty - h * 0.05, ty, ty + 2, ty}, 6);
        g.setStroke(Color.web("#b89a5a"));
        g.setLineWidth(1);
        g.strokeLine(cx, ty - h * 0.03, cx, ty + 2);
        glow(g, cx, ty - h * 0.04, w * 0.08, "#ffe9a0", 0.35);
        // pó de luz / páginas
        drawPage(g, w * 0.18, h * 0.35, w * 0.025, -10);
        drawPage(g, w * 0.82, h * 0.40, w * 0.025, 12);
    }

    private static void drawPlain(GraphicsContext g, double w, double h) {
        g.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2a2238")), new Stop(1, Color.web("#15101d"))));
        g.fillRect(0, 0, w, h);
    }

    /** Hall de entrada: madeira quente, luz dourada, estantes, páginas flutuando e 4 portas. */
    public static void drawHall(GraphicsContext g, double w, double h) {
        double floorY = h * 0.72;

        // parede
        g.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#5b3a24")), new Stop(1, Color.web("#8a5a34"))));
        g.fillRect(0, 0, w, floorY);

        // chão
        g.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#6b4426")), new Stop(1, Color.web("#3b2414"))));
        g.fillRect(0, floorY, w, h - floorY);
        g.setStroke(Color.web("#2e1b0e", 0.6));
        g.setLineWidth(1.5);
        for (int i = 1; i < 8; i++) {
            double y = floorY + (h - floorY) * i / 8.0;
            g.strokeLine(0, y, w, y);
        }

        // estantes laterais
        drawShelf(g, w * 0.03, h * 0.10, w * 0.17, floorY - h * 0.10);
        drawShelf(g, w * 0.80, h * 0.10, w * 0.17, floorY - h * 0.10);

        // portas (norte, sul, leste, oeste)
        drawDoor(g, w * 0.5 - w * 0.06, h * 0.12, w * 0.12, h * 0.30);        // norte
        drawDoor(g, w * 0.5 - w * 0.07, floorY + h * 0.10, w * 0.14, h * 0.14); // sul (no chão)
        drawDoor(g, w * 0.24, h * 0.30, w * 0.08, h * 0.30);                   // oeste
        drawDoor(g, w * 0.68, h * 0.30, w * 0.08, h * 0.30);                   // leste

        // luz dourada
        g.setFill(new RadialGradient(0, 0, w * 0.5, h * 0.35, w * 0.55, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ffd36b", 0.35)), new Stop(1, Color.web("#ffd36b", 0))));
        g.fillRect(0, 0, w, h);

        // páginas flutuando
        double[][] pages = {{0.35, 0.18, -12}, {0.62, 0.22, 10}, {0.45, 0.50, 6}, {0.56, 0.58, -8}};
        for (double[] p : pages) {
            drawPage(g, w * p[0], h * p[1], w * 0.03, p[2]);
        }
    }

    private static void drawShelf(GraphicsContext g, double x, double y, double w, double h) {
        g.setFill(Color.web("#3a2314"));
        g.fillRect(x, y, w, h);
        int rows = 4;
        double rowH = h / rows;
        Color[] colors = {Color.web("#a33a3a"), Color.web("#3a6aa3"), Color.web("#4a8a4a"),
            Color.web("#c9a23a"), Color.web("#7a4aa3")};
        for (int r = 0; r < rows; r++) {
            double by = y + r * rowH;
            double bx = x + 4;
            int i = 0;
            while (bx < x + w - 8) {
                double bw = 6 + ((r * 7 + i * 5) % 7);
                double bh = rowH * (0.6 + ((r + i) % 4) * 0.08);
                g.setFill(colors[(r + i) % colors.length]);
                g.fillRect(bx, by + rowH - 5 - bh, bw, bh);
                bx += bw + 1.5;
                i++;
            }
            g.setFill(Color.web("#2a190d"));
            g.fillRect(x, by + rowH - 5, w, 5);
        }
        g.setStroke(Color.web("#2a190d"));
        g.setLineWidth(3);
        g.strokeRect(x, y, w, h);
    }

    private static void drawDoor(GraphicsContext g, double x, double y, double w, double h) {
        g.setFill(Color.web("#2a190d"));
        g.fillRoundRect(x - 4, y - 4, w + 8, h + 4, 14, 14);
        g.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#7a4a26")), new Stop(1, Color.web("#5a3418"))));
        g.fillRoundRect(x, y, w, h, 12, 12);
        g.setFill(Color.web("#e0b64a"));
        g.fillOval(x + w * 0.75, y + h * 0.55, Math.max(4, w * 0.08), Math.max(4, w * 0.08));
    }

    private static void drawPage(GraphicsContext g, double cx, double cy, double size, double angle) {
        g.save();
        g.translate(cx, cy);
        g.rotate(angle);
        g.setFill(Color.web("#f6ecd0"));
        g.fillRect(-size / 2, -size * 0.65, size, size * 1.3);
        g.setStroke(Color.web("#b89a5a"));
        g.setLineWidth(1);
        g.strokeRect(-size / 2, -size * 0.65, size, size * 1.3);
        for (int i = 0; i < 4; i++) {
            double ly = -size * 0.4 + i * size * 0.28;
            g.strokeLine(-size * 0.35, ly, size * 0.35, ly);
        }
        g.restore();
    }
}
