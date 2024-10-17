package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * класс деления.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * конструктор класса деления.
     *
     * @param left - левое выражение
     * @param right - правое выражение
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * переопределение метода вычисления выражения.
     *
     * @param variables - переменные  и их значения
     * @return результат вычисления
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        // Вычисляем значение для левого и правого выражений и возвращаем их частное
        int rightEval = right.eval(variables);
        if (rightEval == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return left.eval(variables) / rightEval;
    }

    /**
     * переопределение метода вывода выражения.
     *
     * @return выражение
     */
    @Override
    public String print() {
        // Печатаем выражение в виде (левый / правый)
        return "(" + left.print() + " / " + right.print() + ")";
    }

    /**
     * переопределение метода взятия производной.
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    @Override
    public Expression derivative(String variable) {
        // Производная частного: (u/v)' = (u'v - uv') / v^2
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right) // v^2
        );
    }
}
