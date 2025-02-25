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
        while (!Warehouse.isClosed()) {
            takeNewOrder();
        }
        System.out.println("Courier with id " + id + " has finished the working day");
    }

    /**
     * To take some orders from Orders Queue.
     * If Queue is empty then waiting for notification from Warehouse.
     */
    public void takeNewOrder() {
        synchronized (Warehouse.class) {
            while (Warehouse.isEmpty()) {
                if (Warehouse.isClosed()) {
                    return;
                }

                try {
                    Warehouse.class.wait(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
        }
        bag = Warehouse.getFromWarehouse(bagSize);
        deliver(); // to deliver all orders from bag
    }

    /**
     * The delivering process.
     */
    public void deliver() {
        for (int order : bag) {
            System.out.println("[Order: " + order + "]" + " [delivering] ," + " by Courier with id " + id + " , Time: " + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[Order: " + order + "]" + " [delivered to customer] ," + " by Courier with id " + id + " , Time: " + System.currentTimeMillis());
        }
    }
}
