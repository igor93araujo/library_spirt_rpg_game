package br.edu.ifmg.libraryspirit.commands;

import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.boss.SealingStage;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.Room;

/**
 * Comando de reação a uma ameaça: "desviar", "pular", "abaixar" ou "esconder".
 * Cada palavra usa um objeto deste comando, criado com a ação correspondente.
 * Ação certa escapa sem dano; ação errada tira uma vida ou gasta o escudo.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class DodgeCommand implements Command {

    /**
     * Onde as mensagens são exibidas.
     */
    private final Output output;

    /**
     * Jogador que reage à ameaça.
     */
    private final Player player;

    /**
     * chefe final compartilhando pelos comandos do jogo.
     */
    private final GreatTome greatTome;

    /**
     * Ação que este comando representa (ex.: "pular").
     */
    private final String action;

    /**
     * Cria o comando de reação.
     *
     * @param output onde as mensagens serão exibidas
     * @param player jogador que reage
     * @param action ação que este comando representa
     */
    public DodgeCommand(Output output, Player player, String action, GreatTome greatTome) {
        this.output = output;
        this.player = player;
        this.action = action;
        this.greatTome = greatTome;
    }

    /**
     * Reage à ameaça atual da sala. O argumento é ignorado.
     *
     * @param argument ignorado
     * @return {@code true} se o jogador ficou sem vidas 'fora do chefe' e o jogo deve terminar,
     * {@code false} caso contrário
     */
    @Override
    public boolean execute(String argument) {
        Room room = player.getCurrentRoom();

        if (room.isBossRoom() && !greatTome.isDefeated()) {

            SealingStage stage = greatTome.getCurrentStage();

            if (stage.hasActiveThreat()) {
                Threat bossThreat = stage.getCurrentThreat();

                if (bossThreat.isCorrect(action)) {
                    stage.advanceRound();

                    if (stage.hasActiveThreat()) {
                        output.println(stage.getCurrentThreat().getDescription());
                    } else {
                        output.println(
                                "Você escapou por um triz!\n"
                                + "A página mágica começa a brilhar em suas mãos.\n"
                                + "A ameaça foi superada.\n"
                                + "Agora use a página correspondente para restaurar o selo!"
                        );
                    }

                } else if (player.takeDamage()) {
                    output.println("Ação errada! Vidas: " + player.getLives());
                } else {
                    output.println("Ação errada, mas o escudo absorveu o dano!");
                }

                if (!player.isAlive()) {
                    handleBossDefeat();
                }

                return false;
            }

            output.println("As ameaças terminaram. Use a página mágica correspondente.");
            return false;
        }
        if (!room.hasActiveThreat()) {
            output.println("Não há nada de que escapar agora.");
            return false;
        }

        Threat threat = room.getThreat();

        if (threat.isCorrect(action)) {
            room.resolveThreat();
            output.println("Você escapou a tempo!");
        } else if (player.takeDamage()) {
            output.println("Ação errada! A ameaça te atinge. Vidas: " + player.getLives() + ".");
        } else {
            output.println("Ação errada, mas o escudo absorveu o dano!");
        }

        if (!player.isAlive()) {
            output.println("Suas forças se esgotaram. Fim de jogo.");
            return true;
        }

        if (room.hasActiveThreat()) {
            output.println();
            output.println(room.getThreat().getDescription());
            output.println("Reaja: desviar, pular, abaixar ou esconder.");
        }
        return false;
    }
        /**
     * Trata a derrota no chefe: restaura as vidas e reinicia o confronto
     * na primeira etapa. As páginas continuam no inventário.
     */
    private void handleBossDefeat() {
        player.restoreLives();
        greatTome.resetStages();

        output.println();
        output.println("Suas forças se esgotam e o Grande Tomo te derruba...");
        output.println("Mas as páginas continuam com você. O confronto recomeça!");
        output.println("Vidas: " + player.getLives());
        output.println();
        output.println("Etapa atual: " + greatTome.getCurrentStage().getTheme());
        output.println(greatTome.getCurrentStage().getCurrentThreat().getDescription());
    }
}
