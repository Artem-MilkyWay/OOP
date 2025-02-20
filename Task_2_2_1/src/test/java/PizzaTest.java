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

        // Проверка, найден ли ресурс
        if (resource != null) {
            String filePath = Paths.get(resource.getPath()).toString();
            OrderHandler handler = new OrderHandler(filePath);
            handler.start();
            for (int i = 0; i < 10; i++) {
                handler.processOrder(100 + i);
            }
            try {
                handler.end();
            } catch (InterruptedException e) {
                throw new RuntimeException("Error with thread finishing " + e);
            }
        } else {
            System.out.println("Json file has not been found");
        }
    }
}
