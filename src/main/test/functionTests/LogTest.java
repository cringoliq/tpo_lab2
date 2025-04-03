package functionTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import com.labwork.logarithms.LogImpl;
import com.labwork.interfaces.FunctionInterface;

public class LogTest {

    private final double DELTA = 1e-3;
    private final FunctionInterface log2Function = new LogImpl(2);
    private final FunctionInterface log5Function = new LogImpl(5);

    /**
     * Тестирование базовых значений log2(x).
     */
    @Test
    @DisplayName("Тестирование базовых значений log2(x)")
    public void testLog2BasicValues() {
        assertEquals(0.0, log2Function.calculate(1.0, DELTA), DELTA, "log2(1) должен быть 0");
        assertEquals(1.0, log2Function.calculate(2.0, DELTA), DELTA, "log2(2) должен быть 1");
        assertEquals(2.0, log2Function.calculate(4.0, DELTA), DELTA, "log2(4) должен быть 2");
        assertEquals(3.0, log2Function.calculate(8.0, DELTA), DELTA, "log2(8) должен быть 3");
        assertEquals(-1.0, log2Function.calculate(0.5, DELTA), DELTA, "log2(0.5) должен быть -1");
    }

    /**
     * Тестирование базовых значений log5(x).
     */
    @Test
    @DisplayName("Тестирование базовых значений log5(x)")
    public void testLog5BasicValues() {
        assertEquals(0.0, log5Function.calculate(1.0, DELTA), DELTA, "log5(1) должен быть 0");
        assertEquals(1.0, log5Function.calculate(5.0, DELTA), DELTA, "log5(5) должен быть 1");
        assertEquals(2.0, log5Function.calculate(25.0, DELTA), DELTA, "log5(25) должен быть 2");
        assertEquals(-1.0, log5Function.calculate(0.2, DELTA), DELTA, "log5(0.2) должен быть -1");
    }

    /**
     * Тестирование граничных значений log2(x) и log5(x).
     */
    @Test
    @DisplayName("Проверка граничных значений log2(x) и log5(x)")
    public void testLogEdgeCases() {
        assertThrows(IllegalArgumentException.class, () -> log2Function.calculate(0.0, DELTA), "log2(0) должен выбрасывать исключение");
        assertThrows(IllegalArgumentException.class, () -> log2Function.calculate(-1.0, DELTA), "log2(-1) должен выбрасывать исключение");

        assertThrows(IllegalArgumentException.class, () -> log5Function.calculate(0.0, DELTA), "log5(0) должен выбрасывать исключение");
        assertThrows(IllegalArgumentException.class, () -> log5Function.calculate(-1.0, DELTA), "log5(-1) должен выбрасывать исключение");
    }

    /**
     * Тестирование монотонности log2(x) и log5(x).
     */
    @Test
    @DisplayName("Тестирование монотонности log2(x) и log5(x)")
    public void testLogMonotonicity() {
        assertTrue(log2Function.calculate(4.0, DELTA) > log2Function.calculate(2.0, DELTA), "log2(4) должен быть больше log2(2)");
        assertTrue(log5Function.calculate(25.0, DELTA) > log5Function.calculate(5.0, DELTA), "log5(25) должен быть больше log5(5)");
    }

    /**
     * Тестирование log2(x) и log5(x) с данными из CSV-файла.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/log2.csv", numLinesToSkip = 1)
    @DisplayName("Тестирование log2(x) с табличными значениями из CSV")
    public void testLog2FileSource(double x, double expected) {
        double result = log2Function.calculate(x, DELTA);
        assertEquals(expected, result, DELTA, String.format("log2(%.6f) должен быть %.6f", x, expected));
    }

    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение log5(x)}
     * и проверяем вычисления для набора типовых точек from CSV file.
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/log5.csv", numLinesToSkip = 1)
    @DisplayName("Тестирование log5(x) с табличными значениями из CSV")
    public void testLog5FileSource(double x, double expected) {
        double result = log5Function.calculate(x, DELTA);
        assertEquals(expected, result, DELTA, String.format("log5(%.6f) должен быть %.6f", x, expected));
    }
}
