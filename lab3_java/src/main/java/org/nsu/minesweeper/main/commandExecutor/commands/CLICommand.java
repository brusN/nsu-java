package org.nsu.minesweeper.main.commandExecutor.commands;

import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.model.commands.CommandData;

abstract public class CLICommand {
    abstract public boolean canExecute(CommandData commandData, String[] args);
    abstract public CommandExecuteStatus doWork(CommandData commandData, String[] args);
}
