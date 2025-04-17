package ru.nsu.mikiyanskiy.model;

import javafx.geometry.Point2D;
import java.util.*;

public class ModelOfGame {
    private final int numRows;
    private final int numCols;
    private final int targetLength;
    private Snake snake;
    private List<Point2D> foodList = new ArrayList<>();
    private Random random = new Random();

    public ModelOfGame(int numRows, int numCols, int foodAmount, int targetLength) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.targetLength = targetLength;
        this.snake = new Snake(new Point2D(numCols / 2, numRows / 2));
        for (int i = 0; i < foodAmount; i++) {
            generateFood();
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Point2D> getFoodList() {
        return foodList;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getTargetLength() {
        return targetLength;
    }

    public boolean updateGameState() {
        Point2D nextHeadPosition = snake.getNextHeadPosition();
        boolean hasEaten = foodList.remove(nextHeadPosition);
        if (checkGameOver(nextHeadPosition)) return false;
        snake.move(hasEaten);
        if (hasEaten) generateFood();
        return true;
    }

    public boolean hasPlayerWon() {
        return snake.getBody().size() >= targetLength;
    }

    private boolean checkGameOver(Point2D newHeadPosition) {
        return newHeadPosition.getX() < 0 || newHeadPosition.getX() >= numCols ||
                newHeadPosition.getY() < 0 || newHeadPosition.getY() >= numRows ||
                snake.isColliding(newHeadPosition);
    }

    private void generateFood() {
        Point2D point;
        do {
            point = new Point2D(random.nextInt(numCols), random.nextInt(numRows));
        } while (snake.isColliding(point) || foodList.contains(point));
        foodList.add(point);
    }
}
