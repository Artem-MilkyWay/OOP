package org.pizza;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Orders Queue.
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

    public static void printRemainingOrders() {
        System.out.println("Unprocessed orders that remained in the queue: " + orderList);
    }

    /**
     * Checking for orders existence.
     *
     * @return true if queue is empty
     */
    public static boolean orderListIsEmpty() {
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
