package org.nsu.minesweeper.main.commandExecutor.commands;

import org.nsu.minesweeper.main.providers.GamePresetsProvider;
import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.model.commands.CommandData;
import org.nsu.minesweeper.model.eventStatuses.GameMode;
import org.nsu.minesweeper.model.gamemodel.GamePreset;

public class ChangeGameModeCommand extends org.nsu.minesweeper.main.commandExecutor.commands.CLICommand {

    @Override
    public boolean canExecute(CommandData commandData, String[] args) {
        if (args.length != 1)
            return false;
        String fieldTypeName = args[0].toLowerCase();
        return (fieldTypeName.equals("easy") || fieldTypeName.equals("normal") || fieldTypeName.equals("hard"));
    }

    @Override
    public CommandExecuteStatus doWork(CommandData commandData, String[] args) {
        String newFieldTypeName = args[0].toLowerCase();
        if (canExecute(commandData, args)) {
            GamePreset newPreset;
            switch (newFieldTypeName) {
                case "easy": {
                    newPreset = GamePresetsProvider.getGamePreset(GameMode.EASY);
                    break;
                }
                case "normal": {
                    newPreset = GamePresetsProvider.getGamePreset(GameMode.NORMAL);
                    break;
                }
                case "hard": {
                    newPreset = GamePresetsProvider.getGamePreset(GameMode.HARD);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown game mode!");
                }
            }
            commandData.getModel().changeGameMode(newPreset);
            return CommandExecuteStatus.SUCCESS;
        } else
            return CommandExecuteStatus.FAIL;
    }
}
