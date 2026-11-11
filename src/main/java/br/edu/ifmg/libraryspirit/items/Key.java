package br.edu.ifmg.libraryspirit.items;

import br.edu.ifmg.libraryspirit.characters.Player;

/**
 * Chave que abre uma porta trancada. A porta confere se o jogador carrega
 * a chave pelo nome; a chave não some depois de abrir a porta.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class Key extends Item {

    /**
     * Cria uma chave.
     *
     * @param name nome da chave (ex.: "chave de bronze")
     * @param description texto que descreve a chave
     */
    public Key(String name, String description) {
        super(name, description);
    }

    /**
     * Usar a chave direto não abre nada: ela abre portas ao tentar passar por elas.
     *
     * @param player jogador que usa a chave
     * @return mensagem para o jogador
     */
    @Override
    public String use(Player player) {
        return "Basta tentar passar por uma porta trancada que a chave será usada.";
    }
}
