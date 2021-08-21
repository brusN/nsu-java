package org.nsu.minesweeper.main.commandExecutor;

import org.nsu.minesweeper.main.commandExecutor.commands.CLICommand;
import org.nsu.minesweeper.model.commands.CommandData;
import org.nsu.minesweeper.model.commands.CommandDescriptor;

import java.util.HashMap;

public class CommandExecutor {
    private final HashMap<String, CLICommand> commands;

    public CommandExecutor(String propertiesPath) {
        commands = CommandFactory.getCommandList(propertiesPath);
    }

    public CommandExecuteStatus execute(CommandData commandData, CommandDescriptor commandDescriptor) throws IllegalArgumentException {
        String commandName = commandDescriptor.getCommandName();
        if (commands.containsKey(commandName)) {
            return commands.get(commandName).doWork(commandData, commandDescriptor.getCommandArgs());
        } else {
            return CommandExecuteStatus.NOT_RECOGNIZED;
        }
    }
}

