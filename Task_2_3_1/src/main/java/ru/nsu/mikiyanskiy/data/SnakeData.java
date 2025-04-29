package ru.nsu.mikiyanskiy.data;
import javafx.geometry.Point2D;
import ru.nsu.mikiyanskiy.model.AllDirections;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeData {
    private Deque<Point2D> body = new LinkedList<>();
    private AllDirections currentDirection = AllDirections.RIGHT;

    public SnakeData(Point2D startPosition) {
        body.add(startPosition);
    }

    public AllDirections getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(AllDirections currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Deque<Point2D> getBody() {
        return body;
    }

    public Point2D getHead() {
        return body.peekFirst();
    }
}