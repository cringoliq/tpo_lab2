package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

public class CotImpl implements FunctionInterface {
    private final SinImpl sinImpl;
    private final CosImpl cosImpl;

    public CotImpl(SinImpl sinImpl, CosImpl cosImpl) {
        this.sinImpl = sinImpl;
        this.cosImpl = cosImpl;
    }

    public CotImpl() {
        this.sinImpl = new SinImpl();
        this.cosImpl = new CosImpl();
    }

    @Override
    public double calculate(double x, double delta) {
        double sin = sinImpl.calculate(x, delta);
        if (Math.abs(sin) < delta) {
            throw new ArithmeticException("X can't be PI * n");
        }
        double cos = cosImpl.calculate(x, delta);
        return cos / sin;
    }
}
