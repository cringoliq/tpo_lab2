package com.labwork;


import com.labwork.logarithms.LnImpl;
import com.labwork.logarithms.LogImpl;
import com.labwork.trigonometric.*;

public class Main {
    public static void main(String[] args) {


        LnImpl ln = new LnImpl();
        LogImpl log2 = new LogImpl(2);
        LogImpl log5 = new LogImpl(5);
        SinImpl sin = new SinImpl();
        CosImpl cos = new CosImpl(sin);
        CotImpl cot = new CotImpl(sin, cos);
        CscImpl csc = new CscImpl(sin);
        SecImpl sec = new SecImpl(cos);
        TanImpl tan = new TanImpl(sin, cos);

        FunctionSystem function = new FunctionSystem(ln, log2, log5, cos, cot, csc, sec, sin);

        System.out.println("Result:" + function.calculate(-10, 0.01));


        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeResults("sin.csv", -5, 5, sin, 0.1);
        csvWriter.writeResults("cos.csv", -5, 5, cos, 0.1);
        csvWriter.writeResults("sec.csv", -5, 5, sec, 0.1);
        csvWriter.writeResults("cot.csv", -5, 5, cot, 0.1);
        csvWriter.writeResults("tan.csv", -5, 5, tan, 0.1);
        csvWriter.writeResults("csc.csv", -5, 5, csc, 0.1);


        csvWriter.writeResults("ln.csv", 0.00001, 5, ln, 0.1);
        csvWriter.writeResults("log2.csv", 0.00001, 5, log2, 0.1);
        csvWriter.writeResults("log5.csv", 0.00001, 5, log5, 0.1);
        csvWriter.writeResults("system.csv", -5, 5, function, 0.1);
    }
}