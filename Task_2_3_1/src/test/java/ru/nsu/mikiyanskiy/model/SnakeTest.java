package ru.nsu.mikiyanskiy.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.mikiyanskiy.data.SnakeData;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake snake;
    private Point2D start;

    @BeforeEach
    public void setUp() {
        start = new Point2D(5, 5);
        SnakeData snakeData = new SnakeData(start);
        snake = new Snake(snakeData);
    }

    @Test
    public void testMoveWithGrowth() {
        snake.move(true);
        assertEquals(new Point2D(6, 5), snake.getHead());
        assertEquals(2, snake.getBody().size()); // вырос
    }

    @Test
    public void testDirectionChangeValid() {
        snake.setCurrentDirection(AllDirections.DOWN); // допустимая смена
        assertEquals(AllDirections.DOWN, snake.getDirection());
    }

    @Test
    public void testIsCollidingTrue() {
        snake.move(true); // растем, тело: [6,5], [5,5]
        assertTrue(snake.isColliding(new Point2D(5, 5)));
    }

    @Test
    public void testIsCollidingFalse() {
        snake.move(true);
        assertFalse(snake.isColliding(new Point2D(10, 10)));
    }

    @Test
    public void testInitialHead() {
        assertEquals(start, snake.getHead());
        assertEquals(1, snake.getBody().size());
    }

    @Test
    public void testMoveWithoutGrowth() {
        snake.move(false);
        assertEquals(new Point2D(6, 5), snake.getHead());
        assertEquals(1, snake.getBody().size()); // не вырос
    }

    @Test
    public void testGetNextHeadPositionRight() {
        assertEquals(new Point2D(6, 5), snake.getNextHeadPosition());
    }

    @Test
    public void testGetNextHeadPositionAfterDirectionChange() {
        snake.setCurrentDirection(AllDirections.UP);
        assertEquals(new Point2D(5, 4), snake.getNextHeadPosition());
    }
}
