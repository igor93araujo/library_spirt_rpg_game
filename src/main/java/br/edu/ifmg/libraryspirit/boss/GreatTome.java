package br.edu.ifmg.libraryspirit.boss;

import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.items.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o Grande Tomo, chefe final do jogo.
 *
 * O confronto possui três etapas:
 * Terror, Aventura e Ficção Científica.
 *
 * Cada etapa possui três rodadas de ameaças e exige
 * uma página mágica correspondente.
 */
public class GreatTome {

    private List<SealingStage> stages;

    private int currentStageIndex = 0;

    /**
     * Inicializa as três etapas do Grande Tomo, cada uma com três rodadas.
     */
    public GreatTome() {

        stages = new ArrayList<>();

        // ETAPA 1 - TERROR: sombras e livros de feitiços voadores

        List<Threat> horrorThreats = new ArrayList<>();

        horrorThreats.add(new Threat(
                "Um silêncio estranho toma conta da biblioteca.\n"
                        + "De repente, as páginas do Grande Tomo\n"
                        + "começam a virar sozinhas.\n"
                        + "\"Três histórias foram corrompidas.\n"
                        + "Três páginas deverão restaurar o equilíbrio.\n"
                        + "Mas será você capaz de enfrentar\n"
                        + "o que existe dentro delas?\"\n\n"
                        + "Uma gargalhada sombria ecoa pela biblioteca.\n"
                        + "Uma sombra escorre do livro e desliza\n"
                        + "pelo chão, vindo em sua direção...\n"
                        + "Reaja!",
                "esconder"
        ));
        horrorThreats.add(new Threat(
                "Um livro de feitiços se solta do Grande Tomo\n"
                        + "e voa direto na altura da sua cabeça!\n"
                        + "Reaja!",
                "abaixar"
        ));
        horrorThreats.add(new Threat(
                "Sombras e livros de feitiços giram juntos\n"
                        + "e investem contra você de todos os lados!\n"
                        + "Reaja!",
                "desviar"
        ));

        stages.add(new SealingStage(Theme.HORROR, horrorThreats, "terror"));

        // ETAPA 2 - AVENTURA: raízes, cipós e tigres de origami

        List<Threat> adventureThreats = new ArrayList<>();

        adventureThreats.add(new Threat(
                "AVENTURA - O MAPA IMPOSSÍVEL\n\n"
                        + "Um mapa antigo se desenrola no ar e a sala\n"
                        + "vira uma floresta densa.\n"
                        + "Raízes brotam do chão sob seus pés!\n"
                        + "Reaja!",
                "pular"
        ));
        adventureThreats.add(new Threat(
                "Cipós grossos descem das árvores de papel,\n"
                        + "balançando bem na altura da sua cabeça!\n"
                        + "Reaja!",
                "abaixar"
        ));
        adventureThreats.add(new Threat(
                "Um tigre de origami salta do mapa\n"
                        + "em sua direção, de garras abertas!\n"
                        + "Reaja!",
                "desviar"
        ));

        stages.add(new SealingStage(Theme.ADVENTURE, adventureThreats, "aventura"));

        // ETAPA 3 - FICÇÃO CIENTÍFICA: lasers e robôs

        List<Threat> sciFiThreats = new ArrayList<>();

        sciFiThreats.add(new Threat(
                "FICÇÃO CIENTÍFICA - A ANOMALIA GRAVITACIONAL\n\n"
                        + "As luzes começam a piscar e uma voz metálica anuncia:\n"
                        + "\"ANOMALIA GRAVITACIONAL DETECTADA!\"\n"
                        + "Um feixe de laser de dados corrompidos\n"
                        + "varre a sala!\n"
                        + "Reaja!",
                "desviar"
        ));
        sciFiThreats.add(new Threat(
                "Um robô de manutenção com defeito surge\n"
                        + "dos computadores, com os olhos vermelhos\n"
                        + "procurando por você...\n"
                        + "Reaja!",
                "esconder"
        ));
        sciFiThreats.add(new Threat(
                "Um feixe de laser rasteiro corta o salão\n"
                        + "rente ao chão, e um robô avança logo atrás!\n"
                        + "Reaja!",
                "pular"
        ));

        stages.add(new SealingStage(Theme.SCI_FI, sciFiThreats, "ficcao"));
    }

    /**
     * Retorna as três etapas do chefe final.
     */
    public List<SealingStage> getStages() {
        return stages;
    }

    /**
     * Retorna a etapa atual ou null
     * quando todas estiverem concluídas.
     */
    public SealingStage getCurrentStage() {

        if (currentStageIndex >= stages.size()) {
            return null;
        }

        return stages.get(currentStageIndex);
    }

    /**
     * Verifica se o Grande Tomo foi derrotado.
     */
    public boolean isDefeated() {
        return currentStageIndex >= stages.size();
    }

    /**
     * Avança para a próxima etapa quando
     * a etapa atual estiver concluída.
     */
    public void advanceStage() {

        SealingStage currentStage = getCurrentStage();

        if (currentStage != null
                && currentStage.isCompleted()) {

            currentStageIndex++;
        }
    }

    /**
     * Reinicia o confronto na primeira etapa.
     */
    public void resetStages() {

        currentStageIndex = 0;

        for (SealingStage stage : stages) {
            stage.resetRounds();
        }
    }
}