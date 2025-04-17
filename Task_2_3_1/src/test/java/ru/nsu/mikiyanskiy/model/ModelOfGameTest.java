package ru.nsu.mikiyanskiy.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelOfGameTest {
    private ModelOfGame model;
    private final int rows = 10;
    private final int cols = 10;
    private final int foodCount = 3;
    private final int winLength = 5;

    @BeforeEach
    public void setUp() {
        model = new ModelOfGame(rows, cols, foodCount, winLength);
    }

    @Test
    public void testVictoryCondition() {
        Snake snake = model.getSnake();
        for (int i = 0; i < winLength - 1; i++) {
            snake.move(true); // ручное удлинение
        }
        assertTrue(model.hasPlayerWon());
    }

    @Test
    public void testGameFieldDimensions() {
        assertEquals(rows, model.getNumRows());
        assertEquals(cols, model.getNumCols());
        assertEquals(winLength, model.getTargetLength());
    }

    @Test
    public void testFoodNotOnSnake() {
        for (Point2D food : model.getFoodList()) {
            assertFalse(model.getSnake().getBody().contains(food));
        }
    }

    @Test
    public void testInitialSnakePosition() {
        Point2D expected = new Point2D(cols / 2, rows / 2);
        assertEquals(expected, model.getSnake().getHead());
    }

    @Test
    public void testFoodSpawnedInitially() {
        List<Point2D> food = model.getFoodList();
        assertEquals(foodCount, food.size());
    }

    @Test
    public void testUpdateMovesSnake() {
        Point2D before = model.getSnake().getHead();
        model.updateGameState();
        Point2D after = model.getSnake().getHead();
        assertNotEquals(before, after);
    }

    @Test
    public void testUpdateEatsFood() {
        // Ставим еду прямо перед змейкой
        Snake snake = model.getSnake();
        Point2D next = snake.getNextHeadPosition();
        model.getFoodList().clear();
        model.getFoodList().add(next);

        int sizeBefore = snake.getBody().size();
        boolean alive = model.updateGameState(); // съедает
        int sizeAfter = snake.getBody().size();

        assertTrue(alive);
        assertEquals(sizeBefore + 1, sizeAfter); // вырос
    }

    @Test
    public void testUpdateGameOverWhenHitsWall() {
        // Направим змею влево к краю
        Snake snake = model.getSnake();
        snake.setDirection(AllDirections.LEFT);

        while (model.updateGameState()); // идёт до столкновения

        assertFalse(model.updateGameState()); // уже мёртвая
    }
}
