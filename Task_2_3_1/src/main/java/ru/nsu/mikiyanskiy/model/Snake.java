package ru.nsu.mikiyanskiy.model;

import java.util.Deque;
import javafx.geometry.Point2D;
import ru.nsu.mikiyanskiy.data.SnakeData;

public class Snake {
    private SnakeData data;

    public Snake(SnakeData data) {
        this.data = data;
    }

    Point2D getNextHeadPosition() {
        Point2D head = getHead();
        switch (data.getCurrentDirection()) {
            case UP: return head.add(0, -1);
            case DOWN: return head.add(0, 1);
            case LEFT: return head.add(-1, 0);
            case RIGHT: return head.add(1, 0);
            default: return head;
        }
    }

    public AllDirections getDirection() {
        return data.getCurrentDirection();
    }

    public void move(boolean grow) {
        Point2D newHead = getNextHeadPosition();
        data.getBody().addFirst(newHead);
        if (!grow) {
            data.getBody().removeLast();
        }
    }

    public boolean isColliding(Point2D point) {
        return data.getBody().contains(point);
    }

    private boolean isOpposite(AllDirections newDir) {
        return (data.getCurrentDirection() == AllDirections.UP && newDir == AllDirections.DOWN) ||
                (data.getCurrentDirection() == AllDirections.DOWN && newDir == AllDirections.UP) ||
                (data.getCurrentDirection() == AllDirections.LEFT && newDir == AllDirections.RIGHT) ||
                (data.getCurrentDirection() == AllDirections.RIGHT && newDir == AllDirections.LEFT);
    }

    public void setCurrentDirection(AllDirections newDirection) {
        if (!isOpposite(newDirection)) {
            data.setCurrentDirection(newDirection);
        }
    }

    public Point2D getHead() {
        return data.getHead();
    }

    public Deque<Point2D> getBody() {
        return data.getBody();
    }
}
