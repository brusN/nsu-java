package org.nsu.minesweeper.model.gamefield;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.nsu.minesweeper.controller.GameController;

public class GridCell extends StackPane {
    private final Text value;
    private final ImageView imageView;

    public GridCell() {
        value = new Text("");
        value.setFont(Font.font("Consolas", 18));
        value.setVisible(false);
        imageView = new ImageView(GameController.getImage("CLOSED_TILE"));
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        getChildren().addAll(imageView, value);
    }

    public void reset() {
        value.setText("");
        value.setVisible(false);
        imageView.setVisible(true);
        imageView.setImage(GameController.getImage("CLOSED_TILE"));
    }

    public void setValue(int value) {
        this.value.setText(value == 0 ? "" : String.valueOf(value));
    }

    public void updateCellView(CellState cellState) {
        String imageName = null;
        switch (cellState) {
            case CLOSED: {
                imageName = "CLOSED_TILE";
                break;
            }
            case OPENED: {
                value.setVisible(true);
                imageView.setVisible(false);
                break;
            }
            case FLAGGED: {
                imageName = "FLAGGED_TILE";
                break;
            }
            case DEFUSED: {
                imageName = "DEFUSED_TILE";
                break;
            }
            case NOT_FOUNDED_BOMB: {
                imageName = "CLOSED_WITH_BOMB_TILE";
                break;
            }
            case BLOWNED_BOMB: {
                imageName = "OPENED_WITH_BOMB_TILE";
                break;
            }
        }
        if (cellState != CellState.OPENED) {
            imageView.setImage(GameController.getImage(imageName));
        }
    }

    private void markTile() {
        imageView.setImage(GameController.getImage("FLAGGED_TILE"));
    }

    private void unmarkTile() {
        imageView.setImage(GameController.getImage("CLOSED_TILE"));
    }

    private void openTile() {
        value.setVisible(true);
        imageView.setVisible(false);
    }

    public Text getValue() {
        return value;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
