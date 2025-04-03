package functionTests;


import com.labwork.FunctionSystem;
import com.labwork.logarithms.LnImpl;
import com.labwork.logarithms.LogImpl;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SystemTest {
    // Отдельные моки для каждой функции
    private LnImpl lnMock = Mockito.mock(LnImpl.class);
    private LogImpl log2Mock = Mockito.mock(LogImpl.class);
    private LogImpl log3Mock = Mockito.mock(LogImpl.class);
    private LogImpl log5Mock = Mockito.mock(LogImpl.class);
    private LogImpl log10Mock = Mockito.mock(LogImpl.class);

    private SinImpl sinMock = Mockito.mock(SinImpl.class);
    private CosImpl cosMock = Mockito.mock(CosImpl.class);
    private TanImpl tanMock = Mockito.mock(TanImpl.class);
    private CotImpl cotMock = Mockito.mock(CotImpl.class);
    private SecImpl secMock = Mockito.mock(SecImpl.class);
    private CscImpl cscMock = Mockito.mock(CscImpl.class);


    @BeforeAll
    public void fillAll() {
        fillMock(lnMock, "src/test/resources/log/ln.csv");
        fillMock(log2Mock, "src/test/resources/log/log2.csv");
        fillMock(log3Mock, "src/test/resources/log/log3.csv");
        fillMock(log5Mock, "src/test/resources/log/log5.csv");
        fillMock(log10Mock, "src/test/resources/log/log10.csv");

        fillMock(sinMock, "src/test/resources/trig/sin.csv");
        fillMock(cosMock, "src/test/resources/trig/cos.csv");
        fillMock(tanMock, "src/test/resources/trig/tan.csv");
        fillMock(cotMock, "src/test/resources/trig/cot.csv");
        fillMock(secMock, "src/test/resources/trig/sec.csv");
        fillMock(cscMock, "src/test/resources/trig/csc.csv");
    }

    private <T> void fillMock(T function, String path) {
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            List<String[]> records = csvReader.readAll();
            records.remove(0); // Убираем заголовок

            for (String[] record : records) {
                double x = Double.parseDouble(record[0]);
                double res = Double.parseDouble(record[1]);

                Mockito.when(function.getClass().getMethod("calculate", double.class, double.class)
                                .invoke(function, x, 0.00001))
                        .thenReturn(res);
            }
        } catch (IOException | CsvException e) {
            System.out.println("Ошибка при загрузке CSV: " + path);
        } catch (Exception e) {
            System.out.println("Ошибка при мокации метода calculate: " + e.getMessage());
        }
    }


    private void runTest(FunctionSystem functionSystem, Double x, Double trueResult) {
        try {
            double result = functionSystem.calculate(x, 0.00001);
            assertEquals(trueResult, result, 0.0001);
        } catch (ArithmeticException e) {
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

        FunctionSystem functionSystem = new FunctionSystem(new LnImpl(),new LogImpl(2),new LogImpl(3),new LogImpl(5), new LogImpl(10), cosMock, cotMock, cscMock, secMock, sinMock, tanMock);

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

        FunctionSystem functionSystem = new FunctionSystem(new LnImpl(),new LogImpl(2),new LogImpl(3),new LogImpl(5), new LogImpl(10), new CosImpl(), new CotImpl(), new CscImpl(), new SecImpl(), new SinImpl(), new TanImpl());

        runTest(functionSystem, x, trueResult);
    }
}
