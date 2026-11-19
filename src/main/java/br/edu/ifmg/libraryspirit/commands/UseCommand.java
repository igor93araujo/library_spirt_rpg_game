package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.boss.SealingStage;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.items.Item;
import br.edu.ifmg.libraryspirit.items.MagicPage;
import br.edu.ifmg.libraryspirit.ui.Output;

/**
 * Comando "usar": usa um item do inventário. Ex.: "usar marcador de vida".
 * Chama {@code item.use(player)} (polimorfismo), sem {@code instanceof}.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class UseCommand implements Command {

    /**
     * Onde as mensagens são exibidas.
     */
    private final Output output;

    /**
     * Jogador que vai usar o item.
     */
    private final Player player;

    /**
     * Chefe final compartilhado pelos comando.
     */
    private final GreatTome greatTome;

    /**
     * Cria o comando de usar.
     *
     * @param output onde as mensagens serão exibidas
     * @param player jogador que vai usar o item
     */
    public UseCommand(Output output, Player player, GreatTome greatTome) {
        this.output = output;
        this.player = player;
        this.greatTome = greatTome;
    }

    public UseCommand(Output output, Player player) {
        this(output, player, null);
    }

    /**
     * Usa o item informado e, se ele for consumível, tira do inventário.
     *
     * @param argument nome do item
     * @return sempre {@code false}
     */
    @Override
    public boolean execute(String argument) {
        if (argument.isEmpty()) {
            output.println("Usar o quê?");
            return false;
        }
        Item item = player.getItem(argument);
        if (item == null) {
            output.println("Você não tem nenhum(a) " + argument + ".");
            return false;
        }
        if (item instanceof MagicPage
                && greatTome != null
                && greatTome.getCurrentStage() != null) {

            SealingStage stage = greatTome.getCurrentStage();

            // Verifica se a ameaça foi superada.
            if (!stage.isCompleted()) {
                output.println(
                        "A ameaça ainda não foi superada! "
                        + "Supere o desafio antes de usar a página."
                );
                return false;
            }

            // Verifica se a página corresponde à etapa atual.
            if (!item.getName().equals(
                    "pagina do " + stage.getRequiredPage())) {

                output.println(
                        "Esta não é a página correta para esta etapa!"
                );
                return false;
            }

            // Mensagens de selamento.
            if (stage.getRequiredPage().equals("terror")) {

                output.println(
                        "A página do Terror voa de suas mãos\n"
                        + "e se encaixa no Grande Tomo.\n"
                        + "Um brilho vermelho percorre sua capa.\n\n"
                        + "PRIMEIRO SELO RESTAURADO!"
                );

            } else if (stage.getRequiredPage().equals("aventura")) {

                output.println(
                        "A página de Aventura retorna ao Grande Tomo.\n"
                        + "Um clarão dourado ilumina a biblioteca.\n\n"
                        + "SEGUNDO SELO RESTAURADO!"
                );

            } else if (stage.getRequiredPage().equals("ficcao")) {

                output.println(
                        "A última página retorna ao Grande Tomo.\n"
                        + "A anomalia gravitacional desaparece.\n\n"
                        + "TERCEIRO SELO RESTAURADO!"
                );
            }

            /**
             * Avança para a próxima etapa.
             */
            greatTome.advanceStage();

            if (greatTome.isDefeated()) {
                showVictory();
                return true;
            }

            /**
             * Mostra a próxima ameaça automaticamente.
             */
            SealingStage nextStage = greatTome.getCurrentStage();

            if (nextStage != null
                    && nextStage.getCurrentThreat() != null) {

                output.println(
                        nextStage.getCurrentThreat().getDescription()
                );
            }
            return false;
        }
        output.println(item.use(player));
        if (item.isConsumable()) {
            player.removeItem(item.getName());
        }
        return false;
    }

    /**
     * Mostra o texto final do jogo quando as três etapas são seladas.
     */
    private void showVictory() {
        output.println();
        output.println("=== O GRANDE TOMO ESTÁ SELADO! ===");
        output.println("As três páginas voltaram ao lugar e o silêncio retorna à biblioteca.");
        output.println("As histórias deixam de escapar e voltam a dormir entre as capas.");
        output.println("Você cumpriu seu dever de espírito guardião.");
        output.println();
        output.println("PARABÉNS, VOCÊ VENCEU!");
    }
}
