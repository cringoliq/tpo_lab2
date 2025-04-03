package functionTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import com.labwork.logarithms.LnImpl;
import com.labwork.interfaces.FunctionInterface;

public class LnTest {

    private final double DELTA = 1e-3;
    private final FunctionInterface lnFunction = new LnImpl();

    @Test
    @DisplayName("Тестирование базовых значений ln(x)")
    public void testLnBasicValues() {
        assertEquals(0.0, lnFunction.calculate(1.0, DELTA), DELTA, "ln(1) должен быть 0");
        assertEquals(1.0, lnFunction.calculate(Math.E, DELTA), DELTA, "ln(e) должен быть 1");
        assertEquals(2.0, lnFunction.calculate(Math.E * Math.E, DELTA), DELTA, "ln(e^2) должен быть 2");
        assertEquals(2.3026, lnFunction.calculate(10.0, DELTA), DELTA, "ln(10) ≈ 2.3026");
        assertEquals(-0.6931, lnFunction.calculate(0.5, DELTA), DELTA, "ln(0.5) ≈ -0.6931");
    }

    @Test
    @DisplayName("Проверка граничных значений ln(x)")
    public void testLnEdgeCases() {
        assertThrows(ArithmeticException.class, () -> lnFunction.calculate(0.0, DELTA), "ln(0) должен выбрасывать исключение");
        assertThrows(ArithmeticException.class, () -> lnFunction.calculate(-1.0, DELTA), "ln(-1) должен выбрасывать исключение");
    }

    @Test
    @DisplayName("Тестирование монотонности ln(x)")
    public void testLnMonotonicity() {
        assertTrue(lnFunction.calculate(2.0, DELTA) > lnFunction.calculate(1.5, DELTA), "ln(2) должен быть больше ln(1.5)");
        assertTrue(lnFunction.calculate(1.5, DELTA) > lnFunction.calculate(1.0, DELTA), "ln(1.5) должен быть больше ln(1)");
    }


    /**
     * Тестирование табличных значений: задаём массив пар {x, ожидаемое значение ln(x)}
     * и проверяем вычисления для набора типовых точек from CSV file.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/ln.csv", numLinesToSkip = 1)
    @DisplayName("Тестирование ln(x) с табличными значениями из CSV")
    public void testLnFileSource(double x, double expected) {
        double result = lnFunction.calculate(x, DELTA);
        assertEquals(expected, result, DELTA, String.format("ln(%.6f) должен быть %.6f", x, expected));
    }
}
