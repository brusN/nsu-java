package org.nsu.minesweeper.controller;

import org.nsu.minesweeper.main.commandExecutor.CommandExecuteStatus;
import org.nsu.minesweeper.main.commandExecutor.CommandExecutor;
import org.nsu.minesweeper.model.commands.CommandData;
import org.nsu.minesweeper.model.commands.CommandDescriptor;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.model.eventStatuses.GameMode;
import org.nsu.minesweeper.model.eventStatuses.GameStatus;
import org.nsu.minesweeper.model.eventStatuses.UserAnswer;
import org.nsu.minesweeper.main.providers.GamePresetsProvider;
import org.nsu.minesweeper.main.utils.StringSyntaxChecker;
import org.nsu.minesweeper.model.gamemodel.GameModel;
import org.nsu.minesweeper.model.gamemodel.HighscoreTable;
import org.nsu.minesweeper.model.gamemodel.Player;
import org.nsu.minesweeper.model.timer.ThreadSafeTimerCounter;
import org.nsu.minesweeper.view.ConsoleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ConsoleController {
    private CurrentScene location;
    private Player playerProfile;
    private final GameModel gameModel;
    private final ConsoleView consoleView;
    private Timer timer;
    private final ThreadSafeTimerCounter timerCounter;
    private final HighscoreTable highscoreTable;
    private final CommandExecutor commandExecutor;
    private final CommandData commandData;
    private final String[] mainMenu = {"Start new game", "About", "Highscores", "Exit"};
    private final String[] acceptMenu = {"Yes", "No"};

    public void startTimer() {
        resetTime();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCounter.increment(10);
            }
        }, 0, 10);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void resetTime() {
        timerCounter.reset();
    }

    public ConsoleController() {
        highscoreTable = new HighscoreTable("src/main/java/org/nsu/minesweeper/database/highscores.csv");
        gameModel = new GameModel(GamePresetsProvider.getGamePreset(GameMode.EASY));
        consoleView = new ConsoleView();
        commandExecutor = new CommandExecutor("commands.properties");
        commandData = new CommandData(gameModel);
        timerCounter = new ThreadSafeTimerCounter();
    }

    class SceneSwitcher {
        public void launch() {
            CurrentScene nextScene = CurrentScene.MAIN_MENU;
            while (nextScene != CurrentScene.EXIT) {
                switch (nextScene) {
                    case MAIN_MENU: {
                        nextScene = ConsoleController.this.switchToMainMenu();
                        break;
                    }
                    case GAME: {
                        nextScene = ConsoleController.this.startNewGame();
                        break;
                    }
                    case ABOUT: {
                        nextScene = ConsoleController.this.switchToAbout();
                        break;
                    }
                    case HIGHSCORES: {
                        nextScene = ConsoleController.this.switchToHighscores();
                        break;
                    }
                }
            }
            ConsoleController.this.exitFromApplication();
        }
    }

    // Returns rode line from console
    private String readStringFromConsole() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        try {
            line = consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    // Reads line from console and returns command descriptor
    private CommandDescriptor getCommandFromConsole() {
        consoleView.printEnterCommand();
        String[] commandLineArguments = readStringFromConsole().split(" ");
        return new CommandDescriptor(commandLineArguments[0], Arrays.copyOfRange(commandLineArguments, 1, commandLineArguments.length));
    }

    // Executes command which describes by command descriptor
    private CommandExecuteStatus executeCommand(CommandDescriptor commandDescriptor) {
        CommandExecuteStatus status = commandExecutor.execute(commandData, commandDescriptor);
        switch (status) {
            case FAIL: {
                consoleView.printCommandExecutionFailed();
                break;
            }
            case NOT_RECOGNIZED: {
                consoleView.printCommandNotRecognized();
                break;
            }
        }
        return status;
    }

    // Retries input until a number is entered
    private int getOptionID() {
        consoleView.printEnterMenuID();
        String line = readStringFromConsole();
        while (!StringSyntaxChecker.isInteger(line)) {
            consoleView.printIncorrectMenuID();
            consoleView.printEnterMenuID();
            line = readStringFromConsole();
        }
        return Integer.parseInt(line);
    }

    // Gets the menu id and checks if the selection is available
    private int getAnswerFromUser(String[] menu) {
        int optionID = getOptionID();
        int menuMaxID = menu.length;
        while (optionID < 1 || optionID > menuMaxID) {
            consoleView.printIncorrectMenuID();
            optionID = getOptionID();
        }
        return optionID;
    }

    // Starts poll and receives a response from the user
    private UserAnswer startPoll(String pollMessage) {
        consoleView.printMessage(pollMessage);
        consoleView.printMenu(acceptMenu);
        int answer = getAnswerFromUser(acceptMenu);
        if (answer == 1) {
            return UserAnswer.YES;
        } else
            return UserAnswer.NO;
    }

    // Requests user input until the correct nickname is entered according to the template
    private String getPlayerNickname() {
        String nicknameRegex = "^[^0-9][^ @#]+$";
        String playerNickname = readStringFromConsole();
        while (!playerNickname.matches(nicknameRegex)) {
            consoleView.printIncorrectNickname();
            consoleView.printEnterNickname();
            playerNickname = readStringFromConsole();
        }
        return playerNickname;
    }

    // Login by nickname
    private void login() {
        consoleView.showLogin();
        String playerNickname = getPlayerNickname();
        playerProfile = highscoreTable.findPlayer(playerNickname);
        if (playerProfile == null) {
            playerProfile = new Player(playerNickname, Long.MAX_VALUE);
            highscoreTable.addPlayer(playerProfile);
        }
    }

    // Launches application
    public void launch() {
        login();
        location = CurrentScene.MAIN_MENU;
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.launch();
    }

    public CurrentScene switchToMainMenu() {
        location = CurrentScene.MAIN_MENU;
        consoleView.showMainMenu(mainMenu, playerProfile);
        try {
            switch (getAnswerFromUser(mainMenu)) {
                case 1: {
                    return CurrentScene.GAME;
                }
                case 2: {
                    return CurrentScene.ABOUT;
                }
                case 3: {
                    return CurrentScene.HIGHSCORES;
                }
                case 4: {
                    return CurrentScene.EXIT;
                }
                default:
                    throw new IllegalArgumentException("Illegal option ID of org.nsu.minesweeper.main menu");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return CurrentScene.UNKNOWN_SCENE;
    }

    private void updateHighscoreTable(long newTotalTime) {
        long previousTime = playerProfile.getRecordTime();
        if (newTotalTime < previousTime || (newTotalTime == previousTime && newTotalTime < playerProfile.getRecordTime())) {
            playerProfile.updateRecord(newTotalTime);
        }
        highscoreTable.rewriteTable();
    }

    public CurrentScene startNewGame() {
        location = CurrentScene.GAME;
        gameModel.newGame();
        CommandExecuteStatus executeCommandStatus;

        // Game process
        startTimer();
        while (gameModel.getStatus() == GameStatus.ALIVE) {
            consoleView.showGame(gameModel);
            executeCommandStatus = executeCommand(getCommandFromConsole());
            while (executeCommandStatus != CommandExecuteStatus.SUCCESS) {
                executeCommandStatus = executeCommand(getCommandFromConsole());
            }
        }
        stopTimer();
        var totalTime = timerCounter.getValue();

        // If game ended after win or lose
        GameStatus gameStatus = gameModel.getStatus();
        if (gameStatus != GameStatus.NOT_STARTED) {

            // Game results processing
            if (gameStatus == GameStatus.WIN) {
                updateHighscoreTable(totalTime);
            }

            // Print stats
            gameModel.showBombs();
            consoleView.showGame(gameModel);
            consoleView.printStats(gameModel, totalTime);

            // Start pool after lose or win game
            if (startPoll("Do you want play again?") == UserAnswer.YES) {
                return CurrentScene.GAME;
            } else {
                return CurrentScene.MAIN_MENU;
            }
        }

        // If game ended after command back
        return CurrentScene.MAIN_MENU;
    }

    public CurrentScene switchToAbout() {
        location = CurrentScene.ABOUT;
        consoleView.showAbout();
        while (location != CurrentScene.MAIN_MENU) {
            executeCommand(getCommandFromConsole());
            location = commandData.getScene();
        }
        return CurrentScene.MAIN_MENU;
    }

    public CurrentScene switchToHighscores() {
        location = CurrentScene.HIGHSCORES;
        consoleView.showHighscores(highscoreTable);
        while (location != CurrentScene.MAIN_MENU) {
            executeCommand(getCommandFromConsole());
            location = commandData.getScene();
        }
        return CurrentScene.MAIN_MENU;
    }

    public void exitFromApplication() {
        highscoreTable.rewriteTable();
        consoleView.printApplicationHasClosed();
        System.exit(0);
    }
}
