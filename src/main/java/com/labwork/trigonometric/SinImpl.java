package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

public class SinImpl implements FunctionInterface {

    @Override
    public double calculate(final double x, final double delta) {
        // нормализация
        double normX = x % (2 * Math.PI);
        if (normX > Math.PI) {
            normX -= 2 * Math.PI;
        } else if (normX < -Math.PI) {
            normX += 2 * Math.PI;
        }

        // Разложение в ряд Тейлора для sin(x):
        // sin(x) = x - x^3/3! + x^5/5! - ...
        double term = normX; // Первый член
        double res = 0.0;
        int i = 0;

        while (Math.abs(term) > delta) {
            res += term;
            i++;
            // term = term * (-1) * normX^2 / ((2*i) * (2*i+1))
            term = term * -1 * normX * normX / ((2 * i) * (2 * i + 1));
        }
        return res;
    }
}
