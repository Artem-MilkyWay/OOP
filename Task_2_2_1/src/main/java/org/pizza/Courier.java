package org.pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a single courier actions.
 */
public class Courier implements Runnable{
    private final int bagSize;
    private final int id;
    private List<Integer> bag = new ArrayList<>();

    public Courier(int bagSize, int id) {
        this.bagSize = bagSize;
        this.id = id;
    }

    /**
     * After every execution thread starts again.
     */
    @Override
    public void run() {
        while (true) {
            takeFromWarehouse();
            deliver();
        }
    }

    /**
     * To take some orders from Orders Queue.
     * If Queue is empty then waiting for notification from Warehouse.
     */
    public void takeFromWarehouse() {
        synchronized (Warehouse.class) {
            while (Warehouse.isEmpty()) {
                try {
                    System.out.println("courier " + id + " is Waiting for the new delivering , Time: " + System.currentTimeMillis());
                    Warehouse.class.wait(); // Ожидание, если склад пуст
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Установим флаг прерывания
                    return;
                }
            }

            bag = Warehouse.getFromWarehouse(bagSize);
        }
    }

    /**
     * The delivering process.
     */
    public void deliver() {
        for (int order : bag) {
            System.out.println("[Order: " + order + "]" + " [delivering] ," + " by Courier with id " + id + " , Time: " + System.currentTimeMillis());
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[Order: " + order + "]" + " [delivered to customer] ," + " by Courier with id " + id + " , Time: " + System.currentTimeMillis());
        }
    }
}
