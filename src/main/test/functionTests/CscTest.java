package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.CscImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CscTest {

    // Задаём точность вычислений для тестов
    private final double DELTA = 0.00001;
    // Создаём экземпляр функции csc(x)
    private FunctionInterface cscFunction = new CscImpl();
    // Порог, выше которого считаем значение "бесконечным"
    private final double THRESHOLD = 1e4;


    /**
     * Тестирование базовых значений для csc(x).
     * Например, csc(π/2) = 1, csc(π/6) = 2, csc(π/3) = 1/sin(π/3).
     */
    @Test
    public void testCscBasicValues() {
        // csc(π/2) = 1/sin(π/2) = 1
        double x1 = Math.PI / 2;
        double expected1 = 1.0;
        double result1 = cscFunction.calculate(x1, DELTA);
        System.out.println("Testing csc(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("csc(π/2) должен быть 1", expected1, result1, DELTA);

        // csc(π/6) = 1/sin(π/6) = 1/(0.5) = 2
        double x2 = Math.PI / 6;
        double expected2 = 2.0;
        double result2 = cscFunction.calculate(x2, DELTA);
        System.out.println("Testing csc(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("csc(π/6) должен быть 2", expected2, result2, DELTA);

        // csc(π/3) = 1/sin(π/3)
        double x3 = Math.PI / 3;
        double expected3 = 1.0 / Math.sin(x3);
        double result3 = cscFunction.calculate(x3, DELTA);
        System.out.println("Testing csc(" + x3 + "): expected " + expected3 + ", actual " + result3);
        assertEquals("csc(π/3) должен быть " + expected3, expected3, result3, DELTA);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     * Для csc(x) справедливо: csc(-x) = -csc(x).
     */
    @Test
    public void testCscNegativeValues() {
        double x = -Math.PI / 2;
        double expected = -1.0;
        double result = cscFunction.calculate(x, DELTA);
        System.out.println("Testing csc(" + x + "): expected " + expected + ", actual " + result);
        assertEquals("csc(-π/2) должен быть -1", expected, result, DELTA);
    }

    /**
     * Тестирование нормализации аргументов.
     * Проверяется, что функция корректно обрабатывает аргументы вне стандартного интервала.
     * Например, csc(3π/2) = 1/sin(3π/2) = -1.
     */
    @Test
    public void testCscNormalization() {
        double x = 3 * Math.PI / 2;
        double expected = 1.0 / Math.sin(x); // sin(3π/2) = -1, => csc = -1
        double result = cscFunction.calculate(x, DELTA);
        System.out.println("Testing csc(" + x + "): expected " + expected + ", actual " + result);
        assertEquals("csc(3π/2) должен быть -1", expected, result, DELTA);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение csc(x)}
     * и проверяем вычисления для набора типовых точек.
     * Не включаются точки, где sin(x) = 0, поскольку csc(x) там не определён.
     */
    @Test
    public void testCscTabularValues() {
        double[][] table = {
                {Math.PI / 6, 1.0 / Math.sin(Math.PI / 6)},
                {Math.PI / 4, 1.0 / Math.sin(Math.PI / 4)},
                {Math.PI / 3, 1.0 / Math.sin(Math.PI / 3)},
                {Math.PI / 2, 1.0 / Math.sin(Math.PI / 2)},
                {-Math.PI / 6, 1.0 / Math.sin(-Math.PI / 6)},
                {-Math.PI / 4, 1.0 / Math.sin(-Math.PI / 4)},
                {-Math.PI / 3, 1.0 / Math.sin(-Math.PI / 3)},
                {-Math.PI / 2, 1.0 / Math.sin(-Math.PI / 2)}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = cscFunction.calculate(x, DELTA);
            System.out.println("Testing csc(" + x + "): expected " + expected + ", actual " + result);
            assertEquals("csc(" + x + ") должен быть " + expected, expected, result, DELTA);
        }
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение csc(x)}
     * и проверяем вычисления для набора типовых точек from CSV file.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/csc.csv", numLinesToSkip = 1)
    public void testCscFileSource(double x, double expected) {
        double result = cscFunction.calculate(x, DELTA);
        System.out.printf("Testing csc(%.6f): expected %.6f, actual %.6f%n", x, expected, result);
        assertEquals(expected, result, DELTA);
    }

    /**
     * Тестирование случая, когда функция не определена.
     * При x = 0 или x = π, sin(x) = 0, поэтому csc(x) = 1/0, что в Java даёт бесконечность.
     */
    @Test
    public void testCscUndefinedValue() {
        double x = 0.0;
        double result = cscFunction.calculate(x, DELTA);
        System.out.println("Testing csc(" + x + "): expected Infinity (or a value with |value| > " + THRESHOLD + "), actual " + result);
        // Проверяем, что результат либо Infinity, либо имеет модуль больше порогового значения
        assertTrue("csc(0) должен быть бесконечностью или очень большим по модулю",
                Double.isInfinite(result) || Math.abs(result) > THRESHOLD);

        x = Math.PI;
        result = cscFunction.calculate(x, DELTA);
        System.out.println("Testing csc(" + x + "): expected Infinity (or a value with |value| > " + THRESHOLD + "), actual " + result);
        assertTrue("csc(π) должен быть бесконечностью или очень большим по модулю",
                Double.isInfinite(result) || Math.abs(result) > THRESHOLD);
    }
}
