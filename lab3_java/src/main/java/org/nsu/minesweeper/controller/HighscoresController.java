package org.nsu.minesweeper.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.nsu.minesweeper.model.eventStatuses.CurrentScene;
import org.nsu.minesweeper.model.gamemodel.HighscoreTable;
import org.nsu.minesweeper.model.gamemodel.Player;
import org.nsu.minesweeper.view.ApplicationGUI;

import java.net.URL;
import java.util.*;

public class HighscoresController implements Initializable {
    private final static HighscoreTable highscoreTable = new HighscoreTable("src/main/java/org/nsu/minesweeper/database/highscores.csv");

    @FXML
    private TableColumn<Player, String> nicknameColumn;
    @FXML
    private TableColumn<Player, Double> recordTileColumn;
    @FXML
    private TableView<Player> highscoreTableView;

    public static HighscoreTable getHighscoresTable() {
        return highscoreTable;
    }

    public static void updateHighscoresTable() {
        highscoreTable.rewriteTable();
    }

    public void switchToMenu() {
        ApplicationGUI.switchScene(CurrentScene.MAIN_MENU);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Получаем список и удаляем профили игроков, которые не играли ни разу
        highscoreTableView.setItems(FXCollections.observableArrayList(highscoreTable.getPlayedPlayersList()));

        // Сопоставляем характеристики игроков с колонками в таблице рекордов
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        highscoreTableView.getSortOrder().add(recordTileColumn);
        recordTileColumn.setSortType(TableColumn.SortType.ASCENDING);
        recordTileColumn.setCellValueFactory(new PropertyValueFactory<>("recordTime"));
        highscoreTableView.sort();
    }
}
