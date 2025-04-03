package functionTests;

import static org.junit.Assert.assertEquals;

import com.labwork.trigonometric.SinImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SinTest {

    // Точность, которую мы передаём в функцию calculate() — контролирует внутренние вычисления
    private static final double CALC_DELTA = 1e-8;

    // Точность, с которой мы сравниваем результат и ожидаемое значение в assertEquals
    private static final double TEST_TOLERANCE = 1e-3;

    // Создаём экземпляр функции sin(x)
    private final FunctionInterface sinFunction = new SinImpl();

    /**
     * Тестирование базовых значений, лежащих в допустимой области.
     */
    @Test
    public void testSinBasicValues() {
        // sin(0) = 0
        double x0 = 0.0;
        double expected0 = 0.0;
        // Передаём CALC_DELTA внутрь calculate
        double result0 = sinFunction.calculate(x0, CALC_DELTA);
        System.out.println("Testing sin(" + x0 + "): expected " + expected0 + ", actual " + result0);
        // Сравниваем с TEST_TOLERANCE
        assertEquals("sin(0) должен быть 0", expected0, result0, TEST_TOLERANCE);

        // sin(π/6) = 0.5
        double x1 = Math.PI / 6;
        double expected1 = 0.5;
        double result1 = sinFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing sin(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sin(π/6) должен быть 0.5", expected1, result1, TEST_TOLERANCE);

        // sin(π/4) = sqrt(2)/2
        double x2 = Math.PI / 4;
        double expected2 = Math.sqrt(2) / 2;
        double result2 = sinFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing sin(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sin(π/4) должен быть sqrt(2)/2", expected2, result2, TEST_TOLERANCE);

        // sin(π/2) = 1
        double x3 = Math.PI / 2;
        double expected3 = 1.0;
        double result3 = sinFunction.calculate(x3, CALC_DELTA);
        System.out.println("Testing sin(" + x3 + "): expected " + expected3 + ", actual " + result3);
        assertEquals("sin(π/2) должен быть 1", expected3, result3, TEST_TOLERANCE);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     */
    @Test
    public void testSinNegativeValues() {
        // sin(-π/6) = -0.5
        double x1 = -Math.PI / 6;
        double expected1 = -0.5;
        double result1 = sinFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing sin(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sin(-π/6) должен быть -0.5", expected1, result1, TEST_TOLERANCE);

        // sin(-π/4) = -sqrt(2)/2
        double x2 = -Math.PI / 4;
        double expected2 = -Math.sqrt(2) / 2;
        double result2 = sinFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing sin(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sin(-π/4) должен быть -sqrt(2)/2", expected2, result2, TEST_TOLERANCE);
    }

    /**
     * Тестирование нормализации значений. Функция должна корректно работать,
     * даже если x находится вне интервала [-π, π].
     */
    @Test
    public void testSinNormalization() {
        // Для x = 3π: 3π % (2π) = π, sin(π) = 0
        double x1 = 3 * Math.PI;
        double expected1 = 0.0;
        double result1 = sinFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing sin(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sin(3π) должен быть 0 после нормализации", expected1, result1, TEST_TOLERANCE);

        // Для x = -3π: то же самое -> sin(π) = 0
        double x2 = -3 * Math.PI;
        double expected2 = 0.0;
        double result2 = sinFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing sin(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sin(-3π) должен быть 0 после нормализации", expected2, result2, TEST_TOLERANCE);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение}
     * и проверяем вычисления для набора типовых точек.
     */
    @Test
    public void testSinTabularValues() {
        // Таблица значений: { x, ожидаемое значение sin(x) }
        double[][] table = {
                {0.0,           0.0},
                {Math.PI / 6,   0.5},
                {Math.PI / 4,   Math.sqrt(2) / 2},
                {Math.PI / 3,   Math.sin(Math.PI / 3)},
                {Math.PI / 2,   1.0},
                {Math.PI,       0.0},
                {-Math.PI / 6,  -0.5},
                {-Math.PI / 4,  -Math.sqrt(2) / 2},
                {-Math.PI / 2,  -1.0},
                {-Math.PI,      0.0}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = sinFunction.calculate(x, CALC_DELTA);
            System.out.println("Testing sin(" + x + "): expected " + expected + ", actual " + result);
            assertEquals("sin(" + x + ") должен быть " + expected, expected, result, TEST_TOLERANCE);
        }
    }

    /**
     * Тестирование табличных значений из CSV-файла: sin.csv
     * Формат CSV: первая строка — заголовок, а затем пары (x, expected).
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/sin.csv", numLinesToSkip = 1)
    public void testSinFileSource(double x, double expected) {
        // Вызываем функцию с CALC_DELTA
        double result = sinFunction.calculate(x, CALC_DELTA);
        System.out.printf("Testing sin(%.6f): expected %.6f, actual %.6f%n", x, expected, result);
        // Сравниваем с TEST_TOLERANCE
        assertEquals(expected, result, TEST_TOLERANCE);
    }
}
