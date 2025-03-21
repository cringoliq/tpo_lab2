package com.labwork.trigonometric;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import com.labwork.interfaces.FunctionInterface;

public class CosImpl implements FunctionInterface {

    private static final MathContext MC = new MathContext(34, RoundingMode.HALF_EVEN);
    // Значение π/2 с необходимой точностью
    private static final BigDecimal HALF_PI = new BigDecimal("1.5707963267948966192313216916398", MC);

    // Используем существующую реализацию синуса
    private final SinImpl sinImpl = new SinImpl();

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal delta) {
        // Тождество: cos(x) = sin(π/2 - x)
        BigDecimal arg = HALF_PI.subtract(x, MC);
        return sinImpl.calculate(arg, delta);
    }
}
