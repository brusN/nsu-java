package org.nsu.minesweeper.main.commandExecutor.commands;

import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.model.commands.CommandData;
import org.nsu.minesweeper.main.utils.StringSyntaxChecker;
import org.nsu.minesweeper.model.eventStatuses.GameStatus;

public class OpenCellCommand extends org.nsu.minesweeper.main.commandExecutor.commands.CLICommand {
    @Override
    public boolean canExecute(CommandData commandData, String[] args) {
        if (commandData.getModel().getStatus() != GameStatus.ALIVE || args.length != 2)
            return false;

        if (!(StringSyntaxChecker.isInteger(args[0]) && StringSyntaxChecker.isInteger(args[1])))
            return false;

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        return commandData.getModel().isFieldCell(x, y);
    }

    @Override
    public CommandExecuteStatus doWork(CommandData commandData, String[] args) {
        if (canExecute(commandData, args)) {
            commandData.getModel().openCell(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            return CommandExecuteStatus.SUCCESS;
        }
        return CommandExecuteStatus.FAIL;
    }
}
