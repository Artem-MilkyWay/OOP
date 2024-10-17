package ru.nsu.mikiyanskiy;
import java.util.Map;

class Sub extends Expression {
    private final Expression left, right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        // Производная разности: производная левого - производная правого
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        // Вычисляем значение для левого и правого выражений и возвращаем их разность
        return left.eval(variables) - right.eval(variables);
    }
}
