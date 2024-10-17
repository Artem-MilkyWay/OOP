package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * класс самого выражения.
 */
abstract class Expression {
    /**
     * Метод для печати выражения.
     *
     * @return строка - выражение
     */
    public abstract String print();

    /**
     * Метод для взятия производной.
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    public abstract Expression derivative(String variable);

    /**
     * // Метод для вычисления выражения.
     *
     * @param variables - переменные  и их значения
     * @return полученное значение
     */
    public abstract int eval(Map<String, Integer> variables);
}
