package org.pizza;

import java.util.List;

/**
 * For json parsing.
 */
public class PizzaConfig {
    private List<Baker> bakers;
    private List<Courier> couriers;
    private int warehouseCapacity;


    public List<Baker> getBakers() {
        return bakers;
    }
    public void setBakers(List<Baker> bakers) {
        this.bakers = bakers;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }
    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public void setWarehouseCapacity(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }
}
