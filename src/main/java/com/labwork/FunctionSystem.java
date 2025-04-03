package com.labwork;

import com.labwork.interfaces.FunctionInterface;
import com.labwork.logarithms.LnImpl;
import com.labwork.logarithms.LogImpl;
import com.labwork.trigonometric.*;

import com.labwork.trigonometric.CosImpl;
import com.labwork.trigonometric.SinImpl;


public class FunctionSystem implements FunctionInterface {

    LnImpl ln;
    LogImpl log2;
    LogImpl log5;
    LogImpl log10;
    LogImpl log3;
    CosImpl cos;
    CotImpl cot;
    CscImpl csc;
    SecImpl sec;
    TanImpl tan;
    SinImpl sin;

    public FunctionSystem() {
        ln = new LnImpl();
        log2 = new LogImpl(2);
        log5 = new LogImpl(5);
        log3 = new LogImpl(3);
        log10 = new LogImpl(10);
        cos = new CosImpl();
        cot = new CotImpl();
        csc = new CscImpl();
        sec = new SecImpl();
        tan = new TanImpl();
        sin = new SinImpl();
    }

    public FunctionSystem(LnImpl ln, LogImpl log2,LogImpl log3, LogImpl log5, LogImpl log10, CosImpl cos, CotImpl cot, CscImpl csc, SecImpl sec, SinImpl sin, TanImpl tan) {
        this.ln = ln;
        this.log2 = log2;
        this.log5 = log5;
        this.log3 = log3;
        this.log10 = log10;
        this.cos = cos;
        this.cot = cot;
        this.csc = csc;
        this.sec = sec;
        this.sin = sin;
        this.tan = tan;
    }
    @Override
    public double calculate(double x, double delta) {
        if(x<=0){
               return (((((Math.pow(((csc.calculate(x,delta) - tan.calculate(x,delta)) / csc.calculate(x,delta)) ,2)) * (cot.calculate(x,delta) + csc.calculate(x,delta))) * cos.calculate(x,delta)) + (sec.calculate(x,delta) * cot.calculate(x,delta))) / tan.calculate(x,delta));

        //    return (((((((csc.calculate(x,delta) - tan.calculate(x,delta)) / csc.calculate(x,delta)) ^ 2) * (cot.calculate(x,delta) + csc.calculate(x,delta))) * cos.calculate(x,delta)) + (sec.calculate(x,delta) * cot.calculate(x,delta))) / tan.calculate(x,delta));

        }
        else{
            return (((((log5.calculate(x, delta) - log3.calculate(x, delta)) - log10.calculate(x, delta)) + log3.calculate(x, delta)) / (log2.calculate(x, delta) * (log3.calculate(x, delta) / (log10.calculate(x, delta) * ln.calculate(x, delta))))) * (log10.calculate(x, delta) + (ln.calculate(x, delta) - log2.calculate(x, delta))));
        }
    }





}
