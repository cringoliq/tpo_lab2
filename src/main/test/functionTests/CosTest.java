package functionTests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.labwork.trigonometric.CosImpl;
import com.labwork.interfaces.FunctionInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CosTest {

    // Задаём точность вычислений для тестов
    private final double DELTA = 1e-3;
    // Создаём экземпляр функции cos(x)
    private FunctionInterface cosFunction = new CosImpl();

    /**
     * Тестирование базовых значений для cos(x).
     */
    @Test
    public void testCosBasicValues() {
        // cos(0) = 1
        double x0 = 0.0;
        double expected0 = 1.0;
        double result0 = cosFunction.calculate(x0, DELTA);
        System.out.println("Testing cos(" + x0 + "): expected " + expected0 + ", actual " + result0);
        assertEquals("cos(0) должен быть 1", expected0, result0, DELTA);

        // cos(π/3) = 0.5
        double x1 = Math.PI / 3;
        double expected1 = 0.5;
        double result1 = cosFunction.calculate(x1, DELTA);
        System.out.println("Testing cos(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("cos(π/3) должен быть 0.5", expected1, result1, DELTA);

        // cos(π/2) = 0
        double x2 = Math.PI / 2;
        double expected2 = 0.0;
        double result2 = cosFunction.calculate(x2, DELTA);
        System.out.println("Testing cos(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("cos(π/2) должен быть 0", expected2, result2, DELTA);

        // cos(π) = -1
        double x3 = Math.PI;
        double expected3 = -1.0;
        double result3 = cosFunction.calculate(x3, DELTA);
        System.out.println("Testing cos(" + x3 + "): expected " + expected3 + ", actual " + result3);
        assertEquals("cos(π) должен быть -1", expected3, result3, DELTA);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     * Так как cos(x) является чётной функцией, cos(-x) = cos(x).
     */
    @Test
    public void testCosNegativeValues() {
        // cos(-π/3) должен быть равен cos(π/3) = 0.5
        double x1 = -Math.PI / 3;
        double expected1 = 0.5;
        double result1 = cosFunction.calculate(x1, DELTA);
        System.out.println("Testing cos(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("cos(-π/3) должен быть 0.5", expected1, result1, DELTA);

        // cos(-π/2) должен быть равен cos(π/2) = 0
        double x2 = -Math.PI / 2;
        double expected2 = 0.0;
        double result2 = cosFunction.calculate(x2, DELTA);
        System.out.println("Testing cos(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("cos(-π/2) должен быть 0", expected2, result2, DELTA);
    }

    /**
     * Тестирование нормализации аргументов.
     * Проверяем работу функции для значений x, выходящих за стандартный интервал.
     */
    @Test
    public void testCosNormalization() {
        // Например, cos(3π) = cos(π) = -1
        double x1 = 3 * Math.PI;
        double expected1 = -1.0;
        double result1 = cosFunction.calculate(x1, DELTA);
        System.out.println("Testing cos(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("cos(3π) должен быть -1 после нормализации", expected1, result1, DELTA);

        // cos(-3π) = cos(π) = -1
        double x2 = -3 * Math.PI;
        double expected2 = -1.0;
        double result2 = cosFunction.calculate(x2, DELTA);
        System.out.println("Testing cos(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("cos(-3π) должен быть -1 после нормализации", expected2, result2, DELTA);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение cos(x)}
     * и проверяем вычисления для набора типовых точек.
     */
    @Test
    public void testCosTabularValues() {
        // Таблица значений: {x, ожидаемое значение cos(x)}
        double[][] table = {
                {0.0, 1.0},
                {Math.PI / 6, Math.cos(Math.PI / 6)},
                {Math.PI / 4, Math.cos(Math.PI / 4)},
                {Math.PI / 3, Math.cos(Math.PI / 3)},
                {Math.PI / 2, Math.cos(Math.PI / 2)},
                {Math.PI, Math.cos(Math.PI)},
                {-Math.PI / 6, Math.cos(-Math.PI / 6)},
                {-Math.PI / 4, Math.cos(-Math.PI / 4)},
                {-Math.PI / 2, Math.cos(-Math.PI / 2)},
                {-Math.PI, Math.cos(-Math.PI)}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = cosFunction.calculate(x, DELTA);
            System.out.println("Testing cos(" + x + "): expected " + expected + ", actual " + result);
            assertEquals("cos(" + x + ") должен быть " + expected, expected, result, DELTA);
        }
    }



    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение cos(x)}
     * и проверяем вычисления для набора типовых точек from CSV file.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/cos.csv", numLinesToSkip = 1)
    public void testCosFileSource(double x, double expected) {
        double result = cosFunction.calculate(x, DELTA);
        System.out.printf("Testing cos(%.6f): expected %.6f, actual %.6f%n", x, expected, result);
        assertEquals(expected, result, DELTA);
    }
}
