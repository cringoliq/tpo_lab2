package functionTests;

import com.labwork.logarithms.LogImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    // 🔹 Проверка вычислений логарифма с основанием 2
    @ParameterizedTest
    @CsvFileSource(resources = "/log2.csv", numLinesToSkip = 1)
    void testLogBase2FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        LogImpl log = new LogImpl(2); // Логарифм по основанию 2
        assertEquals(expected, log.calculate(x, delta), delta);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/log3.csv", numLinesToSkip = 1)
    void testLogBase3FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        LogImpl log = new LogImpl(3); // Логарифм по основанию 5
        assertEquals(expected, log.calculate(x, delta), delta);
    }

    // 🔹 Проверка вычислений логарифма с основанием 5
    @ParameterizedTest
    @CsvFileSource(resources = "/log5.csv", numLinesToSkip = 1)
    void testLogBase5FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        LogImpl log = new LogImpl(5); // Логарифм по основанию 5
        assertEquals(expected, log.calculate(x, delta), delta);
    }
    // 🔹 Проверка вычислений логарифма с основанием 5
    @ParameterizedTest
    @CsvFileSource(resources = "/log10.csv", numLinesToSkip = 1)
    void testLogBase10FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        LogImpl log = new LogImpl(10); // Логарифм по основанию 5
        assertEquals(expected, log.calculate(x, delta), delta);
    }


    // 🔹 Проверка монотонности логарифма для основания 2
    @ParameterizedTest
    @CsvSource({
            "2, 1, 2",
            "2, 2, 4",
            "2, 4, 8"
    })
    void testLogBase2IsMonotonic(double base, double x1, double x2) {
        double delta = 1e-6;
        LogImpl log = new LogImpl(base);
        assertTrue(log.calculate(x1, delta) < log.calculate(x2, delta));
    }

    // 🔹 Проверка выброса исключений при некорректных входных данных
    @Test
    void testLogarithmThrowsExceptionForZeroX() {
        double delta = 1e-6;
        LogImpl log = new LogImpl(2);
        assertThrows(IllegalArgumentException.class, () -> log.calculate(0, delta));
    }

    @Test
    void testLogarithmThrowsExceptionForNegativeX() {
        double delta = 1e-6;
        LogImpl log = new LogImpl(2);
        assertThrows(IllegalArgumentException.class, () -> log.calculate(-5, delta));
    }

    @Test
    void testLogarithmThrowsExceptionForBaseOne() {
        double delta = 1e-6;
        assertThrows(IllegalArgumentException.class, () -> new LogImpl(1));
    }

    @Test
    void testLogarithmThrowsExceptionForNegativeBase() {
        double delta = 1e-6;
        assertThrows(IllegalArgumentException.class, () -> new LogImpl(-3));
    }

    @Test
    void testLogarithmThrowsExceptionForZeroBase() {
        double delta = 1e-6;
        assertThrows(IllegalArgumentException.class, () -> new LogImpl(0));
    }
}
