package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

import java.util.function.Function;

public class TanImpl implements FunctionInterface {
    private final SinImpl sinImpl;
    private final CosImpl cosImpl;

    public TanImpl() {
        this.sinImpl = new SinImpl();
        this.cosImpl = new CosImpl();
    }

    public TanImpl(SinImpl sinImpl, CosImpl cosImpl) {
        this.sinImpl = sinImpl;
        this.cosImpl = cosImpl;
    }

    @Override
    public double calculate(double x, double delta) {
        double cos = cosImpl.calculate(x, delta);

        double sin = sinImpl.calculate(x, delta);
        return sin / cos;
    }
}
