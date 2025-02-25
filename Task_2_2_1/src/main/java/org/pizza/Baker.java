package org.pizza;

/**
 * Implementation of a single baker actions.
 */
public class Baker implements Runnable{
    private final int speedOfCooking;
    private final int id;
    public int currentOrderId;

    public Baker(int speedOfCooking, int id) {
        this.speedOfCooking = speedOfCooking;
        this.id = id;
    }

    /**
     * After execution thread starts again.
     */
    @Override
    public void run() {
        while (!Warehouse.isClosed()) {
            bakerCook();
        }
        System.out.println("Baker with id " + id + " has finished the working day");
    }

    /**
     * The cooking Process.
     */
    public void bakerCook() {
        try {
            // waiting for new order coming
            synchronized (OrderList.class) {
                while (OrderList.OrderListIsEmpty()) {
                    if (Warehouse.isClosed()) {
                        return;
                    }
                    System.out.println("Baker with id " + id + " is Waiting for the next order , Time: " + System.currentTimeMillis());
                    OrderList.class.wait(1000);
                }

                currentOrderId = OrderList.getLastOrder();
            }

            // if order has been successfully taken
            if (currentOrderId != 0) {
                System.out.println("[Order: " + currentOrderId + "]" + " [cooking] ," + " by baker id " + id + " , Time: "+ System.currentTimeMillis());
                Thread.sleep(1000L * speedOfCooking);
                System.out.println("[Order: " + currentOrderId + "]" + " [finished cooking] ," + " by baker id " + id + " , Time: " + System.currentTimeMillis());

                giveToWarehouse();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * To put finished pizza to the Warehouse.
     */
    private void giveToWarehouse() {
        synchronized (Warehouse.class) {
            if (!Warehouse.hasPlace()) {
                try {
                    Warehouse.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            Warehouse.putToWarehouse(currentOrderId);
            System.out.println("[Order: " + currentOrderId + "]" + " [transferred to the warehouse] , by baker with id " + id + " , Time: " + System.currentTimeMillis());
        }
    }
}

