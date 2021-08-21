package org.nsu.minesweeper.model.gamemodel;

import org.nsu.minesweeper.model.eventStatuses.GameStatus;
import org.nsu.minesweeper.model.gamefield.CellState;
import org.nsu.minesweeper.model.gamefield.FieldCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.nsu.minesweeper.model.gamefield.CellState.*;

public class GameModel {
    private GameStatus status;
    private GamePreset gamePreset;
    private FieldCell[][] gameField;
    private boolean[][] bombsField;
    private int flagsLeft;
    private int cellsLeft;

    private void initField() {
        int fieldSizeX = gamePreset.getFieldSizeX();
        int fieldSizeY = gamePreset.getFieldSizeY();

        gameField = new FieldCell[fieldSizeX][fieldSizeY];
        bombsField = new boolean[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeX; ++i) {
            for (int j = 0; j < fieldSizeY; ++j) {
                gameField[i][j] = new FieldCell(i, j);
                bombsField[i][j] = false;
            }
        }
    }

    public void changeGameMode(GamePreset gamePreset) {
        this.gamePreset = gamePreset;
        initField();
        newGame();
    }

    private void resetProgress() {
        flagsLeft = gamePreset.getMaxBombs();
        cellsLeft = gamePreset.getFieldSizeX() * gamePreset.getFieldSizeY();
    }

    public GameModel(GamePreset preset) {
        this.gamePreset = preset;
        status = GameStatus.NOT_STARTED;
        changeGameMode(preset);
        resetProgress();
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getFieldSizeX() {
        return gamePreset.getFieldSizeX();
    }

    public int getFieldSizeY() {
        return gamePreset.getFieldSizeY();
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public int getCellsLeft() {
        return cellsLeft;
    }

    public FieldCell[][] getGameField() {
        return gameField;
    }

    public GamePreset getGamePreset() {
        return gamePreset;
    }

    public boolean isFieldCell(int x, int y) {
        return (x >= 0 && x < gamePreset.getFieldSizeX() && y >= 0 && y < gamePreset.getFieldSizeY());
    }

    public boolean isCellOpened(int x, int y) {
        return gameField[x][y].isOpened();
    }

    public boolean isCellFlagged(int x, int y) {
        return gameField[x][y].isMarked();
    }

    public int getCellValue(int x, int y) {
        return gameField[x][y].getNeighborsWithBombs();
    }

    private int getCountBombsNear(int x, int y) {
        int countBombs = 0;
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if (isFieldCell(i, j) && bombsField[i][j]) {
                    ++countBombs;
                }
            }
        }
        return countBombs;
    }

    public void resetField() {
        int fieldSizeX = gamePreset.getFieldSizeX();
        int fieldSizeY = gamePreset.getFieldSizeY();
        for (int i = 0; i < fieldSizeX; ++i) {
            for (int j = 0; j < fieldSizeY; ++j) {
                gameField[i][j].reset();
                bombsField[i][j] = false;
            }
        }

        // Generating bombs
        Random rnd = new Random();
        int plantedBombs = 0;
        while (plantedBombs != gamePreset.getMaxBombs()) {
            int randomNumber = rnd.nextInt(fieldSizeX * fieldSizeY - 1);
            int i = randomNumber % fieldSizeX;
            int j = randomNumber / fieldSizeX;
            if (!bombsField[i][j]) {
                bombsField[i][j] = true;
                ++plantedBombs;
            }
        }

        // Getting neighbors with bombs for each
        for (int i = 0; i < fieldSizeX; ++i) {
            for (int j = 0; j < fieldSizeY; ++j) {
                gameField[i][j].setNeighborsWithBombs(getCountBombsNear(i, j));
            }
        }
    }

    public void openCell(int x, int y) {
        if (!isFieldCell(x, y))
            throw new IllegalArgumentException("Wrong indexes for opening cell!");

        if (gameField[x][y].isOpened())
            return;

        gameField[x][y].setOpened(true);
        --cellsLeft;
        if (bombsField[x][y]) {
            status = GameStatus.LOSE;
        } else {
            if (cellsLeft == 0) {
                status = GameStatus.WIN;
            }
            if (gameField[x][y].isMarked()) {
                unmarkCell(x, y);
            }
            if (gameField[x][y].getNeighborsWithBombs() == 0) {
                openNeighbors(gameField[x][y]);
            }
        }
    }

    private List<FieldCell> getNeighborsList(FieldCell cell) {
        List<FieldCell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        for (int curY = y - 1; curY <= y + 1; ++curY) {
            for (int curX = x - 1; curX <= x + 1; ++curX) {
                if (isFieldCell(curX, curY)) {
                    neighbors.add(gameField[curX][curY]);
                }
            }
        }
        neighbors.removeIf(value -> (value.getX() == x && value.getY() == y));
        return neighbors;
    }

    private void openNeighbors(FieldCell cell) {
        var neighborsList = getNeighborsList(cell);
        for (FieldCell neighbor : neighborsList) {
            openCell(neighbor.getX(), neighbor.getY());
        }
    }

    public void markCell(int x, int y) {
        if (!isFieldCell(x, y))
            throw new IllegalArgumentException("Wrong indexes for marking cell!");

        if (!gameField[x][y].isOpened() && flagsLeft > 0) {
            gameField[x][y].setMarked(true);
            --cellsLeft;
            --flagsLeft;
            if (cellsLeft == 0) {
                status = GameStatus.WIN;
            }
        }
    }

    public void unmarkCell(int x, int y) {
        if (!isFieldCell(x, y))
            throw new IllegalArgumentException("Wrong indexes for unmarking cell!");

        if (!gameField[x][y].isOpened() || gameField[x][y].isMarked()) {
            gameField[x][y].setMarked(false);
            ++cellsLeft;
            ++flagsLeft;
        }
    }

    public void newGame() {
        status = GameStatus.ALIVE;
        resetField();
        resetProgress();
    }

    public void showBombs() {
        for (int i = 0; i < gamePreset.getFieldSizeX(); ++i) {
            for (int j = 0; j < gamePreset.getFieldSizeY(); ++j) {
                if (bombsField[i][j]) {
                    gameField[i][j].setOpened(true);
                }
            }
        }
    }

    public CellState getCellState(int x, int y) {
        CellState cellState;
        if (status == GameStatus.ALIVE) {
            if (gameField[x][y].isOpened()) {
                cellState = OPENED;
            } else {
                if (gameField[x][y].isMarked()) {
                    cellState = FLAGGED;
                } else {
                    cellState = CLOSED;
                }
            }
        } else {
            if (gameField[x][y].isOpened()) {
                if (bombsField[x][y]) {
                    if (status == GameStatus.LOSE) {
                        cellState = BLOWNED_BOMB;
                    } else {
                        cellState = DEFUSED;
                    }
                } else {
                    cellState = OPENED;
                }
            } else {
                if (bombsField[x][y]) {
                    if (gameField[x][y].isMarked()) {
                        cellState = DEFUSED;
                    } else {
                        cellState = NOT_FOUNDED_BOMB;
                    }
                } else {
                    if (gameField[x][y].isMarked()) {
                        cellState = FLAGGED;
                    } else {
                        cellState = CLOSED;
                    }
                }
            }
        }
        return cellState;
    }

    public void stopGame() {
        status = GameStatus.NOT_STARTED;
    }
}
