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
    CosImpl cos;
    CotImpl cot;
    CscImpl csc;
    SecImpl sec;
    TanImpl tan;
    SinImpl sin;



    public FunctionSystem(LnImpl ln, LogImpl log2, LogImpl log5, CosImpl cos, CotImpl cot, CscImpl csc, SecImpl sec, SinImpl sin){
        this.ln = ln;
        this.log2 = log2;
        this.log5 = log5;
        this.cos = cos;
        this.cot = cot;
        this.csc = csc;
        this.sec = sec;
        this.sin = sin;
    }
    @Override
    public double calculate(double x, double delta) {
        if(x<=0){
            return (Math.pow(((Math.pow((Math.pow(((((Math.pow((((((Math.pow((tan.calculate(x, delta) + cos.calculate(x,delta)), 3)) + cot.calculate(x, delta)) - (tan.calculate(x, delta) - csc.calculate(x, delta))) - sec.calculate(x, delta)) - csc.calculate(x, delta)), 3)) + (csc.calculate(x, delta) / Math.pow((cot.calculate(x, delta)), 2))) -
                    ((cos.calculate(x, delta) + (Math.pow((sin.calculate(x, delta) / cot.calculate(x, delta)), 3))) - csc.calculate(x, delta))) + (Math.pow(sin.calculate(x, delta), 3))), 2))
                    / (sec.calculate(x, delta) * (sin.calculate(x, delta) * sin.calculate(x, delta))),2))) / ((Math.pow(sin.calculate(x, delta),3) - sec.calculate(x, delta)) +
                    ((csc.calculate(x, delta) / cos.calculate(x, delta)) * ((((cos.calculate(x, delta) * (cos.calculate(x, delta) / cot.calculate(x, delta))) + sec.calculate(x, delta)) - (Math.pow((sin.calculate(x, delta) * csc.calculate(x, delta)),2)))
                            - ((tan.calculate(x, delta) + tan.calculate(x, delta)) * (Math.pow((cos.calculate(x, delta) * csc.calculate(x, delta)),3))))))
                    - (Math.pow((sec.calculate(x, delta) + sin.calculate(x, delta)),3)),2)) / (sin.calculate(x, delta) + ((((Math.pow(cos.calculate(x, delta), 2)) - ((cos.calculate(x, delta) + tan.calculate(x, delta))
                    - (csc.calculate(x, delta) - csc.calculate(x, delta)))) / csc.calculate(x, delta)) * (cos.calculate(x, delta) / ((cot.calculate(x, delta) *
                    ((((sec.calculate(x, delta) + cot.calculate(x, delta)) - sec.calculate(x, delta)) * csc.calculate(x, delta)) / (sec.calculate(x, delta) / sin.calculate(x, delta)))) * ((sin.calculate(x, delta) + cot.calculate(x, delta))
                    / csc.calculate(x, delta))))) * (cot.calculate(x, delta) * (cot.calculate(x, delta) * (((cot.calculate(x, delta) / cot.calculate(x, delta)) +
                    (cot.calculate(x, delta) - Math.pow((Math.pow((cos.calculate(x, delta)),2)) ,3) / sec.calculate(x, delta)))))));
        }
        else{
            return (((((log5.calculate(x, delta) - ln.calculate(x, delta)) - log5.calculate(x, delta)) / ln.calculate(x, delta)) / log2.calculate(x, delta))
                    * (log2.calculate(x, delta) * Math.pow(log5.calculate(x, delta) * (log2.calculate(x, delta) - ln.calculate(x, delta)), 3)));
        }
    }
}
