package com.labwork.trigonometric;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import com.labwork.interfaces.FunctionInterface;

public class SinImpl implements FunctionInterface {

    private static final MathContext MC = new MathContext(34, RoundingMode.HALF_EVEN);
    private static final BigDecimal PI = new BigDecimal("3.141592653589793238462643383279", MC);
    private static final BigDecimal TWO_PI = new BigDecimal("6.283185307179586476925286766559", MC);

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal delta) {
        //нормализация данных
        BigDecimal normX = x.remainder(TWO_PI);
        if (normX.compareTo(PI) > 0) {
            normX = normX.subtract(TWO_PI, MC);
        } else if (normX.compareTo(PI.negate()) < 0) {
            normX = normX.add(TWO_PI, MC);
        }

        // Разложение в ряд Тейлора для sin:
        BigDecimal term = normX; // Первый член
        BigDecimal res = BigDecimal.ZERO;
        int i = 0;

        //итеративное вычисление res, пока не достигнем точности
        while (term.abs().compareTo(delta) > 0) {
            res = res.add(term, MC);
            i++;
            // term = term * (-1) * x^2 / ((2*i) * (2*i+1))
            BigDecimal xSquared = normX.multiply(normX, MC);
            BigDecimal denominator = new BigDecimal((2 * i) * (2 * i + 1), MC);
            term = term.multiply(xSquared.negate(), MC)
                    .divide(denominator, MC);
        }
        return res;
    }
}
