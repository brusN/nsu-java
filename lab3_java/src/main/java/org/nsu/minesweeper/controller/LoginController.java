package org.nsu.minesweeper.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.model.gamemodel.Player;
import org.nsu.minesweeper.view.ApplicationGUI;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static Player playerProfile;

    @FXML
    private TextField textArea;

    @FXML
    private Button enterButton;

    @FXML
    private Button exitButton;

    private void enterNickname() {
        String nicknameRegex = "^[^0-9][^ @#]+$";
        String enteredNickname = textArea.getText();
        if (enteredNickname.matches(nicknameRegex)) {
            var playerTable = HighscoresController.getHighscoresTable();
            playerProfile = playerTable.findPlayer(enteredNickname);
            if (playerProfile == null) {
                playerProfile = new Player(enteredNickname, Long.MAX_VALUE);
                playerTable.addPlayer(playerProfile);
            }
            Stage rootStage = (Stage)enterButton.getScene().getWindow();
            rootStage.close();
            ApplicationGUI.switchScene(CurrentScene.MAIN_MENU);
        } else {
            textArea.setText("Wrong nickname, try another!");
        }
    }

    public static Player getPlayerProfile() {
        return playerProfile;
    }

    private void exitFromApplication() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterButton.setOnAction(event -> enterNickname());
        exitButton.setOnAction(event -> exitFromApplication());
    }
}
