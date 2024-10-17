package ru.nsu.mikiyanskiy;

import java.util.Map;

/**
 * класс переменной
 */
class Variable extends Expression {
    private final String name;

    /**
     * конструктор переменной
     *
     * @param name - имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * переопределение метода вывода выражения
     *
     * @return выражение
     */
    @Override
    public String print() {
        return name;
    }

    /**
     * переопределение метода взятия производной
     *
     * @param variable - переменная по которой берется производная
     * @return выражение после взятия производной
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    /**
     * переопределение метода вычисления выражения
     *
     * @param variables - переменные  и их значения
     * @return результат вычисления
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        if (!variables.containsKey(name)) {
            throw new RuntimeException("Variable not defined: " + name);
        }
        return variables.get(name);
    }
}
