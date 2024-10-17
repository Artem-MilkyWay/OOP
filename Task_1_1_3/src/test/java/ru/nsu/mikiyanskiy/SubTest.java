package ru.nsu.mikiyanskiy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class SubTest {

    @Test
    public void testEval() {
        Expression expr = new Sub(new Number(10), new Number(3));
        assertEquals(7, expr.eval(new HashMap<>()));
    }

    @Test
    public void testPrint() {
        Expression expr = new Sub(new Number(10), new Variable("y"));
        assertEquals("(10 - y)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Sub(new Variable("x"), new Number(5));
        Expression der = expr.derivative("x");
        assertEquals("(1 - 0)", der.print());
    }
}
