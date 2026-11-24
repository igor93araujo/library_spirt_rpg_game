package br.edu.ifmg.libraryspirit.ui.gui;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.image.Image;

/**
 * Carrega imagens PNG opcionais de {@code /images} no classpath.
 * Se o arquivo não existir (ou falhar ao carregar), devolve vazio e o chamador
 * usa a arte desenhada por código. Nunca lança exceção por arquivo ausente.
 */
public final class ImageLoader {

    private static final String BASE = "/images/";
    private static final Map<String, Optional<Image>> CACHE = new HashMap<>();

    private ImageLoader() {
    }

    /** Nome do arquivo (sem extensão) de uma cena, ex.: {@code scene_hall}. */
    public static String sceneName(SceneId id) {
        return "scene_" + id.name().toLowerCase();
    }

    /** Imagem de fundo da sala, se existir {@code images/scene_<id>.png}. */
    public static Optional<Image> scene(SceneId id) {
        return id == null ? Optional.empty() : load(sceneName(id));
    }

    /** Imagem de ameaça, se existir {@code images/threat_<nome>.png}. */
    public static Optional<Image> threat(String kind) {
        return kind == null ? Optional.empty() : load("threat_" + kind.toLowerCase());
    }

    /** Imagem de item, se existir {@code images/item_<nome>.png}. */
    public static Optional<Image> item(String name) {
        return name == null ? Optional.empty() : load("item_" + name.toLowerCase());
    }

    /** Carrega {@code images/<name>.png}; vazio se ausente ou inválido. */
    public static synchronized Optional<Image> load(String name) {
        return CACHE.computeIfAbsent(name, n -> {
            try (InputStream in = ImageLoader.class.getResourceAsStream(BASE + n + ".png")) {
                if (in == null) {
                    return Optional.empty();
                }
                Image img = new Image(in);
                return img.isError() ? Optional.empty() : Optional.of(img);
            } catch (Exception e) {
                return Optional.empty();
            }
        });
    }
}
