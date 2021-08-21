package org.nsu.minesweeper.model.gamemodel;

import org.nsu.minesweeper.model.eventStatuses.GameMode;

public class GamePreset {
    private final GameMode gameMode;
    private final String name;
    private final int fieldSizeX;
    private final int fieldSizeY;
    private final int maxBombs;

    public GamePreset(GameMode gameMode, String name, int fieldSizeX, int fieldSizeY, int maxBombs) {
        this.gameMode = gameMode;
        this.name = name;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.maxBombs = maxBombs;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getName() {
        return name;
    }

    public int getFieldSizeX() {
        return fieldSizeX;
    }

    public int getFieldSizeY() {
        return fieldSizeY;
    }


    public int getMaxBombs() {
        return maxBombs;
    }
}
