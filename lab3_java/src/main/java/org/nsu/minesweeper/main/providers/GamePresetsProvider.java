package org.nsu.minesweeper.main.providers;

import org.nsu.minesweeper.model.eventStatuses.GameMode;
import org.nsu.minesweeper.model.gamemodel.GamePreset;

public class GamePresetsProvider {
    public static GamePreset getGamePreset(GameMode gameMode) {
        switch (gameMode) {
            case EASY: {
                return new GamePreset(gameMode, "EASY", 9, 9,  10);
            }
            case NORMAL: {
                return new GamePreset(gameMode, "NORMAL",16, 16,  40);
            }
            case HARD: {
                return new GamePreset(gameMode, "HARD",30, 16, 99);
            }
            default:
                throw new IllegalArgumentException("Unknown field type!");
        }
    }
}
