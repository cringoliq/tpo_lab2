package inequationIntegrationTests;


import com.labwork.FunctionSystem;
import com.labwork.interfaces.FunctionInterface;
import com.labwork.logarithms.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogPartTest {
    // Отдельные моки для каждой функции
    private final LnImpl lnMock = Mockito.mock(LnImpl.class);
    private final Log2Impl log2Mock = Mockito.mock(Log2Impl.class);
    private final Log3Impl log3Mock = Mockito.mock(Log3Impl.class);
    private final Log5Impl log5Mock = Mockito.mock(Log5Impl.class);
    private final Log10Impl log10Mock = Mockito.mock(Log10Impl.class);

    @BeforeAll
    public void fillAll() {

        fillMock(lnMock, "ln.csv");
        fillMock(log2Mock, "log2.csv");
        fillMock(log3Mock, "log3.csv");
        fillMock(log5Mock, "log5.csv");
        fillMock(log10Mock, "log10.csv");
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
    @CsvFileSource(resources = "/logPart.csv")
    void allMockTest(Double x, Double trueResult) {

        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, log5Mock, log10Mock);

        runTest(functionSystem, x, trueResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void lnTest(Double x, Double trueResult) {

        FunctionSystem functionSystem = new FunctionSystem(new LnImpl(),log2Mock, log3Mock, log5Mock, log10Mock);

        runTest(functionSystem, x, trueResult);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log2Test(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, new Log2Impl(), log3Mock, log5Mock, log10Mock);

        runTest(functionSystem, x, trueResult);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log2ParamTest(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, new Log2Impl(lnMock), log3Mock, log5Mock, log10Mock);

        runTest(functionSystem, x, trueResult);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log3Test(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, new Log3Impl(), log5Mock, log10Mock);

        runTest(functionSystem, x, trueResult);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log3ParamTest(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, new Log3Impl(lnMock), log5Mock, log10Mock);

        runTest(functionSystem, x, trueResult);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log5Test(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, new Log5Impl(), log10Mock);

        runTest(functionSystem, x, trueResult);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log5ParamTest(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, new Log5Impl(lnMock), log10Mock);

        runTest(functionSystem, x, trueResult);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log10Test(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, log5Mock, new Log10Impl());

        runTest(functionSystem, x, trueResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void log10ParamTest(Double x, Double trueResult) {
        FunctionSystem functionSystem = new FunctionSystem(lnMock, log2Mock, log3Mock, log5Mock, new Log10Impl(lnMock));

        runTest(functionSystem, x, trueResult);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/logPart.csv")
    void fullTest(Double x, Double trueResult) {

        FunctionSystem functionSystem = new FunctionSystem(new LnImpl(),new Log2Impl(),new Log3Impl(),new Log5Impl(), new Log10Impl());

        runTest(functionSystem, x, trueResult);
    }
}
