package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Starts the JavaFX Tic-Tac-Toe application. */
public class TicTacToeApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TicTacToeView view = new TicTacToeView();
        Scene scene = view.createScene();
        new TicTacToeController(view);
        stage.setTitle("Tic-Tac-Toe");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
