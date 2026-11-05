package com.example;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/** Connects JavaFX controls to the existing Tic-Tac-Toe game logic. */
public class TicTacToeController {
    private final Board board = new Board();
    private final TicTacToeView view;
    private char currentMarker = 'X';
    private boolean gameOver;

    public TicTacToeController(TicTacToeView view) {
        this.view = view;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                int selectedRow = row;
                int selectedColumn = column;
                view.getCell(row, column).setOnAction(
                        event -> playHumanMove(selectedRow, selectedColumn));
            }
        }

        view.getRestartButton().setOnAction(event -> restart());
    }

    private void playHumanMove(int row, int column) {
        if (gameOver || currentMarker != 'X' || !board.place(row, column, 'X')) {
            return;
        }

        view.setCell(row, column, 'X');
        if (finishTurn()) {
            return;
        }

        currentMarker = 'O';
        view.setStatus("CPU is thinking...");
        view.setBoardDisabled(true);

        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(event -> playCpuTurn());
        pause.play();
    }

    private void playCpuTurn() {
        new CpuPlayer('O').makeMove(board);
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                char marker = board.getCell(row, column);
                if (marker == 'X' || marker == 'O') {
                    view.setCell(row, column, marker);
                }
            }
        }

        if (!finishTurn()) {
            currentMarker = 'X';
            view.setBoardDisabled(false);
            view.setStatus("Your turn - X");
        }
    }

    private boolean finishTurn() {
        if (board.checkWin()) {
            gameOver = true;
            view.setStatus(currentMarker == 'X' ? "You win!" : "CPU wins!");
            view.setBoardDisabled(true);
            highlightWinningCells();
            return true;
        }
        if (board.isFull()) {
            gameOver = true;
            view.setStatus("It's a draw!");
            view.setBoardDisabled(true);
            return true;
        }
        return false;
    }

    private void highlightWinningCells() {
        List<int[]> winningCells = new ArrayList<>();

        for (int index = 0; index < 3; index++) {
            if (isWinningMarker(board.getCell(index, 0), board.getCell(index, 1),
                    board.getCell(index, 2))) {
                winningCells.add(new int[]{index, 0});
                winningCells.add(new int[]{index, 1});
                winningCells.add(new int[]{index, 2});
            }
            if (isWinningMarker(board.getCell(0, index), board.getCell(1, index),
                    board.getCell(2, index))) {
                winningCells.add(new int[]{0, index});
                winningCells.add(new int[]{1, index});
                winningCells.add(new int[]{2, index});
            }
        }

        if (isWinningMarker(board.getCell(0, 0), board.getCell(1, 1),
                board.getCell(2, 2))) {
            winningCells.add(new int[]{0, 0});
            winningCells.add(new int[]{1, 1});
            winningCells.add(new int[]{2, 2});
        }
        if (isWinningMarker(board.getCell(0, 2), board.getCell(1, 1),
                board.getCell(2, 0))) {
            winningCells.add(new int[]{0, 2});
            winningCells.add(new int[]{1, 1});
            winningCells.add(new int[]{2, 0});
        }

        for (int[] cell : winningCells) {
            view.highlightCell(cell[0], cell[1]);
        }
    }

    private boolean isWinningMarker(char first, char second, char third) {
        return first != ' ' && first == second && second == third;
    }

    private void restart() {
        board.initialize();
        currentMarker = 'X';
        gameOver = false;
        view.clear();
        view.setStatus("Your turn - X");
    }
}
