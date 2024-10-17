package ru.nsu.mikiyanskiy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MulTest {

    @Test
    public void testEval() {
        Expression expr = new Mul(new Number(4), new Number(5));
        assertEquals(20, expr.eval(new HashMap<>()));
    }

    @Test
    public void testPrint() {
        Expression expr = new Mul(new Number(4), new Variable("x"));
        assertEquals("(4 * x)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Mul(new Variable("x"), new Number(3));
        Expression der = expr.derivative("x");
        assertEquals("((1 * 3) + (x * 0))", der.print());
    }
}
