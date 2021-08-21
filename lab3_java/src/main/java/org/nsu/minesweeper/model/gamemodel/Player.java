package org.nsu.minesweeper.model.gamemodel;

public class Player {
    private final String nickname;
    private long recordTime;

    public Player(String nickname, long recordTime) {
        this.nickname = nickname;
        this.recordTime = recordTime;
    }

    public String getNickname() {
        return nickname;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void updateRecord (long newRecordTime) {
        this.recordTime = newRecordTime;
    }
}
