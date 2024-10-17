package ru.nsu.mikiyanskiy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberTest {

    @Test
    public void testEval() {
        Expression num = new Number(5);
        assertEquals(5, num.eval(new HashMap<>()));
    }

    @Test
    public void testPrint() {
        Expression num = new Number(5);
        assertEquals("5", num.print());
    }

    @Test
    public void testDerivative() {
        Expression num = new Number(5);
        Expression der = num.derivative("x");
        assertEquals("0", der.print());
    }
}
