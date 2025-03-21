package functionTests;

import static org.junit.Assert.assertEquals;

import com.labwork.trigonometric.SinImpl;
import org.junit.Test;
import com.labwork.interfaces.FunctionInterface;

public class SinTest {

    // Задаём точность вычислений для тестов
    private final double DELTA = 1e-6;
    // Создаём экземпляр функции
    private FunctionInterface sinFunction = new SinImpl();

    /**
     * Тестирование базовых значений, лежащих в допустимой области.
     */
    @Test
    public void testSinBasicValues() {
        // Проверка sin(0) = 0
        double x0 = 0.0;
        double expected0 = 0.0;
        double result0 = sinFunction.calculate(x0, DELTA);
        System.out.println("Testing sin(" + x0 + "): expected " + expected0 + ", actual " + result0);
        assertEquals("sin(0) должен быть 0", expected0, result0, DELTA);

        // sin(π/6) = 0.5
        double x1 = Math.PI / 6;
        double expected1 = 0.5;
        double result1 = sinFunction.calculate(x1, DELTA);
        System.out.println("Testing sin(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sin(π/6) должен быть 0.5", expected1, result1, DELTA);

        // sin(π/4) = sqrt(2)/2
        double x2 = Math.PI / 4;
        double expected2 = Math.sqrt(2) / 2;
        double result2 = sinFunction.calculate(x2, DELTA);
        System.out.println("Testing sin(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sin(π/4) должен быть sqrt(2)/2", expected2, result2, DELTA);

        // sin(π/2) = 1
        double x3 = Math.PI / 2;
        double expected3 = 1.0;
        double result3 = sinFunction.calculate(x3, DELTA);
        System.out.println("Testing sin(" + x3 + "): expected " + expected3 + ", actual " + result3);
        assertEquals("sin(π/2) должен быть 1", expected3, result3, DELTA);
    }

    /**
     * Тестирование для отрицательных значений аргумента.
     */
    @Test
    public void testSinNegativeValues() {
        // sin(-π/6) = -0.5
        double x1 = -Math.PI / 6;
        double expected1 = -0.5;
        double result1 = sinFunction.calculate(x1, DELTA);
        System.out.println("Testing sin(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sin(-π/6) должен быть -0.5", expected1, result1, DELTA);

        // sin(-π/4) = -sqrt(2)/2
        double x2 = -Math.PI / 4;
        double expected2 = -Math.sqrt(2) / 2;
        double result2 = sinFunction.calculate(x2, DELTA);
        System.out.println("Testing sin(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sin(-π/4) должен быть -sqrt(2)/2", expected2, result2, DELTA);
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
        double result1 = sinFunction.calculate(x1, DELTA);
        System.out.println("Testing sin(" + x1 + "): expected " + expected1 + ", actual " + result1);
        assertEquals("sin(3π) должен быть 0 после нормализации", expected1, result1, DELTA);

        // Для x = -3π: после нормализации также должно получиться sin(π) = 0
        double x2 = -3 * Math.PI;
        double expected2 = 0.0;
        double result2 = sinFunction.calculate(x2, DELTA);
        System.out.println("Testing sin(" + x2 + "): expected " + expected2 + ", actual " + result2);
        assertEquals("sin(-3π) должен быть 0 после нормализации", expected2, result2, DELTA);
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение}
     * и проверяем вычисления для набора типовых точек.
     */
    @Test
    public void testSinTabularValues() {
        // Таблица значений: {x, ожидаемое значение sin(x)}
        double[][] table = {
                {0.0, 0.0},
                {Math.PI / 6, 0.5},
                {Math.PI / 4, Math.sqrt(2) / 2},
                {Math.PI / 3, Math.sin(Math.PI / 3)},
                {Math.PI / 2, 1.0},
                {Math.PI, 0.0},
                {-Math.PI / 6, -0.5},
                {-Math.PI / 4, -Math.sqrt(2) / 2},
                {-Math.PI / 2, -1.0},
                {-Math.PI, 0.0}
        };

        for (double[] pair : table) {
            double x = pair[0];
            double expected = pair[1];
            double result = sinFunction.calculate(x, DELTA);
            System.out.println("Testing sin(" + x + "): expected " + expected + ", actual " + result);
            assertEquals("sin(" + x + ") должен быть " + expected, expected, result, DELTA);
        }
    }
}
