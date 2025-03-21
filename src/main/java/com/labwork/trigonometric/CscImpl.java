package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

public class CscImpl implements FunctionInterface {
    private final SinImpl sinImpl;

    public CscImpl() {
        this.sinImpl = new SinImpl();
    }

    public CscImpl(SinImpl sinImpl) {
        this.sinImpl = sinImpl;
    }

    @Override
    public double calculate(double x, double delta) {
        double sin = sinImpl.calculate(x, delta);
        if (Math.abs(sin) < delta) {
            throw new ArithmeticException("X can't be PI * n");
        }
        return 1 / sin;
    }
}
