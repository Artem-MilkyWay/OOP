package ru.nsu.mikiyanskiy;
import java.util.Map;

class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        if (!variables.containsKey(name)) {
            throw new RuntimeException("Variable not defined: " + name);
        }
        return variables.get(name);
    }
}
