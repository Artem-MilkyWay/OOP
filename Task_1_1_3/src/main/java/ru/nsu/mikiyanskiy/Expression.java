package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * Интерфейс для выражения.
 */
public interface Expression {
    /**
     * Метод для печати выражения.
     *
     * @return строка - выражение
     */
    String print();

    /**
     * Метод для взятия производной.
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    Expression derivative(String variable);

    /**
     * Метод для вычисления выражения.
     *
     * @param variables - переменные и их значения
     * @return полученное значение
     */
    int eval(Map<String, Integer> variables);
}
