package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * класс сложения.
 */
class Add extends Expression {
    private final Expression left, right;

    /**
     * конструктор класса сложения.
     *
     * @param left - левое выражение
     * @param right - правое выражение
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    /**
     * переопределение метода вывода выражения.
     *
     * @return выражение
     */
    @Override
    public String print() {
        return "(" + left.print() + " + " + right.print() + ")";
    }

    /**
     * переопределение метода взятия производной.
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    @Override
    public Expression derivative(String variable) {
        // Производная суммы: производная левого + производная правого
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * переопределение метода вычисления выражения.
     *
     * @param variables - переменные  и их значения
     * @return результат вычисления
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        // Вычисляем значение для левого и правого выражений и возвращаем их сумму
        return left.eval(variables) + right.eval(variables);
    }
}
