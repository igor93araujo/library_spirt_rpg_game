package br.edu.ifmg.libraryspirit.game;

import br.edu.ifmg.libraryspirit.boss.GreatTome;
import br.edu.ifmg.libraryspirit.characters.Player;
import br.edu.ifmg.libraryspirit.characters.Threat;
import br.edu.ifmg.libraryspirit.commands.AnswerCommand;
import br.edu.ifmg.libraryspirit.commands.DodgeCommand;
import br.edu.ifmg.libraryspirit.commands.GoCommand;
import br.edu.ifmg.libraryspirit.commands.HelpCommand;
import br.edu.ifmg.libraryspirit.commands.InventoryCommand;
import br.edu.ifmg.libraryspirit.commands.LookCommand;
import br.edu.ifmg.libraryspirit.commands.QuitCommand;
import br.edu.ifmg.libraryspirit.commands.StatusCommand;
import br.edu.ifmg.libraryspirit.commands.TakeCommand;
import br.edu.ifmg.libraryspirit.commands.UseCommand;
import br.edu.ifmg.libraryspirit.ui.Output;
import br.edu.ifmg.libraryspirit.world.WorldBuilder;

/**
 * Monta as peças do jogo (jogador, chefe e comandos), para que o console
 * e a interface gráfica usem exatamente a mesma configuração.
 *
 * @author Grupo O Espírito da Biblioteca
 * @version 1.0
 */
public class GameSetup {

    private final Player player;
    private final GreatTome greatTome;
    private final CommandWords commandWords;

    private GameSetup(Player player, GreatTome greatTome, CommandWords commandWords) {
        this.player = player;
        this.greatTome = greatTome;
        this.commandWords = commandWords;
    }

    /**
     * Cria um jogo novo, com todos os comandos registrados.
     *
     * @param output destino das mensagens dos comandos
     * @return a configuração pronta
     */
    public static GameSetup create(Output output) {
        Player player = new Player(new WorldBuilder().build());
        GreatTome greatTome = new GreatTome();

        CommandWords commandWords = new CommandWords();
        commandWords.register("ir", new GoCommand(output, player, greatTome));
        commandWords.register("olhar", new LookCommand(output, player));
        commandWords.register("pegar", new TakeCommand(output, player));
        commandWords.register("inventario", new InventoryCommand(output, player));
        commandWords.register("responder", new AnswerCommand(output, player));
        commandWords.register("usar", new UseCommand(output, player, greatTome));
        commandWords.register("status", new StatusCommand(output, player));

        for (String action : Threat.VALID_ACTIONS) {
            commandWords.register(action, new DodgeCommand(output, player, action, greatTome));
        }

        commandWords.register("ajuda", new HelpCommand(output, commandWords));
        commandWords.register("sair", new QuitCommand(output));

        return new GameSetup(player, greatTome, commandWords);
    }

    /** @return o jogador */
    public Player getPlayer() {
        return player;
    }

    /** @return o chefe final */
    public GreatTome getGreatTome() {
        return greatTome;
    }

    /** @return os comandos registrados */
    public CommandWords getCommandWords() {
        return commandWords;
    }
}
