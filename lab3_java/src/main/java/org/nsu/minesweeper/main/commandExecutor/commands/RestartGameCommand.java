package org.nsu.minesweeper.main.commandExecutor.commands;

import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.model.commands.CommandData;
import org.nsu.minesweeper.model.eventStatuses.GameStatus;

public class RestartGameCommand extends CLICommand {
    @Override
    public boolean canExecute(CommandData commandData, String[] args) {
        if (commandData.getModel().getStatus() == GameStatus.NOT_STARTED) // Плохое условие
            return false;

        return (args.length == 0);
    }

    @Override
    public CommandExecuteStatus doWork(CommandData commandData, String[] args) {
        if (canExecute(commandData, args)) {
            commandData.getModel().newGame();
            return CommandExecuteStatus.SUCCESS;
        }
        return CommandExecuteStatus.FAIL;
    }
}
