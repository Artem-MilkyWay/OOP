package ru.nsu.mikiyanskiy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DivTest {

    @Test
    public void testEval() {
        Expression expr = new Div(new Number(10), new Number(2));
        assertEquals(5, expr.eval(new HashMap<>()));
    }

    @Test
    public void testPrint() {
        Expression expr = new Div(new Number(10), new Variable("x"));
        assertEquals("(10 / x)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Div(new Variable("x"), new Number(5));
        Expression der = expr.derivative("x");
        assertEquals("(((1 * 5) - (x * 0)) / (5 * 5))", der.print());
    }
}

