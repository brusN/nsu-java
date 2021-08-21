package org.nsu.minesweeper.main.commandExecutor.commands;

import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.model.commands.CommandData;

public class ExitCommand extends CLICommand {

    @Override
    public boolean canExecute(CommandData commandData, String[] args) {
        return (args.length == 0);
    }

    @Override
    public CommandExecuteStatus doWork(CommandData commandData, String[] args) {
        if (canExecute(commandData, args)) {
            commandData.setScene(CurrentScene.EXIT);
            return CommandExecuteStatus.SUCCESS;
        } else
            return CommandExecuteStatus.FAIL;
    }
}
