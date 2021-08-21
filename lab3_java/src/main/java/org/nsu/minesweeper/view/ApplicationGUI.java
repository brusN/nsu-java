package org.nsu.minesweeper.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.nsu.minesweeper.controller.GameController;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.main.providers.GamePresetsProvider;
import org.nsu.minesweeper.model.eventStatuses.GameMode;
import org.nsu.minesweeper.model.gamemodel.GamePreset;

import java.io.IOException;
import java.util.Objects;

public class ApplicationGUI extends Application {
    private static Stage stage;
    private static GameMode gameMode = GameMode.EASY;

    public static GameMode getGameMode() {
        return gameMode;
    }

    private void login() {
        Stage loginStage = new Stage();
        loginStage.getIcons().add(GameController.getImage("BOMB"));
        loginStage.setTitle("Login");
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(ApplicationGUI.class.getClassLoader().getResource("login.fxml")));
            loginStage.setScene(new Scene(root));
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setStartWindowOptions(Stage stage) {
        stage.setTitle("Minesweeper");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/bomb.png"))));
    }

    public static void switchScene(CurrentScene nextScene) {
        String nextSceneFXMLPath = null;
        switch (nextScene) {
            case MAIN_MENU: {
                nextSceneFXMLPath = "mainMenu.fxml";
                break;
            }
            case ABOUT: {
                nextSceneFXMLPath = "about.fxml";
                break;
            }
            case HIGHSCORES: {
                nextSceneFXMLPath = "highscores.fxml";
                break;
            }
            case GAME: {
                resizeWindow(GamePresetsProvider.getGamePreset(gameMode));
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown scene!");
            }
        }
        try {
            if (nextScene != CurrentScene.GAME) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(ApplicationGUI.class.getClassLoader().getResource(nextSceneFXMLPath)));
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage startStage) {
        stage = startStage;
        setStartWindowOptions(startStage);
        login();
    }

    public static void resizeWindow(GamePreset gamePreset) {
        String pathPostfix;
        switch (gamePreset.getGameMode()) {
            case EASY: {
                gameMode = GameMode.EASY;
                pathPostfix = "_easy";
                break;
            }
            case NORMAL: {
                gameMode = GameMode.NORMAL;
                pathPostfix = "_normal";
                break;
            }
            case HARD: {
                gameMode = GameMode.HARD;
                pathPostfix = "_hard";
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + gamePreset.getGameMode());
        }
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(ApplicationGUI.class.getClassLoader().getResource("game" + pathPostfix + ".fxml")));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
