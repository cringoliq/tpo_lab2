package com.labwork.logarithms;

import com.labwork.interfaces.FunctionInterface;

public class LogImpl implements FunctionInterface {
    private final LnImpl ln;
    private final double base;

    public LogImpl(double base) {
        this.ln = new LnImpl();
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Base must be greater than zero and not equal to 1");
        }
        this.base = base;
    }

    public LogImpl(final LnImpl ln, double base) {
        this.ln = ln;
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Base must be greater than zero and not equal to 1");
        }
        this.base = base;
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (x <= 0) {
            throw new IllegalArgumentException("x must be greater than zero");
        }
        return ln.calculate(x, epsilon) / ln.calculate(base, epsilon);
    }
}
