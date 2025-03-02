package org.pizza;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode configNode;

    public Parser(String configFilePath) {
        try {
            configNode = objectMapper.readTree(new File(configFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Warehouse initialization
    public void initializeWarehouse() {
        Warehouse.initializeWarehouse(configNode.get("warehouseCapacity").asInt());
    }

    // Bakers Parsing
    public List<Thread> parseBakers() {
        List<Thread> bakerThreads = new ArrayList<>();
        Iterator<JsonNode> bakerIterator = configNode.get("bakers").elements();
        while (bakerIterator.hasNext()) {
            JsonNode bakerNode = bakerIterator.next();
            int id = bakerNode.get("id").asInt();
            int speedOfCooking = bakerNode.get("speedOfCooking").asInt();
            bakerThreads.add(new Thread(new Baker(speedOfCooking, id)));
        }
        return bakerThreads;
    }

    // Couriers Parsing
    public List<Thread> parseCouriers() {
        List<Thread> courierThreads = new ArrayList<>();
        Iterator<JsonNode> courierIterator = configNode.get("couriers").elements();
        while (courierIterator.hasNext()) {
            JsonNode courierNode = courierIterator.next();
            int id = courierNode.get("id").asInt();
            int bagSize = courierNode.get("bagSize").asInt();
            courierThreads.add(new Thread(new Courier(bagSize, id)));
        }
        return courierThreads;
    }
}
