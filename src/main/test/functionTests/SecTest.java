package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.SecImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SecTest {

    // Точность для расчётов внутри функции (например, в ряду Тейлора)
    private static final double CALC_DELTA = 1e-8;

    // Точность, с которой сравниваем фактическое значение с ожидаемым в тестах
    private static final double TEST_TOLERANCE = 1e-1;

    // С какой величины считаем число "очень большим" и допускаем Infinity
    private static final double HUGE_THRESHOLD = 1e15;

    // Экземпляр функции sec(x)
    private final FunctionInterface secFunction = new SecImpl();

    /**
     * Утилитарный метод, который сравнивает фактическое значение (actual) с ожидаемым (expected):
     *  - Если expected = Infinity, проверяем, что actual тоже Infinity
     *  - Если expected очень велико (|expected| > HUGE_THRESHOLD), допускаем, что actual может быть Infinity
     *  - Иначе сравниваем обычным assertEquals с TEST_TOLERANCE
     */
    private void checkSecValue(double x, double expected, double actual) {
        // 1) Если ожидаем Infinity
        if (Double.isInfinite(expected)) {
            assertTrue("sec(" + x + ") должен быть бесконечностью", Double.isInfinite(actual));
            return;
        }

        // 2) Если ожидаем "очень большое" число (например, > 1e15)
        if (Math.abs(expected) > HUGE_THRESHOLD) {
            // Допустим, что actual может оказаться Infinity из-за переполнения
            if (Double.isInfinite(actual)) {
                // Считаем это корректным результатом
                return;
            }
            // Иначе делаем обычную проверку
            assertEquals("sec(" + x + ") должен быть ~" + expected + " (очень большое)",
                    expected, actual, TEST_TOLERANCE);
            return;
        }

        // 3) Обычный случай: сравниваем через assertEquals
        assertEquals("sec(" + x + ") должен быть " + expected,
                expected, actual, TEST_TOLERANCE);
    }


    /**
     * Тестирование базовых значений для sec(x).
     */
    @Test
    public void testSecBasicValues() {
        double x0 = 0.0;
        double expected0 = 1.0;
        double actual0 = secFunction.calculate(x0, CALC_DELTA);
        System.out.println("Testing sec(" + x0 + "): expected " + expected0 + ", actual " + actual0);
        checkSecValue(x0, expected0, actual0);

        double x1 = Math.PI / 3;
        double expected1 = 1.0 / Math.cos(x1); // = 2
        double actual1 = secFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing sec(" + x1 + "): expected " + expected1 + ", actual " + actual1);
        checkSecValue(x1, expected1, actual1);

        double x2 = Math.PI;
        double expected2 = 1.0 / Math.cos(x2); // = -1
        double actual2 = secFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing sec(" + x2 + "): expected " + expected2 + ", actual " + actual2);
        checkSecValue(x2, expected2, actual2);
    }

    /**
     * Тест для отрицательных значений аргумента.
     */
    @Test
    public void testSecNegativeValues() {
        double x = -Math.PI / 3;
        double expected = 1.0 / Math.cos(x);
        double actual = secFunction.calculate(x, CALC_DELTA);
        System.out.println("Testing sec(" + x + "): expected " + expected + ", actual " + actual);
        checkSecValue(x, expected, actual);
    }

    /**
     * Тестирование нормализации аргументов.
     */
    @Test
    public void testSecNormalization() {
        double x1 = 3 * Math.PI;
        double expected1 = 1.0 / Math.cos(x1); // = -1
        double actual1 = secFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing sec(" + x1 + "): expected " + expected1 + ", actual " + actual1);
        checkSecValue(x1, expected1, actual1);

        double x2 = -3 * Math.PI;
        double expected2 = 1.0 / Math.cos(x2); // = -1
        double actual2 = secFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing sec(" + x2 + "): expected " + expected2 + ", actual " + actual2);
        checkSecValue(x2, expected2, actual2);
    }

    /**
     * Тестирование табличных значений из массива.
     */
    @Test
    public void testSecTabularValues() {
        double[][] table = {
                {0.0,         1.0 / Math.cos(0.0)},
                {Math.PI / 6, 1.0 / Math.cos(Math.PI / 6)},
                {Math.PI / 4, 1.0 / Math.cos(Math.PI / 4)},
                {Math.PI / 3, 1.0 / Math.cos(Math.PI / 3)},
                {Math.PI / 2, 1.0 / Math.cos(Math.PI / 2)}, // => Infinity (в теории)
                {Math.PI,     1.0 / Math.cos(Math.PI)},
                {-Math.PI / 6,1.0 / Math.cos(-Math.PI / 6)},
                {-Math.PI / 4,1.0 / Math.cos(-Math.PI / 4)},
                {-Math.PI / 2,1.0 / Math.cos(-Math.PI / 2)}, // => Infinity
                {-Math.PI,    1.0 / Math.cos(-Math.PI)}
        };

        for (double[] pair : table) {
            double x       = pair[0];
            double expected= pair[1];
            double actual  = secFunction.calculate(x, CALC_DELTA);
            System.out.println("Testing sec(" + x + "): expected " + expected + ", actual " + actual);
            checkSecValue(x, expected, actual);
        }
    }

    /**
     * Тестирование табличных значений из CSV (sec.csv).
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/sec.csv", numLinesToSkip = 1)
    public void testSecFileSource(double x, double expected) {
        double actual = secFunction.calculate(x, CALC_DELTA);
        System.out.printf("Testing sec(%.6f): expected %.6f, actual %.6f%n", x, expected, actual);
        checkSecValue(x, expected, actual);
    }

    /**
     * Тестирование, когда cos(x) = 0 => sec(x) → Infinity.
     */
    @Test
    public void testSecUndefinedValues() {
        double x1 = Math.PI / 2;
        double actual1 = secFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing sec(" + x1 + "): expected Infinity, actual " + actual1);
        assertTrue("sec(π/2) должен быть бесконечностью", Double.isInfinite(actual1));

        double x2 = 3 * Math.PI / 2;
        double actual2 = secFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing sec(" + x2 + "): expected Infinity, actual " + actual2);
        assertTrue("sec(3π/2) должен быть бесконечностью", Double.isInfinite(actual2));
    }
}
