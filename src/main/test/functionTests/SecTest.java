package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.SecImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;

public class SecTest {

    // Задаём точность вычислений для тестов
    private final double DELTA = 1e-6;
    // Создаём экземпляр функции sec(x)
    private final FunctionInterface secFunction = new SecImpl();

    /**
     * Тестирование базовых значений для sec(x).
     * Например, sec(0) = 1, sec(π/3) = 2, sec(π) = -1.
     */
    @Test
    public void testSecBasicValues() {
        // sec(0) = 1/cos(0) = 1
        double x0 = 0.0;
        double expected0 = 1.0;
        double result0 = secFunction.calculate(x0, DELTA);
        System.out.println("Testing sec(" + x0 + "): expected " + expected0 + ", actual " + result0);
        assertEquals("sec(0) должен быть 1", expected0, result0, DELTA);

        // sec(π/3) = 1/cos(π/3) = 1/(0.5) = 2
        double x1 = Math.PI / 3;
        double expected1 = 1.0 / Math.cos(x1);
        double result1 = secFunction.calculate(x1, DELTA);
        System.out.println("Testing sec(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sec(π/3) должен быть 2", expected1, result1, DELTA);

        // sec(π) = 1/cos(π) = 1/(-1) = -1
        double x2 = Math.PI;
        double expected2 = 1.0 / Math.cos(x2);
        double result2 = secFunction.calculate(x2, DELTA);
        System.out.println("Testing sec(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sec(π) должен быть -1", expected2, result2, DELTA);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     * Поскольку cos(x) – чётная функция, sec(x) также чётна: sec(-x) = sec(x).
     */
    @Test
    public void testSecNegativeValues() {
        double x = -Math.PI / 3;
        double expected = 1.0 / Math.cos(x); // должно быть равно sec(π/3)
        double result = secFunction.calculate(x, DELTA);
        System.out.println("Testing sec(" + x + "): expected " + expected + ", actual " + result);
        assertEquals("sec(-π/3) должен быть равен sec(π/3)", expected, result, DELTA);
    }

    /**
     * Тестирование нормализации аргументов.
     * Например, sec(3π) = sec(π) = -1, поскольку cos(3π)=cos(π)= -1.
     */
    @Test
    public void testSecNormalization() {
        double x1 = 3 * Math.PI;
        double expected1 = 1.0 / Math.cos(x1);
        double result1 = secFunction.calculate(x1, DELTA);
        System.out.println("Testing sec(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sec(3π) должен быть равен sec(π)", expected1, result1, DELTA);

        double x2 = -3 * Math.PI;
        double expected2 = 1.0 / Math.cos(x2);
        double result2 = secFunction.calculate(x2, DELTA);
        System.out.println("Testing sec(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sec(-3π) должен быть равен sec(π)", expected2, result2, DELTA);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение sec(x)}
     * и проверяем вычисления для набора типовых точек.
     */
    @Test
    public void testSecTabularValues() {
        double[][] table = {
                {0.0, 1.0 / Math.cos(0.0)},
                {Math.PI / 6, 1.0 / Math.cos(Math.PI / 6)},
                {Math.PI / 4, 1.0 / Math.cos(Math.PI / 4)},
                {Math.PI / 3, 1.0 / Math.cos(Math.PI / 3)},
                {Math.PI / 2, 1.0 / Math.cos(Math.PI / 2)}, // cos(π/2)=0, sec = Infinity
                {Math.PI, 1.0 / Math.cos(Math.PI)},
                {-Math.PI / 6, 1.0 / Math.cos(-Math.PI / 6)},
                {-Math.PI / 4, 1.0 / Math.cos(-Math.PI / 4)},
                {-Math.PI / 2, 1.0 / Math.cos(-Math.PI / 2)}, // sec = Infinity
                {-Math.PI, 1.0 / Math.cos(-Math.PI)}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = secFunction.calculate(x, DELTA);
            System.out.println("Testing sec(" + x + "): expected " + expected + ", actual " + result);
            if (Double.isInfinite(expected)) {
                assertTrue("sec(" + x + ") должен быть бесконечностью", Double.isInfinite(result));
            } else {
                assertEquals("sec(" + x + ") должен быть " + expected, expected, result, DELTA);
            }
        }
    }

    /**
     * Тестирование случая, когда функция не определена.
     * При x, где cos(x)=0 (например, x = π/2 или x = 3π/2), sec(x) = 1/0,
     * что в Java даёт бесконечность.
     */
    @Test
    public void testSecUndefinedValues() {
        double x = Math.PI / 2;
        double result = secFunction.calculate(x, DELTA);
        System.out.println("Testing sec(" + x + "): expected Infinity, actual " + result);
        assertTrue("sec(π/2) должен быть бесконечностью", Double.isInfinite(result));

        x = 3 * Math.PI / 2;
        result = secFunction.calculate(x, DELTA);
        System.out.println("Testing sec(" + x + "): expected Infinity, actual " + result);
        assertTrue("sec(3π/2) должен быть бесконечностью", Double.isInfinite(result));
    }
}
