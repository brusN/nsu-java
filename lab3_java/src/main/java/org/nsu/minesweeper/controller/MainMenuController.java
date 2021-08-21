package org.nsu.minesweeper.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.view.ApplicationGUI;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Text nicknameText;

    @FXML
    private Text recordtimeText;

    public void switchToMenu() {
        ApplicationGUI.switchScene(CurrentScene.MAIN_MENU);
    }

    public void switchToGame() {
        ApplicationGUI.switchScene(CurrentScene.GAME);
    }

    public void switchToAbout() {
        ApplicationGUI.switchScene(CurrentScene.ABOUT);
    }

    public void switchToHighscores() {
        ApplicationGUI.switchScene(CurrentScene.HIGHSCORES);
    }

    public void exit(javafx.event.ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var playerProfile = LoginController.getPlayerProfile();
        nicknameText.setText(playerProfile.getNickname());
        long recordTime = playerProfile.getRecordTime();
        recordtimeText.setText(recordTime == Long.MAX_VALUE ? "not played" : (double) recordTime / 1000 + " sec");
    }
}
