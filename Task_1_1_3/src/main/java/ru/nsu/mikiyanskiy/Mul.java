package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * класс произведения.
 */
class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * конструктор класса произведения.
     *
     * @param left - левое выражение
     * @param right - правое выражение
     */
    public Mul(Expression left, Expression right) {
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
        return "(" + left.print() + " * " + right.print() + ")";
    }

    /**
     * переопределение метода взятия производной.
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    /**
     * переопределение метода вычисления выражения.
     *
     * @param variables - переменные  и их значения
     * @return результат вычисления
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }
}
