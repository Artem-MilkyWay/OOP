package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * класс константы.
 */
class Number implements Expression {
    private final int value;

    /**
     * конструктор класса константы.
     *
     * @param value - само число
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * переопределение метода вывода выражения.
     *
     * @return выражение
     */
    @Override
    public String print() {
        return Integer.toString(value);
    }

    /**
     * переопределение метода взятия производной.
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * переопределение метода вычисления выражения.
     *
     * @param variables - переменные  и их значения
     * @return результат вычисления
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }
}
