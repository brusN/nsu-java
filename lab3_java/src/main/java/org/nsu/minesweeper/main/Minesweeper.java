package org.nsu.minesweeper.main;

import org.nsu.minesweeper.controller.ConsoleController;
import org.nsu.minesweeper.main.providers.OptionsProvider;
import org.nsu.minesweeper.view.ApplicationGUI;

public class Minesweeper {
    public static void main(String[] args) {
        OptionsProvider optionsProvider = new OptionsProvider(args);

        if (optionsProvider.launchWithGUI()) {
            ApplicationGUI.launch();
        } else {
            ConsoleController consoleController = new ConsoleController();
            consoleController.launch();
        }
    }
}
