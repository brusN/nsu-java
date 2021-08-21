package org.nsu.minesweeper.model.commands;

import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.model.gamemodel.GameModel;

public class CommandData {
    private final GameModel model;
    private CurrentScene scene;

    public CommandData(GameModel model) {
        this.model = model;
    }

    public GameModel getModel() {
        return model;
    }

    public void setScene(CurrentScene scene) {
        this.scene = scene;
    }

    public CurrentScene getScene() {
        return scene;
    }


}
