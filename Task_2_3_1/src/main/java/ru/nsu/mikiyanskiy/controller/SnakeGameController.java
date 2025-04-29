package ru.nsu.mikiyanskiy.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.nsu.mikiyanskiy.model.*;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class SnakeGameController {
    @FXML
    Canvas canvas;
    @FXML
    Label statusLabel;
    @FXML
    VBox endOverlay;


    private int rows, cols, foodCount, winLength;

    private final int CELL_SIZE = 20;
    private ModelOfGame model;
    private Timeline timeline;

    private void handleKey(KeyEvent e) {
        switch (e.getCode()) {
            case UP, W -> model.getSnake().setCurrentDirection(AllDirections.UP);
            case DOWN, S -> model.getSnake().setCurrentDirection(AllDirections.DOWN);
            case LEFT, A -> model.getSnake().setCurrentDirection(AllDirections.LEFT);
            case RIGHT, D -> model.getSnake().setCurrentDirection(AllDirections.RIGHT);
        }
    }

    private void gameLoop() {
        boolean alive = model.updateGameState();
        draw();
        if (!alive) {
            endGame("Game Over!");
        } else if (model.hasPlayerWon()) {
            endGame("You Win!");
        }
    }

    private void endGame(String message) {
        timeline.stop();
        statusLabel.setText(message);
        endOverlay.setVisible(true);
    }

    public void initGame(int rows, int cols, int foodCount, int winLength) {
        this.rows = rows;
        this.cols = cols;
        this.foodCount = foodCount;
        this.winLength = winLength;

        model = new ModelOfGame(rows, cols, foodCount, winLength);
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        endOverlay.setVisible(false);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKey);
        statusLabel.setText("");
    }

    @FXML
    void restartGame() {
        initGame(rows, cols, foodCount, winLength);
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int width = cols * CELL_SIZE;
        int height = rows * CELL_SIZE;
        canvas.setWidth(width);
        canvas.setHeight(height);

        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, canvas.getHeight());
        }
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(0, y * CELL_SIZE, canvas.getWidth(), y * CELL_SIZE);
        }

        var body = new ArrayList<>(model.getSnake().getBody());

        for (int i = 0; i < body.size(); i++) {
            Point2D part = body.get(i);
            if (i == 0) {
                gc.setFill(Color.DARKGREEN); // голова
            } else if (i == body.size() - 1) {
                gc.setFill(Color.LIGHTGREEN); // хвост
            } else {
                gc.setFill(Color.GREEN); // тело
            }
            gc.fillRect(part.getX() * CELL_SIZE, part.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.RED);
        for (var food : model.getFoodList()) {
            gc.fillOval(food.getX() * CELL_SIZE, food.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    @FXML
    private void goToSettings() {
        try {
            ru.nsu.mikiyanskiy.Main.showConfigScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
