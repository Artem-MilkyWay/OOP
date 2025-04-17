package ru.nsu.mikiyanskiy.model;

import java.util.Deque;
import java.util.LinkedList;
import javafx.geometry.Point2D;

public class Snake {
    private Deque<Point2D> body = new LinkedList<>();
    private AllDirections currentAllDirections = AllDirections.RIGHT;

    Point2D getNextHeadPosition() {
        Point2D head = getHead();
        switch (currentAllDirections) {
            case UP: return head.add(0, -1);
            case DOWN: return head.add(0, 1);
            case LEFT: return head.add(-1, 0);
            case RIGHT: return head.add(1, 0);
            default: return head;
        }
    }

    public AllDirections getDirection() {
        return currentAllDirections;
    }

    public void setDirection(AllDirections allDirections) {
        if (!isOpposite(allDirections)) {
            currentAllDirections = allDirections;
        }
    }

    public void move(boolean grow) {
        Point2D newHead = getNextHeadPosition();
        body.addFirst(newHead);
        if (!grow) {
            body.removeLast();
        }
    }

    public boolean isColliding(Point2D point) {
        return body.contains(point);
    }

    private boolean isOpposite(AllDirections newDir) {
        return (currentAllDirections == AllDirections.UP && newDir == AllDirections.DOWN) ||
                (currentAllDirections == AllDirections.DOWN && newDir == AllDirections.UP) ||
                (currentAllDirections == AllDirections.LEFT && newDir == AllDirections.RIGHT) ||
                (currentAllDirections == AllDirections.RIGHT && newDir == AllDirections.LEFT);
    }


    public Snake(Point2D startPosition) {
        body.add(startPosition);
    }

    public Point2D getHead() {
        return body.peekFirst();
    }

    public Deque<Point2D> getBody() {
        return body;
    }
}
