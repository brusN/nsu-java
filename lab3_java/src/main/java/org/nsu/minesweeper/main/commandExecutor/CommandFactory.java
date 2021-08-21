package org.nsu.minesweeper.main.commandExecutor;

import org.nsu.minesweeper.main.commandExecutor.commands.CLICommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class CommandFactory {
    static HashMap<String, CLICommand> getCommandList(String propertiesFilePath) {
        HashMap<String, CLICommand> commands = new HashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(CommandFactory.class.getClassLoader().getResourceAsStream(propertiesFilePath));
            commands = new HashMap<>();
            for (var nextProperty : properties.entrySet()) {
                String classPath = nextProperty.getKey().toString();
                String commandSyntax = nextProperty.getValue().toString();
                CLICommand newCommand = (CLICommand) Class.forName("org.nsu.minesweeper.main.commandExecutor.commands." + classPath).newInstance();
                commands.put(commandSyntax, newCommand);
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return commands;
    }
}
