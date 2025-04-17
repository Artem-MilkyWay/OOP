package org.pizza;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class implements handling of the entire process.
 */
public class OrderHandler {
    private final List<Thread> bakerThreads;
    private final List<Thread> courierThreads;

    private OrderHandler(List<Thread> bakerThreads, List<Thread> courierThreads) {
        this.bakerThreads = bakerThreads;
        this.courierThreads = courierThreads;
    }

    /**
     * The constructor that match elements from
     json to lists and extract a capacity of the Warehouse.
     *
     * @param configFilePath path to the json file.
     */
    public static OrderHandler build(String configFilePath) {
        Parser parser = new Parser(configFilePath);
        parser.initializeWarehouse();
        return new OrderHandler(parser.parseBakers(), parser.parseCouriers());
    }

    /**
     * To make all Bakers and Couriers run.
     */
    public void startProcess() {
        for (Thread baker : bakerThreads) {
            baker.start();
        }

        for (Thread courier : courierThreads) {
            courier.start();
        }
    }

    /**
     * The process of a new order.
     *
     * @param orderId number of the order
     */
    public synchronized void processOrder(int orderId) {
        OrderList.newOrder(orderId);
    }

    /**
     * To make all threads finished.
     *
     * @throws InterruptedException error with thread finishing
     */
    public void end() throws InterruptedException {
        Warehouse.close();

        for (Thread baker : bakerThreads) {
            baker.join();
        }

        for (Thread courier : courierThreads) {
            courier.join();
        }


        Warehouse.printRemainingOrders();
        OrderList.printRemainingOrders();
    }
}
