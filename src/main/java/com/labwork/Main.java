package com.labwork;


import com.labwork.logarithms.*;
import com.labwork.trigonometric.*;

public class Main {
    public static void main(String[] args) {


        LnImpl ln = new LnImpl();
        Log2Impl log2 = new Log2Impl();
        Log5Impl log5 = new Log5Impl();
        Log3Impl log3 = new Log3Impl();
        Log10Impl log10 = new Log10Impl();
        SinImpl sin = new SinImpl();
        CosImpl cos = new CosImpl(sin);
        CotImpl cot = new CotImpl(sin, cos);
        CscImpl csc = new CscImpl(sin);
        SecImpl sec = new SecImpl(cos);
        TanImpl tan = new TanImpl(sin, cos);

        FunctionSystem function = new FunctionSystem(ln, log2, log3, log5, log10, cos, cot, csc, sec, sin, tan);
        System.out.println("Result:" + function.calculate(-7, 0.0001));


        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeResults("sin.csv", -5, 5, sin, 0.1);
        csvWriter.writeResults("cos.csv", -5, 5, cos, 0.1);
        csvWriter.writeResults("sec.csv", -5, 5, sec, 0.1);
        csvWriter.writeResults("cot.csv", -5, 5, cot, 0.1);
        csvWriter.writeResults("tan.csv", -5, 5, tan, 0.1);
        csvWriter.writeResults("csc.csv", -5, 5, csc, 0.1);



        csvWriter.writeResults("ln.csv", 0.1, 5, ln, 0.1);
        csvWriter.writeResults("log2.csv", 0.1, 5, log2, 0.1);
        csvWriter.writeResults("log3.csv", 0.1, 5, log3, 0.1);

        csvWriter.writeResults("log5.csv", 0.1, 5, log5, 0.1);
        csvWriter.writeResults("log10.csv", 0.1, 5, log10, 0.1);

        csvWriter.writeResults("system.csv", -5, 5, function, 0.1);
    }
}