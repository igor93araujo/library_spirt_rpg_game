package br.edu.ifmg.libraryspirit.boss;

import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.items.Theme;

import java.util.List;

public class SealingStage {
    /** Guarda o número da rodada atual do comfronto final.*/
    private int currentRound = 1;
    /** Guarda o tema da etapa.*/
    private  Theme theme;
    /** Guarda a lista de ameaças d etapa.*/
    private List<Threat> threats;
    /** Guarda o nome da pagina.*/
    private String requiredPage;

    public SealingStage(Theme theme,List<Threat> threats, String requiredPage){
        this.theme = theme;
        this.threats =threats;
        this.requiredPage = requiredPage;
    }

    /**
     * Retorna o tema desta etapa.
     *
     * @return tema da etapa
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Retorna o número da rodada atual do chefe final.
     *
     * @return número da rodada atual
     */
    public int getCurrentRound() {
        return currentRound;
    }

    public void resetRounds () {
        currentRound = 1;

        }
    /**
     * Retorna as três ameaças desta etapa.
     * @return lista de ameaças da etapa
     */
    public List<Threat> getThreats() {
        return threats;

    }
    public void advanceRound() {
        if (currentRound <= threats.size()) {
            currentRound++;
        }
    }
    /**
     * Verifica se todas as rodadas foram concluídas.
     * @return true se todas as ameaças foram enfrentadas
     */
    public boolean isCompleted() {
        return currentRound > threats.size();
    }
    /**
     * Retorna a ameaça da rodada atual.
     *
     * @return ameaça atual ou null se as rodadas terminaram
     */
    public Threat getCurrentThreat() {
        if (isCompleted()) {
            return null;
        }
        return threats.get(currentRound - 1);
    }
    /**
     * Verifica se ainda existem ameaças na etapa.
     *
     * @return true se houver ameaças restantes
     */
    public boolean hasActiveThreat() {
        return currentRound <= threats.size();
    }
    /**
     * Retorna o nome da página mágica necessária
     * para concluir esta etapa.
     *
     * @return nome da página exigida
     */
    public String getRequiredPage() {
        return requiredPage;
    }

}
