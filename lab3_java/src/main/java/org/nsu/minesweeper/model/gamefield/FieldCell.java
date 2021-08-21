package org.nsu.minesweeper.model.gamefield;

public class FieldCell {
    private final int x;
    private final int y;
    private boolean marked;
    private boolean opened;
    private int neighborsWithBombs;

    public FieldCell(int x, int y) {
        this.x = x;
        this.y = y;
        reset();
    }

    public void reset() {
        marked = false;
        opened = false;
        neighborsWithBombs = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNeighborsWithBombs(int value) {
        neighborsWithBombs = value;
    }

    public int getNeighborsWithBombs() {
        return neighborsWithBombs;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }


}
