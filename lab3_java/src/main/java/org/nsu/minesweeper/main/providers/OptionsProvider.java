package org.nsu.minesweeper.main.providers;

import org.apache.commons.cli.*;

public class OptionsProvider {
    Options options;
    private boolean guiView = true;

    private Options getAppOptionsList() {
        Options optionsList = new Options();
        optionsList.addOption("c", "console", false, "Launch app in console without GUI");
        return optionsList;
    }

    public OptionsProvider(String[] args) {
        options = getAppOptionsList();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("c")) {
                guiView = false;
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
    }

    public boolean launchWithGUI() {
        return guiView;
    }
}
