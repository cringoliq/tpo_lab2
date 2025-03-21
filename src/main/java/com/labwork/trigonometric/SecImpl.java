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

        return 1 / cos;
    }
}
