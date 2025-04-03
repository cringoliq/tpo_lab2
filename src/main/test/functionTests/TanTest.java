package functionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.labwork.trigonometric.TanImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TanTest {

    // Задаём точность вычислений для тестов
    private final double DELTA = 1e-6;
    // Создаём экземпляр функции tan(x)
    private final FunctionInterface tanFunction = new TanImpl();

    /**
     * Тестирование базовых значений для tan(x).
     * Например, tan(0) = 0, tan(π/4) = 1, tan(-π/4) = -1.
     */
    @Test
    public void testTanBasicValues() {
        // tan(0) = 0
        double x0 = 0.0;
        double expected0 = 0.0;
        double result0 = tanFunction.calculate(x0, DELTA);
        System.out.println("Testing tan(" + x0 + "): expected " + expected0 + ", actual " + result0);
        assertEquals("tan(0) должен быть 0", expected0, result0, DELTA);

        // tan(π/4) = 1
        double x1 = Math.PI / 4;
        double expected1 = 1.0;
        double result1 = tanFunction.calculate(x1, DELTA);
        System.out.println("Testing tan(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("tan(π/4) должен быть 1", expected1, result1, DELTA);

        // tan(-π/4) = -1
        double x2 = -Math.PI / 4;
        double expected2 = -1.0;
        double result2 = tanFunction.calculate(x2, DELTA);
        System.out.println("Testing tan(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("tan(-π/4) должен быть -1", expected2, result2, DELTA);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение tan(x)}
     * и проверяем вычисления для набора типовых точек.
     */
    @Test
    public void testTanTabularValues() {
        // Используем Math.tan() для вычисления ожидаемых значений
        double[][] table = {
                {Math.PI / 6, Math.tan(Math.PI / 6)},
                {Math.PI / 4, Math.tan(Math.PI / 4)},
                {Math.PI / 3, Math.tan(Math.PI / 3)},
                {-Math.PI / 6, Math.tan(-Math.PI / 6)},
                {-Math.PI / 4, Math.tan(-Math.PI / 4)},
                {-Math.PI / 3, Math.tan(-Math.PI / 3)}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = tanFunction.calculate(x, DELTA);
            System.out.println("Testing tan(" + x + "): expected " + expected + ", actual " + result);
            assertEquals("tan(" + x + ") должен быть " + expected, expected, result, DELTA);
        }
    }
    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение tan(x)}
     * и проверяем вычисления для набора типовых точек from CSV file.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/tan.csv", numLinesToSkip = 1)
    public void testCosFileSource(double x, double expected) {
        double result = tanFunction.calculate(x, DELTA);
        System.out.printf("Testing cos(%.6f): expected %.6f, actual %.6f%n", x, expected, result);
        assertEquals(expected, result, DELTA);
    }

    /**
     * Тестирование нормализации аргументов.
     * Например, tan(3π) = tan(π) = 0, поскольку tan(x) периодична с периодом π.
     */
    @Test
    public void testTanNormalization() {
        double x1 = 3 * Math.PI;
        double expected1 = Math.tan(Math.PI); // tan(π) = 0
        double result1 = tanFunction.calculate(x1, DELTA);
        System.out.println("Testing tan(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("tan(3π) должен быть равен tan(π)", expected1, result1, DELTA);

        double x2 = -3 * Math.PI;
        double expected2 = Math.tan(-Math.PI); // tan(-π) = 0
        double result2 = tanFunction.calculate(x2, DELTA);
        System.out.println("Testing tan(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("tan(-3π) должен быть равен tan(π)", expected2, result2, DELTA);
    }

    /**
     * Тестирование случая, когда функция не определена.
     * Например, при x = π/2 или x = 3π/2, tan(x) должна стремиться к ± бесконечности.
     */
    @Test
    public void testTanUndefinedValue() {
        double x = Math.PI / 2;
        double result = tanFunction.calculate(x, DELTA);
        System.out.println("Testing tan(" + x + "): expected Infinity, actual " + result);
        assertTrue("tan(π/2) должен быть бесконечностью", Double.isInfinite(result));
    }
}
