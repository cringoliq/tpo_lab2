package functionTests;


import com.labwork.FunctionSystem;
import com.labwork.interfaces.FunctionInterface;
import com.labwork.logarithms.*;
import com.labwork.trigonometric.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SystemTest {
    // Отдельные моки для каждой функции
    private final LnImpl lnMock = Mockito.mock(LnImpl.class);
    private final Log2Impl log2Mock = Mockito.mock(Log2Impl.class);
    private final Log3Impl log3Mock = Mockito.mock(Log3Impl.class);
    private final Log5Impl log5Mock = Mockito.mock(Log5Impl.class);
    private final Log10Impl log10Mock = Mockito.mock(Log10Impl.class);

    private final SinImpl sinMock = Mockito.mock(SinImpl.class);
    private final CosImpl cosMock = Mockito.mock(CosImpl.class);
    private final TanImpl tanMock = Mockito.mock(TanImpl.class);
    private final CotImpl cotMock = Mockito.mock(CotImpl.class);
    private final SecImpl secMock = Mockito.mock(SecImpl.class);
    private final CscImpl cscMock = Mockito.mock(CscImpl.class);


    @BeforeAll
    public void fillAll() {

        fillMock(lnMock, "ln.csv");
        fillMock(log2Mock, "log2.csv");
        fillMock(log3Mock, "log3.csv");
        fillMock(log5Mock, "log5.csv");
        fillMock(log10Mock, "log10.csv");
        fillMock(sinMock, "sin.csv");
        fillMock(cosMock, "cos.csv");
        fillMock(tanMock, "tan.csv");
        fillMock(cotMock, "cot.csv");
        fillMock(secMock, "sec.csv");
        fillMock(cscMock, "csc.csv");
    }
    private void fillMock(FunctionInterface function, String path) {
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                try {
                    double x = Double.parseDouble(record[0]);
                    double res = Double.parseDouble(record[1]);

                    System.out.println("Mocking: " + function.getClass().getSimpleName() + " x = " + x + ", result = " + res);

                    when(function.calculate(doubleThat(val -> Math.abs(val - x) < 1e-9), anyDouble())).thenReturn(res);

                } catch (NumberFormatException e) {
                    System.out.println("Ошибка парсинга в файле " + path + ": " + record[0] + ", " + record[1]);
                }
            }
        } catch (IOException | CsvException e) {
            System.out.println("Ошибка при загрузке CSV: " + path);
        }
    }





    private void runTest(FunctionSystem functionSystem, Double x, Double trueResult) {
        try {
            double result = functionSystem.calculate(x, 0.00001);
            System.out.println("Testing value x: " + x + ", calculated result: " + result);
            assertEquals(trueResult, result, 0.00001);
        } catch (ArithmeticException e) {
            System.out.println("Error with value x: " + x + " -> " + e.getMessage());

            assertEquals("Wrong x", e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv")
    void allMockTest(Double x, Double trueResult) {

        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, log5Mock, log10Mock, cosMock, cotMock, cscMock, secMock, sinMock, tanMock);

        runTest(functionSystem, x, trueResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv")
    void logTest(Double x, Double trueResult) {

        FunctionSystem functionSystem = new FunctionSystem(new LnImpl(),new Log2Impl(),new Log3Impl(),new Log5Impl(), new Log10Impl(), cosMock, cotMock, cscMock, secMock, sinMock, tanMock);

        runTest(functionSystem, x, trueResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv")
    void trigTest(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, log5Mock, log10Mock, new CosImpl(), new CotImpl(), new CscImpl(), new SecImpl(), new SinImpl(), new TanImpl());

        runTest(functionSystem, x, trueResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv")
    void fullTest(Double x, Double trueResult) {

        FunctionSystem functionSystem = new FunctionSystem(new LnImpl(),new Log2Impl(),new Log3Impl(),new Log5Impl(), new Log10Impl(), new CosImpl(), new CotImpl(), new CscImpl(), new SecImpl(), new SinImpl(), new TanImpl());

        runTest(functionSystem, x, trueResult);
    }
}
