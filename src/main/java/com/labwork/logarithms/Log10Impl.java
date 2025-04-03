package com.labwork.logarithms;

import com.labwork.interfaces.FunctionInterface;

public class Log10Impl implements FunctionInterface {
    private final LnImpl ln;

    public Log10Impl() {
        this.ln = new LnImpl();
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (x <= 0) {
            throw new IllegalArgumentException("x must be greater than zero");
        }
        double base = 10;
        return ln.calculate(x, epsilon) / ln.calculate(base, epsilon);
    }
}
