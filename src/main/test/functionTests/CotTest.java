package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.CotImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CotTest {

    // Задаём точность вычислений для тестов
    private final double DELTA = 1e-8;
    // Создаём экземпляр функции cot(x)
    private final FunctionInterface cotFunction = new CotImpl();

    /**
     * Тестирование базовых значений для cot(x).
     * Например, cot(π/4) = 1, cot(π/6) = √3, cot(π/3) ≈ 0.57735.
     */
    @Test
    public void testCotBasicValues() {
        // cot(π/4) = cos(π/4)/sin(π/4) = 1
        double x1 = Math.PI / 4;
        double expected1 = 1.0;
        double result1 = cotFunction.calculate(x1, DELTA);
        System.out.println("Testing cot(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("cot(π/4) должен быть 1", expected1, result1, DELTA);

        // cot(π/6) = cos(π/6)/sin(π/6) = (√3/2) / (1/2) = √3
        double x2 = Math.PI / 6;
        double expected2 = Math.cos(x2) / Math.sin(x2);
        double result2 = cotFunction.calculate(x2, DELTA);
        System.out.println("Testing cot(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("cot(π/6) должен быть √3", expected2, result2, DELTA);

        // cot(π/3) = cos(π/3)/sin(π/3) = (1/2) / (√3/2) ≈ 0.57735
        double x3 = Math.PI / 3;
        double expected3 = Math.cos(x3) / Math.sin(x3);
        double result3 = cotFunction.calculate(x3, DELTA);
        System.out.println("Testing cot(" + x3 + "): expected " + expected3 + ", actual " + result3);
        assertEquals("cot(π/3) должен быть примерно 0.57735", expected3, result3, DELTA);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     * По свойству нечетности, cot(-x) = -cot(x).
     */
    @Test
    public void testCotNegativeValues() {
        double x = -Math.PI / 4;
        double expected = Math.cos(x) / Math.sin(x); // должно быть -1
        double result = cotFunction.calculate(x, DELTA);
        System.out.println("Testing cot(" + x + "): expected " + expected + ", actual " + result);
        assertEquals("cot(-π/4) должен быть -1", expected, result, DELTA);
    }

    /**
     * Тестирование нормализации аргументов.
     * Поскольку тригонометрические функции периодичны, проверяем, что функция корректно
     * обрабатывает аргументы, выходящие за основной период.
     */
    @Test
    public void testCotNormalization() {
        // cot(5π/4) = cot(π + π/4) = cot(π/4) = 1
        double x = 5 * Math.PI / 4;
        double expected = Math.cos(x) / Math.sin(x);
        double result = cotFunction.calculate(x, DELTA);
        System.out.println("Testing cot(" + x + "): expected " + expected + ", actual " + result);
        assertEquals("cot(5π/4) должен быть равен cot(π/4)", expected, result, DELTA);
    }

    /**
     * Тестирование табличных значений:
     * Задаём массив пар {x, ожидаемое значение cot(x)} и проверяем вычисления для типовых точек.
     * Важно не включать точки, где sin(x) = 0 (например, x = 0, π, ...), поскольку cot(x) там не определён.
     */
    @Test
    public void testCotTabularValues() {
        double[][] table = {
                {Math.PI / 6, Math.cos(Math.PI / 6) / Math.sin(Math.PI / 6)},
                {Math.PI / 4, Math.cos(Math.PI / 4) / Math.sin(Math.PI / 4)},
                {Math.PI / 3, Math.cos(Math.PI / 3) / Math.sin(Math.PI / 3)},
                {-Math.PI / 6, Math.cos(-Math.PI / 6) / Math.sin(-Math.PI / 6)},
                {-Math.PI / 4, Math.cos(-Math.PI / 4) / Math.sin(-Math.PI / 4)},
                {-Math.PI / 3, Math.cos(-Math.PI / 3) / Math.sin(-Math.PI / 3)}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = cotFunction.calculate(x, DELTA);
            System.out.println("Testing cot(" + x + "): expected " + expected + ", actual " + result);
            assertEquals("cot(" + x + ") должен быть " + expected, expected, result, DELTA);
        }
    }
    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение cot(x)}
     * и проверяем вычисления для набора типовых точек from CSV file.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1)
    public void testCotFileSource(double x, double expected) {
        double result = cotFunction.calculate(x, DELTA);
        System.out.printf("Testing cos(%.6f): expected %.6f, actual %.6f%n", x, expected, result);
        assertEquals(expected, result, DELTA);
    }

    /**
     * Тестирование случая, когда sin(x) равен 0.
     * Например, при x = 0, функция cot(x) должна давать бесконечность.
     */
    @Test
    public void testCotUndefinedValue() {
        double x = 0.0;
        double result = cotFunction.calculate(x, DELTA);
        System.out.println("Testing cot(" + x + "): expected Infinity, actual " + result);
        // В Java деление на 0.0 дает Infinity или -Infinity
        assertTrue("cot(0) должен быть бесконечностью", Double.isInfinite(result));
    }
}
