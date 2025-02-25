import org.junit.jupiter.api.Test;
import org.pizza.OrderHandler;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Testing the entire system.
 */
public class PizzaTest {

    /**
     * Main testing function.
     */
    @Test
    public void pizzaTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("pizzeria.json");

        // Checking if resource has been found
        if (resource != null) {
            String filePath = Paths.get(resource.getPath()).toString();
            OrderHandler handler = new OrderHandler(filePath);

            //To open pizzeria
            handler.startProcess();

            //Making orders
            for (int i = 0; i < 65; i++) {
                handler.processOrder(100 + i);
            }

            //Pizzeria working time
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //To close pizzeria
            handler.end();
        } else {
            System.out.println("Json file has not been found");
        }
    }
}
