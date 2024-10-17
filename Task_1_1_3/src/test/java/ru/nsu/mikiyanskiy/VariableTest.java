package ru.nsu.mikiyanskiy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * тесты для класса переменной
 */
public class VariableTest {

    /**
     * тест для вычисления выражения
     */
    @Test
    public void testEval() {
        Expression var = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(10, var.eval(variables));
    }

    /**
     * тест для вывода выражения
     */
    @Test
    public void testPrint() {
        Expression var = new Variable("x");
        assertEquals("x", var.print());
    }

    /**
     * тест для взятие производной
     */
    @Test
    public void testDerivative() {
        Expression var = new Variable("x");
        Expression der = var.derivative("x");
        assertEquals("1", der.print());

        der = var.derivative("y");
        assertEquals("0", der.print());
    }
}
