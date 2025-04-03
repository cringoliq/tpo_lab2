package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.TanImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TanTest {

    // Точность для расчётов внутри самой функции (разложение и т.п.)
    private static final double CALC_DELTA = 1e-8;

    // Точность сравнения результата с ожидаемым в тестах
    private static final double TEST_TOLERANCE = 0.01;

    // «Очень большая» граница, при которой мы допускаем, что фактический результат может стать Infinity
    private static final double HUGE_THRESHOLD = 1e12;

    // Экземпляр функции tan(x)
    private final FunctionInterface tanFunction = new TanImpl();

    /**
     * Вспомогательный метод, который сравнивает фактическое значение (actual) с ожидаемым (expected):
     * - Если expected = Infinity, проверяем, что actual тоже Infinity (или очень большое).
     * - Если expected по модулю > HUGE_THRESHOLD, разрешаем, что actual может быть Infinity.
     * - Иначе обычное сравнение assertEquals(..., TEST_TOLERANCE).
     */
    private void checkTanValue(double x, double expected, double actual) {
        // Если ожидаем Infinity
        if (Double.isInfinite(expected)) {
            // Разрешаем, что actual может быть Infinity или очень большим
            if (Double.isInfinite(actual) || Math.abs(actual) > HUGE_THRESHOLD) {
                // Всё ок, считаем, что test passed
                return;
            }
            // Иначе тест падает
            assertTrue("tan(" + x + ") должен быть бесконечностью или очень большим числом",
                    Double.isInfinite(actual));
        } else if (Math.abs(expected) > HUGE_THRESHOLD) {
            // Если ожидаем очень большое число
            if (Double.isInfinite(actual)) {
                // Считаем ок
                return;
            }
            // Иначе обычная проверка с TEST_TOLERANCE
            assertEquals("tan(" + x + ") должен быть очень большим, близко к " + expected,
                    expected, actual, TEST_TOLERANCE);
        } else {
            // Обычный случай
            assertEquals("tan(" + x + ") должен быть " + expected, expected, actual, TEST_TOLERANCE);
        }
    }

    /**
     * Тестирование базовых значений для tan(x).
     * Например, tan(0) = 0, tan(π/4) = 1, tan(-π/4) = -1.
     */
    @Test
    public void testTanBasicValues() {
        // tan(0) = 0
        double x0 = 0.0;
        double expected0 = 0.0;
        double actual0 = tanFunction.calculate(x0, CALC_DELTA);
        System.out.println("Testing tan(" + x0 + "): expected " + expected0 + ", actual " + actual0);
        checkTanValue(x0, expected0, actual0);

        // tan(π/4) = 1
        double x1 = Math.PI / 4;
        double expected1 = 1.0;
        double actual1 = tanFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing tan(" + x1 + "): expected " + expected1 + ", actual " + actual1);
        checkTanValue(x1, expected1, actual1);

        // tan(-π/4) = -1
        double x2 = -Math.PI / 4;
        double expected2 = -1.0;
        double actual2 = tanFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing tan(" + x2 + "): expected " + expected2 + ", actual " + actual2);
        checkTanValue(x2, expected2, actual2);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение tan(x)}.
     */
    @Test
    public void testTanTabularValues() {
        // Используем Math.tan() для вычисления ожидаемых значений
        double[][] table = {
                {Math.PI / 6,  Math.tan(Math.PI / 6)},
                {Math.PI / 4,  Math.tan(Math.PI / 4)},
                {Math.PI / 3,  Math.tan(Math.PI / 3)},
                {-Math.PI / 6, Math.tan(-Math.PI / 6)},
                {-Math.PI / 4, Math.tan(-Math.PI / 4)},
                {-Math.PI / 3, Math.tan(-Math.PI / 3)}
        };

        for (double[] pair : table) {
            double x       = pair[0];
            double expected= pair[1];
            double actual  = tanFunction.calculate(x, CALC_DELTA);

            System.out.println("Testing tan(" + x + "): expected " + expected + ", actual " + actual);
            checkTanValue(x, expected, actual);
        }
    }

    /**
     * Тестирование табличных значений из CSV (tan.csv).
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/tan.csv", numLinesToSkip = 1)
    public void testTanFileSource(double x, double expected) {
        double actual = tanFunction.calculate(x, CALC_DELTA);
        System.out.printf("Testing tan(%.6f): expected %.6f, actual %.6f%n", x, expected, actual);
        checkTanValue(x, expected, actual);
    }

    /**
     * Тестирование нормализации аргументов.
     * tan(3π) = tan(π) = 0.
     */
    @Test
    public void testTanNormalization() {
        double x1 = 3 * Math.PI;
        double expected1 = Math.tan(Math.PI); // 0
        double actual1 = tanFunction.calculate(x1, CALC_DELTA);
        System.out.println("Testing tan(" + x1 + "): expected " + expected1 + ", actual " + actual1);
        checkTanValue(x1, expected1, actual1);

        double x2 = -3 * Math.PI;
        double expected2 = Math.tan(-Math.PI); // 0
        double actual2 = tanFunction.calculate(x2, CALC_DELTA);
        System.out.println("Testing tan(" + x2 + "): expected " + expected2 + ", actual " + actual2);
        checkTanValue(x2, expected2, actual2);
    }

    /**
     * Тестирование случая, когда функция не определена.
     * При x = π/2 или x = 3π/2 tan(x) → ±∞.
     */
    @Test
    public void testTanUndefinedValue() {
        double x = Math.PI / 2;
        double actual = tanFunction.calculate(x, CALC_DELTA);
        System.out.println("Testing tan(" + x + "): expected Infinity, actual " + actual);

        // Проверяем, что фактическое значение -> бесконечность (либо очень большое)
        assertTrue("tan(π/2) должен быть бесконечностью",
                Double.isInfinite(actual) || Math.abs(actual) > HUGE_THRESHOLD);
    }
}
