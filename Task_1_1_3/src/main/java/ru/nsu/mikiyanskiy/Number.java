package ru.nsu.mikiyanskiy;
import java.util.Map;

class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String print() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }
}
