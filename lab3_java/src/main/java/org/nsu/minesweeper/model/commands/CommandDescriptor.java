package org.nsu.minesweeper.model.commands;

public class CommandDescriptor {
    private String commandName;
    private String[] commandArgs;

    public CommandDescriptor(String commandName, String [] commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getCommandArgs() {
        return commandArgs;
    }

    public int numberArgs() {
        return commandArgs.length;
    }
}
