package org.nsu.minesweeper.view;
import org.nsu.minesweeper.model.eventStatuses.GameStatus;
import org.nsu.minesweeper.model.gamefield.CellState;
import org.nsu.minesweeper.model.gamemodel.GameModel;
import org.nsu.minesweeper.model.gamemodel.HighscoreTable;
import org.nsu.minesweeper.model.gamemodel.Player;

import java.io.*;
import java.util.Comparator;
import java.util.Objects;

public class ConsoleView {
    public void printEnterCommand() {
        System.out.println("Enter command: ");
    }

    public void printCommandExecutionFailed() {
        System.out.println("Previous command has failed execution!");
    }

    public void printCommandNotRecognized() {
        System.out.println("Command hasn't recognized!");
    }

    public void printMenu(String[] menu) {
        for (int menuID = 0; menuID < menu.length; ++menuID) {
            System.out.println((menuID + 1) + ". " + menu[menuID]);
        }
    }

    public void printEnterMenuID() {
        System.out.println("Choose option: ");
    }

    public void printIncorrectMenuID() {
        System.out.println("Incorrect number of option!");
    }

    private void clean() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("=== Minesweeper ===");
    }

    private char getSymbolOfCellState(CellState cellState, int value) {
        char characterState;
        switch (cellState) {
            case CLOSED: {
                characterState =  '.';
                break;
            }
            case OPENED: {
                characterState = value == 0 ? ' ' : Character.forDigit(value, 10);
                break;
            }
            case FLAGGED: {
                characterState = 'F';
                break;
            }
            case DEFUSED: {
                characterState = 'D';
                break;
            }
            case NOT_FOUNDED_BOMB: {
                characterState = '*';
                break;
            }
            case BLOWNED_BOMB: {
                characterState = 'X';
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + cellState);
        }
        return characterState;
    }

    private void printField(GameModel gameModel) {
        int fieldSizeX = gameModel.getFieldSizeX();
        int fieldSizeY = gameModel.getFieldSizeY();

        System.out.print("   ");
        for (int i = 0; i < fieldSizeX; ++i) {
            System.out.print(" " + i);
        }
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < fieldSizeY; ++i) {
            System.out.print(" —");
        }
        System.out.println();

        // Field print
        var gameField = gameModel.getGameField();
        for (int i = 0; i < fieldSizeY; ++i) {
            System.out.print(i + " |");
            for (int j = 0; j < fieldSizeX; ++j) {
                System.out.print(" " + getSymbolOfCellState(gameModel.getCellState(i, j), gameField[i][j].getNeighborsWithBombs()));
            }
            System.out.println();
        }
    }

    public void showMainMenu(String[] menu, Player playerProfile) {
        clean();
        printPlayerProfile(playerProfile);
        printMenu(menu);
    }

    private void printProgress(GameModel model) {
        System.out.println("Flags left: " + model.getFlagsLeft());
        System.out.println("Cells left: " + model.getCellsLeft());
    }

    public void showGame(GameModel gameModel) {
        clean();
        printProgress(gameModel);
        printField(gameModel);
    }
    public void printStats(GameModel model, long time) {
        GameStatus status = model.getStatus();
        if (status == GameStatus.LOSE) {
            System.out.println("You lose!");
        } else {
            System.out.println("You win!");
        }
        System.out.println("Your time: " + (Double) (time / 1e3));
    }

    public void showAbout() {
        clean();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("about.txt"))));
        try {
            String line = fileReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = fileReader.readLine();
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHighscores(HighscoreTable table) {
        clean();
        System.out.println("=== Top 10 players ===");
        printHighscoreTable(table);
    }

    public void printApplicationHasClosed() {
        System.out.println("Application has closed!");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printIncorrectNickname() {
        System.out.println("Incorrect nickname!");
    }

    public void printEnterNickname() {
        System.out.println("Enter your nickname: ");
    }

    public void showLogin() {
        clean();
        printEnterNickname();
    }

    private String getPlayerRecord(Player player) {
        long time = player.getRecordTime();
        return player.getNickname() + " -> " + (time == Long.MAX_VALUE ? "none" : (Double) (time / 1e3) + " sec");
    }

    private void printHighscoreTable(HighscoreTable table) {
        var playerList = table.getPlayersList();

        // Highscore table shows only first ten top players
        int totalProfiles = playerList.size();
        if (totalProfiles > 10) {
            totalProfiles = 10;
        }

        // Sorting by highscore
        playerList.sort(Comparator.comparingDouble(Player::getRecordTime));

        // Print top 10 players
        Double time;
        for (int i = 0; i < totalProfiles; ++i) {
            System.out.println((i + 1) + ". " + getPlayerRecord(playerList.get(i)));
        }
    }

    public void printPlayerProfile(Player playerProfile) {
        System.out.println("Current profile: " + playerProfile.getNickname());
        long time = playerProfile.getRecordTime();
        System.out.println("Record time: " + (time == Long.MAX_VALUE ? "none" : (Double) (time / 1e3)));
    }

}
