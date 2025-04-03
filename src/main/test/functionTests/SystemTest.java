package functionTests;


import com.labwork.FunctionSystem;
import com.labwork.interfaces.FunctionInterface;
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
import java.lang.reflect.InvocationTargetException;
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
            if (records.isEmpty()) {
                System.out.println("Файл пустой: " + path);
                return; // Прерываем выполнение метода, если файл пустой
            }

            records.remove(0); // Убираем заголовок

            for (String[] record : records) {
                double x = Double.parseDouble(record[0]);
                double res = Double.parseDouble(record[1]);

                // Проверка на NaN или Infinity


                // Мокируем вызов calculate
                when(function.calculate(Mockito.anyDouble(), Mockito.eq(0.00001))).thenReturn(res);
            }
        } catch (IOException | CsvException e) {
            System.out.println("Ошибка при загрузке CSV: " + path);
        }
    }




    private void runTest(FunctionSystem functionSystem, Double x, Double trueResult) {
        try {
            double result = functionSystem.calculate(x, 0.00001);
            System.out.println("Testing value x: " + x + ", calculated result: " + result);
            assertEquals(trueResult, result, 0.0001);
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
