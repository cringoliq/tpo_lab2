package com.labwork.trigonometric;

import com.labwork.interfaces.FunctionInterface;

public class SecImpl implements FunctionInterface {

    private final CosImpl cosImpl;

    // Порог, при котором считаем, что cos(x) ≈ 0, а значит sec(x) → ±∞
    // Можно взять, например, 1e-15 или 1e-14 — зависит от желаемой "чувствительности".
    private static final double EPS = 1e-8;

    public SecImpl() {
        this.cosImpl = new CosImpl();
    }

    public SecImpl(CosImpl cosImpl) {
        this.cosImpl = cosImpl;
    }

    @Override
    public double calculate(double x, double delta) {
        // Сначала вычисляем cos(x)
        double cosValue = cosImpl.calculate(x, delta);

        // Если cos(x) слишком мал по модулю, считаем, что sec(x) уходит в ±∞
        if (Math.abs(cosValue) < EPS) {
            // смотрим знак cosValue — если отрицательный, то выдаём -∞, иначе +∞
            return (cosValue >= 0.0)
                    ? Double.POSITIVE_INFINITY
                    : Double.NEGATIVE_INFINITY;
        }

        // Иначе возвращаем 1/cos(x), как обычно
        return 1.0 / cosValue;
    }
}
