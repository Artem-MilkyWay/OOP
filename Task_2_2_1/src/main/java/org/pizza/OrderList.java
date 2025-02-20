package org.pizza;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Orders Queue
 */
public class OrderList {
    private final static Queue<Integer> orderList = new LinkedList<>();

    /**
     * to add order to the queue.
     * notify the baker to start new cooking process.
     *
     * @param orderId - number of single order
     */
    public static void newOrder(int orderId) {
        synchronized (OrderList.class) {
            orderList.offer(orderId);
            OrderList.class.notify();
        }
    }

    /**
     * Checking for orders existence.
     *
     * @return true if queue is empty
     */
    public static boolean OrderListIsEmpty() {
        return orderList.isEmpty();
    }

    /**
     * To take the oldest order from queue.
     *
     * @return id number of the order
     */
    public static int getLastOrder() {
        return !orderList.isEmpty() ? orderList.poll() : 0;
    }
}
