package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.CotImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CotTest {

    // Точность для внутренней реализации тригонометрических вычислений (глубина разложения и т.п.)
    private static final double CALC_DELTA = 1e-8;

    // Точность, с которой мы сравниваем фактический результат с ожидаемым в тестах
    private static final double TEST_TOLERANCE = 1e-3;

    private final FunctionInterface cotFunction = new CotImpl();

    /**
     * Тестирование базовых значений для cot(x).
     * Например, cot(π/4) = 1, cot(π/6) = √3, cot(π/3) ≈ 0.57735.
     */
    @Test
    public void testCotBasicValues() {
        // Пример: cot(π/4) = cos(π/4)/sin(π/4) = 1
        double x1 = Math.PI / 4;
        double expected1 = 1.0;

        // Передаём CALC_DELTA в calculate
        double result1 = cotFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing cot(" + x1 + "): expected " + expected1 + ", actual " + result1);

        // Сравниваем с TEST_TOLERANCE
        assertEquals("cot(π/4) должен быть 1", expected1, result1, TEST_TOLERANCE);

        // Пример: cot(π/6) = (√3/2) / (1/2) = √3
        double x2 = Math.PI / 6;
        double expected2 = Math.cos(x2) / Math.sin(x2);
        double result2 = cotFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing cot(" + x2 + "): expected " + expected2 + ", actual " + result2);

        assertEquals("cot(π/6) должен быть √3", expected2, result2, TEST_TOLERANCE);

        // Пример: cot(π/3) = (1/2)/(√3/2) ≈ 0.57735
        double x3 = Math.PI / 3;
        double expected3 = Math.cos(x3) / Math.sin(x3);
        double result3 = cotFunction.calculate(x3, CALC_DELTA);
        System.out.println("Testing cot(" + x3 + "): expected " + expected3 + ", actual " + result3);

        assertEquals("cot(π/3) должен быть примерно 0.57735", expected3, result3, TEST_TOLERANCE);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     * По свойству нечётности, cot(-x) = -cot(x).
     */
    @Test
    public void testCotNegativeValues() {
        double x = -Math.PI / 4;
        double expected = Math.cos(x) / Math.sin(x); // должно быть -1

        double result = cotFunction.calculate(x, CALC_DELTA);
        System.out.println("Testing cot(" + x + "): expected " + expected + ", actual " + result);

        assertEquals("cot(-π/4) должен быть -1", expected, result, TEST_TOLERANCE);
    }

    /**
     * Тестирование нормализации аргументов.
     * Поскольку тригонометрические функции периодичны, проверяем, что функция корректно
     * обрабатывает аргументы, выходящие за основной период.
     */
    @Test
    public void testCotNormalization() {
        // Например: cot(5π/4) = cot(π + π/4). Теоретически должно совпасть с cot(π/4).
        double x = 5 * Math.PI / 4;
        double expected = Math.cos(x) / Math.sin(x);

        double result = cotFunction.calculate(x, CALC_DELTA);
        System.out.println("Testing cot(" + x + "): expected " + expected + ", actual " + result);

        assertEquals("cot(5π/4) должен быть равен cot(π/4)", expected, result, TEST_TOLERANCE);
    }

    /**
     * Тестирование табличных значений.
     * Задаём массив пар { x, ожидаемое значение } и проверяем вычисления для типовых точек.
     * Важно не включать точки, где sin(x) = 0 (например, x = 0, π, ...), поскольку cot(x) там не определён.
     */
    @Test
    public void testCotTabularValues() {
        double[][] table = {
                {Math.PI / 6,  Math.cos(Math.PI / 6)  / Math.sin(Math.PI / 6)},
                {Math.PI / 4,  Math.cos(Math.PI / 4)  / Math.sin(Math.PI / 4)},
                {Math.PI / 3,  Math.cos(Math.PI / 3)  / Math.sin(Math.PI / 3)},
                {-Math.PI / 6, Math.cos(-Math.PI / 6) / Math.sin(-Math.PI / 6)},
                {-Math.PI / 4, Math.cos(-Math.PI / 4) / Math.sin(-Math.PI / 4)},
                {-Math.PI / 3, Math.cos(-Math.PI / 3) / Math.sin(-Math.PI / 3)}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];

            double result = cotFunction.calculate(x, CALC_DELTA);
            System.out.println("Testing cot(" + x + "): expected " + expected + ", actual " + result);

            assertEquals("cot(" + x + ") должен быть " + expected, expected, result, TEST_TOLERANCE);
        }
    }

    /**
     * Тестирование табличных значений из CSV-файла cot.csv.
     * Формат CSV: первая строка — заголовок, а потом пары (x, expected).
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1)
    public void testCotFileSource(double x, double expected) {
        // Вызываем функцию с CALC_DELTA
        double result = cotFunction.calculate(x, CALC_DELTA);
        System.out.printf("Testing cot(%.6f): expected %.6f, actual %.6f%n", x, expected, result);

        // Сравниваем с TEST_TOLERANCE
        assertEquals(expected, result, TEST_TOLERANCE);
    }

    /**
     * Тестирование случая, когда sin(x) = 0 (например, x = 0).
     * cot(0) → ±∞, в Java деление на 0.0 даёт Infinity или -Infinity.
     */
    @Test
    public void testCotUndefinedValue() {
        double x = 0.0;
        double result = cotFunction.calculate(x, CALC_DELTA);
        System.out.println("Testing cot(" + x + "): expected Infinity, actual " + result);

        // Проверяем, что результат - бесконечность
        assertTrue("cot(0) должен быть бесконечностью", Double.isInfinite(result));
    }
}
