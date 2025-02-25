import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import java.net.URL;
import org.pizza.OrderHandler;

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
            for (int i = 0; i < 20; i++) {
                handler.processOrder(100 + i);
            }

            //Pizzeria working time
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //To close pizzeria
            try {
                handler.end();
            } catch (InterruptedException e) {
                System.out.println("Error with threads finishing " + e.getMessage());
            }
        } else {
            System.out.println("Json file has not been found");
        }
    }
}
