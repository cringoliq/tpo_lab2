package com.labwork.logarithms;

import com.labwork.interfaces.FunctionInterface;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class LnImpl implements FunctionInterface {

    @Override
    public double calculate(final double x, final double delta) {
        if (x <= 0) {  // x <= 0
            throw new ArithmeticException("x must be more than zero");
        }
        if (delta <= 0 || delta >= 1) {
            throw new ArithmeticException("delta must be more than 0 and less than 1");
        }

        double one = 1.0;
        double y = (x - one) / (x + one); // (x - 1) / (x + 1)
        double sum = 0.0;
        double term = y;
        int n = 1;

        while (Math.abs(term) > delta) {  // Пока abs(term) > precision
            sum += term / n;  // sum += term / n
            term *= y * y;  // term *= y^2
            n += 2;
        }

        return sum * 2.0; // Умножаем на 2
    }
}

