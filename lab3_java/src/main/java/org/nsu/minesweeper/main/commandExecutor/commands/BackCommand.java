package org.nsu.minesweeper.main.commandExecutor.commands;

import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.model.commands.CommandData;
import org.nsu.minesweeper.model.eventStatuses.GameStatus;

public class BackCommand extends CLICommand {
    @Override
    public boolean canExecute(CommandData commandData, String[] args) {
        return (args.length == 0);
    }

    @Override
    public CommandExecuteStatus doWork(CommandData commandData, String[] args) {
        if (canExecute(commandData, args)) {
            commandData.setScene(CurrentScene.MAIN_MENU);
            if (commandData.getModel().getStatus() != GameStatus.NOT_STARTED) {
                commandData.getModel().stopGame();
            }
            return CommandExecuteStatus.SUCCESS;
        } else {
            return CommandExecuteStatus.FAIL;
        }
    }
}
