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
 * Cada etapa possui uma ameaça e exige
 * uma página mágica correspondente.
 */
public class GreatTome {

    private List<SealingStage> stages;

    private int currentStageIndex = 0;

    /**
     * Inicializa as três etapas do Grande Tomo.
     */
    public GreatTome() {

        stages = new ArrayList<>();

        // ETAPA 1 - TERROR: O ABISMO

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
                        + "O chão começa a rachar sob seus pés!\n"
                        + "Das profundezas, uma escuridão parece\n"
                        + "querer engolir tudo ao redor.\n\n"
                        + "O chão está desmoronando!\n"
                        + "Reaja antes que seja tarde!",
                "pular"
        ));

        SealingStage terrorStage = new SealingStage(
                Theme.HORROR,
                horrorThreats,
                "terror"
        );

        stages.add(terrorStage);

        // ETAPA 2 - AVENTURA: O MAPA IMPOSSÍVEL

        List<Threat> adventureThreats = new ArrayList<>();

        adventureThreats.add(new Threat(
                "AVENTURA - O MAPA IMPOSSÍVEL\n\n"
                        + "Mas a tranquilidade dura pouco...\n"
                        + "Um mapa antigo se desenrola no ar.\n"
                        + "Montanhas, oceanos e cidades surgem\n"
                        + "em suas linhas encantadas.\n"
                        + "De repente, os caminhos se embaralham!\n"
                        + "Uma muralha de pergaminhos se ergue\n"
                        + "e avança como uma onda pelo salão.\n\n"
                        + "Ela está prestes a atingir você!\n"
                        + "Reaja!",
                "desviar"
        ));

        SealingStage adventureStage = new SealingStage(
                Theme.ADVENTURE,
                adventureThreats,
                "aventura"
        );

        stages.add(adventureStage);

        // ETAPA 3 - FICÇÃO CIENTÍFICA:
        // A ANOMALIA GRAVITACIONAL

        List<Threat> sciFiThreats = new ArrayList<>();

        sciFiThreats.add(new Threat(
                "FICÇÃO CIENTÍFICA - A ANOMALIA GRAVITACIONAL\n\n"
                        + "As luzes começam a piscar.\n"
                        + "Um som metálico rompe o silêncio.\n"
                        + "As letras se desprendem das páginas\n"
                        + "e começam a flutuar pelo salão.\n\n"
                        + "Uma voz metálica anuncia:\n"
                        + "\"ANOMALIA GRAVITACIONAL DETECTADA!\"\n\n"
                        + "O espaço ao seu redor se distorce.\n"
                        + "Um arco de energia comprime o ar\n"
                        + "e avança na altura da sua cabeça!\n\n"
                        + "Reaja!",
                "abaixar"
        ));

        SealingStage sciFiStage = new SealingStage(
                Theme.SCI_FI,
                sciFiThreats,
                "ficcao"
        );

        stages.add(sciFiStage);
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