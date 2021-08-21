package org.nsu.minesweeper.model.gamemodel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighscoreTable {
    private final List<Player> playersList;
    private final String tableFilePath;

    private List<Player> getScoreTable() {
        List<Player> players = new ArrayList<>();
        String row;
        try {
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(tableFilePath)));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                players.add(new Player(data[0], Long.parseLong(data[1])));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    public HighscoreTable(String tableFilePath) {
        this.tableFilePath = tableFilePath;
        this.playersList = getScoreTable();
    }

    public Player findPlayer(String playerNickname) {
        for (var player : playersList) {
            if (player.getNickname().equals(playerNickname)) {
                return player;
            }
        }
        return null;
    }

    public void rewriteTable() {
        try {
            PrintWriter tableWritter = new PrintWriter(tableFilePath);
            for (var player : playersList) {
                tableWritter.println(player.getNickname() + ',' + player.getRecordTime());
            }
            tableWritter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player newPlayer) {
        playersList.add(newPlayer);
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public List<Player> getPlayedPlayersList() {
        var filteredPlayerList = new ArrayList<>(playersList);
        filteredPlayerList.removeIf(player -> player.getRecordTime() == Long.MAX_VALUE);
        return filteredPlayerList;
    }
}
