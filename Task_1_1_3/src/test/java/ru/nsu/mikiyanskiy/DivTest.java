package ru.nsu.mikiyanskiy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тесты для класса деления
 */
public class DivTest {

    /**
     * тест для вычисления выражения
     */
    @Test
    public void testEval() {
        Expression expr = new Div(new Number(10), new Number(2));
        assertEquals(5, expr.eval(new HashMap<>()));
    }

    /**
     * тест для вывода выражения
     */
    @Test
    public void testPrint() {
        Expression expr = new Div(new Number(10), new Variable("x"));
        assertEquals("(10 / x)", expr.print());
    }

    /**
     * тест для взятие производной
     */
    @Test
    public void testDerivative() {
        Expression expr = new Div(new Variable("x"), new Number(5));
        Expression der = expr.derivative("x");
        assertEquals("(((1 * 5) - (x * 0)) / (5 * 5))", der.print());
    }
}
