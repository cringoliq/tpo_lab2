package com.labwork.logarithms;

import com.labwork.interfaces.FunctionInterface;

public class Log3Impl implements FunctionInterface {
    private final LnImpl ln;

    public Log3Impl() {
        this.ln = new LnImpl();
    }
    public Log3Impl(LnImpl ln) {
        this.ln = ln;
    }


    @Override
    public double calculate(double x, double epsilon) {
        if (x <= 0) {
            throw new IllegalArgumentException("x must be greater than zero");
        }
        double base = 3;
        return ln.calculate(x, epsilon) / ln.calculate(base, epsilon);
    }
}
