package org.nsu.minesweeper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.view.ApplicationGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private Text textArea;

    public void switchToMenu() {
        ApplicationGUI.switchScene(CurrentScene.MAIN_MENU);
    }

    private String getAboutGuide() {
        StringBuilder text = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("about.txt")));) {
            String line = fileReader.readLine();
            while (line != null) {
                text.append("\n").append(line);
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText(getAboutGuide());
    }
}
