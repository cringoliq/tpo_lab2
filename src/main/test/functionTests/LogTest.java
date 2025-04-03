package functionTests;

import com.labwork.logarithms.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    // 🔹 Проверка вычислений логарифма с основанием 2
    @ParameterizedTest
    @CsvFileSource(resources = "/log2.csv", numLinesToSkip = 0)
    void testLogBase2FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        Log2Impl log = new Log2Impl(); // Логарифм по основанию 2
        assertEquals(expected, log.calculate(x, delta), delta);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/log3.csv", numLinesToSkip = 0)
    void testLogBase3FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        Log3Impl log = new Log3Impl(); // Логарифм по основанию 5
        assertEquals(expected, log.calculate(x, delta), delta);
    }

    // 🔹 Проверка вычислений логарифма с основанием 5
    @ParameterizedTest
    @CsvFileSource(resources = "/log5.csv", numLinesToSkip = 0)
    void testLogBase5FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        Log5Impl log = new Log5Impl(); // Логарифм по основанию 5
        assertEquals(expected, log.calculate(x, delta), delta);
    }
    // 🔹 Проверка вычислений логарифма с основанием 5
    @ParameterizedTest
    @CsvFileSource(resources = "/log10.csv", numLinesToSkip = 0)
    void testLogBase10FromCsvFile(double x, double expected) {
        double delta = 0.00001; // Точность вычислений
        Log10Impl log = new Log10Impl(); // Логарифм по основанию 5
        assertEquals(expected, log.calculate(x, delta), delta);
    }



    // 🔹 Проверка выброса исключений при некорректных входных данных
    @Test
    void testLogarithmThrowsExceptionForZeroX() {
        double delta = 1e-6;
        Log2Impl log = new Log2Impl();
        assertThrows(IllegalArgumentException.class, () -> log.calculate(0, delta));
    }

    @Test
    void testLogarithmThrowsExceptionForNegativeX() {
        double delta = 1e-6;
        Log2Impl log = new Log2Impl();
        assertThrows(IllegalArgumentException.class, () -> log.calculate(-5, delta));
    }




}
