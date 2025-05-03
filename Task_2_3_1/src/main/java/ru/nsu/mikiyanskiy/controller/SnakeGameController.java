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
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.Glow;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Deque;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SnakeGameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label statusLabel;
    @FXML
    private VBox endOverlay;
    @FXML
    private Label scoreLabel;
    @FXML
    private AnchorPane gameContainer;
    @FXML
    private Label finalScoreLabel;

    private int rows, cols, foodCount, winLength;
    private int score = 0;
    private static final int CELL_SIZE = 30;
    private ModelOfGame model;
    private Timeline timeline;
    private ParallelTransition endGameAnimation;
    private ScaleTransition foodEatenAnimation;
    private Glow glowEffect;
    private DropShadow shadowEffect;
    private GraphicsContext gc;

    public void initialize() {
        // Initialize effects
        glowEffect = new Glow(0.8);
        shadowEffect = new DropShadow(10, Color.rgb(67, 198, 172, 0.5));

        // Initialize canvas with fixed size
        if (gameCanvas != null) {
            gameCanvas.setWidth(400);  // Reduced width
            gameCanvas.setHeight(400); // Reduced height
            gc = gameCanvas.getGraphicsContext2D();
            gameCanvas.setFocusTraversable(true);
        }

        // Add resize listener
        gameContainer.widthProperty().addListener((obs, oldVal, newVal) -> updateCanvasSize());
        gameContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateCanvasSize());
    }

    private void updateCanvasSize() {
        if (model == null || gameCanvas == null || gc == null) return;
        
        // Keep fixed size but center the canvas
        gameCanvas.setLayoutX((gameContainer.getWidth() - gameCanvas.getWidth()) / 2);
        gameCanvas.setLayoutY((gameContainer.getHeight() - gameCanvas.getHeight()) / 2);
        
        // Redraw the game
        drawGame();
    }

    private void handleKey(KeyEvent e) {
        switch (e.getCode()) {
            case UP, W -> model.getSnake().setCurrentDirection(AllDirections.UP);
            case DOWN, S -> model.getSnake().setCurrentDirection(AllDirections.DOWN);
            case LEFT, A -> model.getSnake().setCurrentDirection(AllDirections.LEFT);
            case RIGHT, D -> model.getSnake().setCurrentDirection(AllDirections.RIGHT);
        }
    }

    private void updateGame() {
        if (model.updateGameState()) {
            if (model.hasPlayerWon()) {
                timeline.stop();
                showGameOver("You Won!");
            }
            drawGame();
            scoreLabel.setText("Score: " + (model.getSnake().getBody().size() - 1));
        } else {
            timeline.stop();
            showGameOver("Game Over!");
        }
    }

    private void drawGame() {
        if (gc == null || gameCanvas == null) return;
        
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        
        // Calculate cell size based on fixed canvas size
        double cellSize = Math.min(
            gameCanvas.getWidth() / cols,
            gameCanvas.getHeight() / rows
        );
        
        // Draw grid
        gc.setStroke(Color.rgb(67, 198, 172, 0.2));
        gc.setLineWidth(1);
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(x * cellSize, 0, x * cellSize, gameCanvas.getHeight());
        }
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(0, y * cellSize, gameCanvas.getWidth(), y * cellSize);
        }
        
        // Draw snake
        var body = model.getSnake().getBody();
        var bodyList = new ArrayList<>(body);
        for (int i = 0; i < bodyList.size(); i++) {
            Point2D part = bodyList.get(i);
            double opacity = 0.7 + (0.3 * i / bodyList.size());
            
            if (i == 0) { // Head
                gc.setFill(Color.rgb(67, 198, 172));
                gc.setEffect(glowEffect);
            } else { // Body
                gc.setFill(Color.rgb(67, 198, 172, opacity));
                gc.setEffect(null);
            }
            
            gc.fillRoundRect(
                part.getX() * cellSize + 2,
                part.getY() * cellSize + 2,
                cellSize - 4,
                cellSize - 4,
                8, 8
            );
        }
        
        // Draw food
        gc.setEffect(shadowEffect);
        gc.setFill(Color.rgb(248, 255, 174));
        for (Point2D food : model.getFoodList()) {
            gc.fillOval(
                food.getX() * cellSize + 4,
                food.getY() * cellSize + 4,
                cellSize - 8,
                cellSize - 8
            );
        }
        gc.setEffect(null);
    }

    private void showGameOver(String message) {
        statusLabel.setText(message);
        finalScoreLabel.setText("Final Score: " + (model.getSnake().getBody().size() - 1));
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), endOverlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(500), endOverlay);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1);
        scaleIn.setToY(1);
        
        endOverlay.setVisible(true);
        fadeIn.play();
        scaleIn.play();
    }

    @FXML
    private void handlePlayAgain() {
        endOverlay.setVisible(false);
        initGame(rows, cols, foodCount, winLength);
    }

    private void startGame() {
        timeline.play();
    }

    public void initGame(int rows, int cols, int foodCount, int winLength) {
        this.rows = rows;
        this.cols = cols;
        this.foodCount = foodCount;
        this.winLength = winLength;
        score = 0;
        scoreLabel.setText("Score: 0");

        model = new ModelOfGame(rows, cols, foodCount, winLength);
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> updateGame()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        updateCanvasSize();
        
        timeline.play();
        endOverlay.setVisible(false);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKey);
        statusLabel.setText("");
    }

    @FXML
    void restartGame() {
        initGame(rows, cols, foodCount, winLength);
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
