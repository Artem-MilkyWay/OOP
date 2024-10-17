package ru.nsu.mikiyanskiy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTest {

    @Test
    public void testEval() {
        Expression expr = new Add(new Number(3), new Number(2));
        assertEquals(5, expr.eval(new HashMap<>()));
    }

    @Test
    public void testPrint() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        assertEquals("(3 + x)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Add(new Variable("x"), new Number(2));
        Expression der = expr.derivative("x");
        assertEquals("(1 + 0)", der.print());
    }
}
