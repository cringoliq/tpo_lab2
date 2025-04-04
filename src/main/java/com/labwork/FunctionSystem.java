package com.labwork;

import com.labwork.interfaces.FunctionInterface;
import com.labwork.logarithms.*;
import com.labwork.trigonometric.*;

import com.labwork.trigonometric.CosImpl;
import com.labwork.trigonometric.SinImpl;


public class FunctionSystem implements FunctionInterface {

    LnImpl ln;
    Log2Impl log2;
    Log5Impl log5;
    Log10Impl log10;
    Log3Impl log3;
    CosImpl cos;
    CotImpl cot;
    CscImpl csc;
    SecImpl sec;
    TanImpl tan;
    SinImpl sin;

    public FunctionSystem() {
        ln = new LnImpl();
        log2 = new Log2Impl();
        log5 = new Log5Impl();
        log3 = new Log3Impl();
        log10 = new Log10Impl();
        cos = new CosImpl();
        cot = new CotImpl();
        csc = new CscImpl();
        sec = new SecImpl();
        tan = new TanImpl();
        sin = new SinImpl();
    }

    public FunctionSystem(LnImpl ln, Log2Impl log2,Log3Impl log3, Log5Impl log5, Log10Impl log10, CosImpl cos, CotImpl cot, CscImpl csc, SecImpl sec, SinImpl sin, TanImpl tan) {
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
        System.out.println("FunctionSystem: calculate(" + x + ", " + delta + ")");

        if(x<=0){
               return (((((Math.pow(((csc.calculate(x,delta) - tan.calculate(x,delta)) / csc.calculate(x,delta)) ,2)) * (cot.calculate(x,delta) + csc.calculate(x,delta))) * cos.calculate(x,delta)) + (sec.calculate(x,delta) * cot.calculate(x,delta))) / tan.calculate(x,delta));
        }
        else{
            return (((((log5.calculate(x, delta) - log3.calculate(x, delta)) - log10.calculate(x, delta)) + log3.calculate(x, delta)) / (log2.calculate(x, delta) * (log3.calculate(x, delta) / (log10.calculate(x, delta) * ln.calculate(x, delta))))) * (log10.calculate(x, delta) + (ln.calculate(x, delta) - log2.calculate(x, delta))));
        }
    }





}
