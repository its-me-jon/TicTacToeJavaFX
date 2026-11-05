package com.example;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/** Builds and exposes the JavaFX controls used by the game. */
public class TicTacToeView {
    private final Button[][] cells = new Button[3][3];
    private final Label status = new Label("Your turn - X");
    private final Button restartButton = new Button("NEW GAME");

    public Scene createScene() {
        Label eyebrow = new Label("CLASSIC TIC-TAC-TOE");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Tic-Tac-Toe");
        title.getStyleClass().add("title");

        status.getStyleClass().add("status");

        GridPane board = new GridPane();
        board.getStyleClass().add("board");
        board.setHgap(10);
        board.setVgap(10);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                Button cell = new Button();
                cell.getStyleClass().add("cell");
                cells[row][column] = cell;
                board.add(cell, column, row);
            }
        }

        restartButton.getStyleClass().add("restart-button");
        Label hint = new Label("Choose an empty square");
        hint.getStyleClass().add("hint");

        VBox content = new VBox(12, eyebrow, title, status, board, hint, restartButton);
        content.getStyleClass().add("content");
        content.setAlignment(Pos.CENTER);

        VBox root = new VBox(content);
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(false);

        Scene scene = new Scene(root, 520, 720);
        scene.getStylesheets().add(Objects.requireNonNull(
                TicTacToeView.class.getResource("/app.css")).toExternalForm());
        return scene;
    }

    public Button getCell(int row, int column) {
        return cells[row][column];
    }

    public void setStatus(String text) {
        status.setText(text);
    }

    public void setCell(int row, int column, char marker) {
        Button cell = cells[row][column];
        cell.setText(String.valueOf(marker));
        cell.getStyleClass().add(marker == 'X' ? "x-cell" : "o-cell");
    }

    public void clear() {
        for (Button[] row : cells) {
            for (Button cell : row) {
                cell.setText("");
                cell.setDisable(false);
                cell.getStyleClass().removeAll("x-cell", "o-cell");
            }
        }
    }

    public void setBoardDisabled(boolean disabled) {
        for (Button[] row : cells) {
            for (Button cell : row) {
                if (cell.getText().isEmpty()) {
                    cell.setDisable(disabled);
                }
            }
        }
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
