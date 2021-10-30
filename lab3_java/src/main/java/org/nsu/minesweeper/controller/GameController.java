package org.nsu.minesweeper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.main.providers.GamePresetsProvider;
import org.nsu.minesweeper.main.providers.ImageProvider;
import org.nsu.minesweeper.model.eventStatuses.GameMode;
import org.nsu.minesweeper.model.eventStatuses.GameStatus;
import org.nsu.minesweeper.model.gamefield.GridCell;
import org.nsu.minesweeper.model.gamemodel.GameModel;
import org.nsu.minesweeper.model.gamemodel.GamePreset;
import org.nsu.minesweeper.model.gamemodel.Player;
import org.nsu.minesweeper.model.timer.ThreadSafeTimerCounter;
import org.nsu.minesweeper.view.ApplicationGUI;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements Initializable {
    @FXML
    private ImageView backButton;

    @FXML
    private ComboBox<GamePreset> fieldSizeComboBox;

    @FXML
    private GridPane fieldGridPane;

    @FXML
    private ImageView statusIcon;

    @FXML
    private ImageView firstNumFlagCounter;

    @FXML
    private ImageView secondNumFlagCounter;

    @FXML
    private ImageView firstNumTimer;

    @FXML
    private ImageView secondNumTimer;

    @FXML
    private ImageView thirdNumTimer;


    private Player playerProfile;
    private Timer timer = new Timer();
    private final ThreadSafeTimerCounter timerCounter;
    private final static HashMap<String, Image> images;
    private final GameModel gameModel;
    private GridCell[][] cellsView;

    static {
        images = ImageProvider.getImages("images.properties");
    }

    public void startTimer() {
        resetTime();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCounter.increment(10);
                updateTimerView();
            }
        }, 0, 10);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void resetTime() {
        timerCounter.reset();
    }

    public GameController() {
        timerCounter = new ThreadSafeTimerCounter();
        gameModel = new GameModel(GamePresetsProvider.getGamePreset(ApplicationGUI.getGameMode()));
    }

    public static Image getImage(String imageName) {
        return images.get(imageName);
    }

    public void switchToMenu() {
        startTimer();
        ApplicationGUI.switchScene(CurrentScene.MAIN_MENU);
    }

    private void initComboBox() {
        // Adding variables to choice box
        fieldSizeComboBox.getItems().addAll(
                GamePresetsProvider.getGamePreset(GameMode.EASY),
                GamePresetsProvider.getGamePreset(GameMode.NORMAL),
                GamePresetsProvider.getGamePreset(GameMode.HARD));

        // Displaying values in app
        fieldSizeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(GamePreset object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public GamePreset fromString(String s) {
                return null;
            }
        });
        fieldSizeComboBox.setValue(gameModel.getGamePreset());

        // Action on changed value
        fieldSizeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            ApplicationGUI.resizeWindow(newVal);
        });
    }

    private void updateGridView() {
        for (int i = 0; i < gameModel.getFieldSizeX(); ++i) {
            for (int j = 0; j < gameModel.getFieldSizeY(); ++j) {
                cellsView[i][j].updateCellView(gameModel.getCellState(i, j));
            }
        }
    }

    private void tryToOpenCell(int x, int y) {
        if (!(gameModel.isCellOpened(x, y) || gameModel.isCellFlagged(x, y))) {
            gameModel.openCell(x, y);
            updateGridView();
            updateTopBarInfo();
        }
    }

    private void tryToMarkCell(int x, int y) {
        if (!gameModel.isCellOpened(x, y)) {
            if (gameModel.isCellFlagged(x, y)) {
                gameModel.unmarkCell(x, y);
            } else {
                gameModel.markCell(x, y);
            }
            cellsView[x][y].updateCellView(gameModel.getCellState(x, y));
            updateTopBarInfo();
        }
    }

    private void lockMouseActions() {
        fieldGridPane.getChildren().forEach(item -> { item.setOnMouseClicked(event -> {}); });
    }

    private void updateFlagCounter() {
        int flagsCount = gameModel.getFlagsLeft();
        int firstNum = flagsCount / 10;
        int secondNum = flagsCount % 10;
        firstNumFlagCounter.setImage(getImage("DIGIT_" + firstNum));
        secondNumFlagCounter.setImage(getImage("DIGIT_" + secondNum));
    }

    private void updatePlayerRecordTime() {
        long currentTime = timerCounter.getValue();
        long playerCurrentTime = playerProfile.getRecordTime();
        if (currentTime < playerCurrentTime) {
            playerProfile.updateRecord(currentTime);
            HighscoresController.updateHighscoresTable();
        }
    }

    private void updateTopBarInfo() {
        updateFlagCounter();
        GameStatus status = gameModel.getStatus();
        if (status == GameStatus.ALIVE)
            return;

        switch (status) {
            case WIN: {
                statusIcon.setImage(getImage("STATUS_WIN"));
                updatePlayerRecordTime();
                break;
            }
            case LOSE: {
                statusIcon.setImage(getImage("STATUS_LOSE"));
                break;
            }
        }
        lockMouseActions();
        stopTimer();
    }

    private void resetViewCells() {
        for (int i = 0; i < gameModel.getFieldSizeX(); ++i) {
            for (int j = 0; j < gameModel.getFieldSizeY(); ++j) {
                cellsView[i][j].reset();
                cellsView[i][j].setValue(gameModel.getCellValue(i, j));
            }
        }
    }

    @FXML
    private void startNewGame() {
        stopTimer();
        gameModel.newGame();
        resetViewCells();
        setMouseActionsForCells();
        updateTopBarInfo();
        statusIcon.setImage(getImage("STATUS_ALIVE"));
        startTimer();
    }

    private void setMouseActionsForCells() {
        fieldGridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(event -> {
                int x = GridPane.getColumnIndex(item);
                int y = GridPane.getRowIndex(item);

                if (event.getButton() == MouseButton.PRIMARY) {
                    tryToOpenCell(x, y);
                }

                if (event.getButton() == MouseButton.SECONDARY) {
                    tryToMarkCell(x, y);
                }
            });
        });
    }

    private void initGrid() {
        int fieldSizeX = gameModel.getFieldSizeX();
        int fieldSizeY = gameModel.getFieldSizeY();
        cellsView = new GridCell[fieldSizeX][fieldSizeY];
        for (int i = 0; i < gameModel.getFieldSizeX(); ++i) {
            for (int j = 0; j < gameModel.getFieldSizeY(); ++j) {
                cellsView[i][j] = new GridCell();
                fieldGridPane.add(cellsView[i][j], i, j);
            }
        }
    }

    private void updateTimerView() {
        long time = timerCounter.getValue();
        int countSeconds = (int) (time / 1000);
        thirdNumTimer.setImage(images.get("DIGIT_" + countSeconds % 10));
        secondNumTimer.setImage(images.get("DIGIT_" + countSeconds / 10));
        firstNumTimer.setImage(images.get("DIGIT_" + countSeconds / 100));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerProfile = LoginController.getPlayerProfile();
        gameModel.changeGameMode(GamePresetsProvider.getGamePreset(ApplicationGUI.getGameMode()));
        initComboBox();
        initGrid();
        statusIcon.setOnMouseClicked(event -> startNewGame());
        startNewGame();
    }
}
