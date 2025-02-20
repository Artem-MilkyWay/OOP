package org.pizza;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of pizza storage.
 */
public class Warehouse {
    private static int capacity;
    private static int currentAmount = 0;
    private static final Queue<Integer> warehouse = new LinkedList<>();

    public static void initializeWarehouse(int capacity) {
        Warehouse.capacity = capacity;
    }

    public static boolean hasPlace() {
        return currentAmount < capacity;
    }

    /**
     * Checking for the pizza existence.
     *
     * @return true if pizza does not exist.
     */
    public static boolean isEmpty() {
        return currentAmount == 0;
    }

    /**
     * Put a pizza at the Warehouse after finishing cooking process.
     * Notify a courier that storage is not empty.
     *
     * @param orderId number of order.
     */
    public static synchronized void putToWarehouse(int orderId) {
        Warehouse.currentAmount++;
        warehouse.offer(orderId);

        Warehouse.class.notify();
    }

    /**
     * Take one or more pizzas from storage ( if bag allows ).
     * Notify Baker that storage has a new place for order placement.
     *
     * @param amount - bag capacity
     * @return all orders that could be taken by courier
     */
    public static synchronized List<Integer> getFromWarehouse(int amount)
    {
        List<Integer> bag = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            if (!Warehouse.isEmpty()) {
                bag.add(warehouse.poll());
                Warehouse.currentAmount--;
            }
            else {
                break;
            }
        }

        Warehouse.class.notify();
        return bag;
    }
}
