package org.pizza;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class implements handling of the entire process.
 */
public class OrderHandler {
    private final List<Thread> bakerThreads = new ArrayList<>();
    private final List<Thread> courierThreads = new ArrayList<>();

    /**
     * The constructor that match elements from json to lists and extract a capacity of the Warehouse.
     *
     * @param configFilePath path to the json file.
     */
    public OrderHandler(String configFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode configNode = objectMapper.readTree(new File(configFilePath));

            // Инициализация склада
            Warehouse.initializeWarehouse(configNode.get("warehouseCapacity").asInt());

            // Baker parsing
            Iterator<JsonNode> bakerIterator = configNode.get("bakers").elements();
            while (bakerIterator.hasNext()) {
                JsonNode bakerNode = bakerIterator.next();
                int id = bakerNode.get("id").asInt();
                int speedOfCooking = bakerNode.get("speedOfCooking").asInt();
                bakerThreads.add(new Thread(new Baker (speedOfCooking, id)));
            }

            // Courier parsing
            Iterator<JsonNode> courierIterator = configNode.get("couriers").elements();
            while (courierIterator.hasNext()) {
                JsonNode courierNode = courierIterator.next();
                int id = courierNode.get("id").asInt();
                int bagSize = courierNode.get("bagSize").asInt();
                courierThreads.add(new Thread(new Courier(bagSize, id)));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with thread waiting " + e.getMessage());
        }
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
