package functionTests;

import com.labwork.logarithms.LnImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LnTest {

    private final LnImpl ln = new LnImpl();

    // Проверка корректных значений
    @ParameterizedTest
    @CsvFileSource(resources = "/ln.csv", numLinesToSkip = 1) // Пропускаем заголовок
    void testLnPositiveValues(double x, double expected) {
        double delta = 0.001; // Точность вычислений
        assertEquals(expected, ln.calculate(x, delta), delta);
    }

    // Проверка выброса исключений при x <= 0
    @Test
    void testLnThrowsExceptionForZero() {
        double delta = 1e-6;
        assertThrows(ArithmeticException.class, () -> ln.calculate(0, delta));
    }

    @Test
    void testLnThrowsExceptionForNegative() {
        double delta = 1e-6;
        assertThrows(ArithmeticException.class, () -> ln.calculate(-1, delta));
    }

    // Проверка выброса исключений при некорректном delta
    @Test
    void testInvalidDeltaTooSmall() {
        assertThrows(ArithmeticException.class, () -> ln.calculate(2, -0.1));
    }

    @Test
    void testInvalidDeltaTooLarge() {
        assertThrows(ArithmeticException.class, () -> ln.calculate(2, 1));
    }

    // Проверка монотонности ln(x)
    @ParameterizedTest
    @CsvSource({
            "1.0, 2.0",
            "2.0, 3.0",
            "3.0, 10.0",
            "0.5, 1.0"
    })
    void testLnIsMonotonic(double x1, double x2) {
        double delta = 1e-6;
        assertTrue(ln.calculate(x1, delta) < ln.calculate(x2, delta));
    }
}
