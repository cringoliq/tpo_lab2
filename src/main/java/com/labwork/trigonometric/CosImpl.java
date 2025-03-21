package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

public class CosImpl implements FunctionInterface {

    private final SinImpl sinImpl = new SinImpl();

    @Override
    public double calculate(final double x, final double delta) {
        // Используем тригонометрическое тождество: cos(x) = sin(π/2 - x)
        double arg = (Math.PI / 2) - x;
        return sinImpl.calculate(arg, delta);
    }
}
