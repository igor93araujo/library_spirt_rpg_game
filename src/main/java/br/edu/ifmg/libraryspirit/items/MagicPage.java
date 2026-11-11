package br.edu.ifmg.libraryspirit.items;

import br.edu.ifmg.libraryspirit.characters.Player;

/**
 * Página mágica de um tema. São necessárias as três para abrir a Sala
 * de Leitura Central e selar o Grande Tomo.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class MagicPage extends Item {

    /** Tema da página. */
    private final Theme theme;

    /**
     * Cria a página mágica de um tema.
     *
     * @param theme tema da página
     */
    public MagicPage(Theme theme) {
        super("pagina do " + theme.getDisplayName(),
                "Uma página mágica do tema " + theme.getDisplayName()
                + ", que brilha com uma luz fraca.");
        this.theme = theme;
    }

    /**
     * Retorna o tema da página.
     *
     * @return o tema
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Usa a página. Fora do combate contra o Grande Tomo ela não tem efeito.
     *
     * @param player jogador que usa a página
     * @return mensagem para o jogador
     */
    @Override
    public String use(Player player) {
        return "A página brilha, mas ainda não é o momento de usá-la.";
    }
}
