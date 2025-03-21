package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

import java.lang.module.Configuration;

public class SecImpl implements FunctionInterface {
    private final CosImpl cosImpl;

    public SecImpl() {
        this.cosImpl = new CosImpl();
    }

    public SecImpl(CosImpl cosImpl) {
        this.cosImpl = cosImpl;
    }

    @Override
    public double calculate(double x, double delta) {
        double cos = cosImpl.calculate(x, delta);
        if (Math.abs(cos) < delta) {
            throw new ArithmeticException("X can't be PI/2 * n");
        }
        return 1 / cos;
    }
}
