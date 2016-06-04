package javacalculus.test;

import javacalculus.core.CalculusEngine;
import javacalculus.evaluator.extend.Calc1ParamFunctionEvaluator;
import javacalculus.evaluator.extend.Calc2ParamFunctionEvaluator;
import javacalculus.struct.*;
import nsccsclub.isogum.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anjeo on 4/15/2016.
 */
public class derivateCalc {
    private CalcFunction derivative;
    public CalcFunction function;
    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    private HashMap<String,Variable> variables;
    private static CalculusEngine engine;

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
    }

    public derivateCalc(String f, Map<String,Variable> var)
    {
        engine = new CalculusEngine();
        variables = new HashMap<String,Variable>();
        f.replaceAll(" ","");
        String [] split = f.split("=");
        if(split.length!=2){
            throw new IllegalArgumentException("Too many equal signs");
        }
        String [] name = f.split("\\)",2);
        if(name.length != 2){
            throw new IllegalArgumentException("Too many brackets!");
        }
        String func = split[1];
        String [] splitVar = name[0].split("\\(");
        String [] vars = splitVar[1].split(",");
        function = (CalcFunction)engine.parser("DIFF("+func+","+vars[0]+")").getParameters().get(0);

      /*  for(int i = 0; i < vars.length; i++){
            Variable var = new Variable(null);
           // var.derivative = engine.derivative("DIFF("+func+","+vars[i]+")");
           // var.derivativeName= engine.execute("DIFF("+func+","+vars[i]+")");
            variables.put(vars[i],var);
        }*/

    }
    public double recursiveEvaluate(){
        return recursiveEvaluate(function,"",0);
    }
    public double recursiveEvaluate(CalcObject object, String var, double increment){
        if(object instanceof CalcInteger){
            CalcInteger integer = (CalcInteger) object;
            return integer.intValue();
        }

        if(object instanceof CalcSymbol){
            CalcSymbol symbol = (CalcSymbol) object;
            String name = symbol.getName();
            if(var.equals(name)){
                return variables.get(symbol.getName()).getValue() + increment;
            }
            return variables.get(symbol.getName()).getValue();
        }
        double result = 0;
        CalcFunction function = new CalcFunction(null);
        if (object instanceof CalcFunction){
            function = (CalcFunction) object;
        }
        CalcSymbol functionHeader = function.functionHeader;
        if(functionHeader.getName().equals("ADD")){
            for(CalcObject functions: function.getParameters()){
                result += recursiveEvaluate( functions,var,increment);
            }
            return result;
        }
        else if(functionHeader.getName().equals("SUBTRACT")){
            for(CalcObject functions: function.getParameters()){
                result -= recursiveEvaluate( functions,var,increment);
            }
            return result;
        }
        else if(functionHeader.getName().equals("MULTIPLY")){
            result += 1;
            for(CalcObject functions: function.getParameters()){
                result *= recursiveEvaluate( functions,var,increment);
            }
            return result;
        }
        else if (functionHeader.evaluator instanceof Calc2ParamFunctionEvaluator){
            ArrayList<CalcObject> parameter = function.getParameters();
            result = recursiveEvaluate(parameter.get(0),var,increment);
            CalcDouble calc = new CalcDouble(result);
            Calc2ParamFunctionEvaluator evaluator = (Calc2ParamFunctionEvaluator)functionHeader.evaluator;
            CalcFunction holder = new CalcFunction(functionHeader,calc, parameter.get(1));
            return( (CalcDouble)evaluator.evaluate(holder)).doubleValue();
        }
        else {
            if(functionHeader.getName().equals("POWER")){
                System.out.println(object instanceof Calc2ParamFunctionEvaluator);
            }
            for(CalcObject functions: function.getParameters()){
                result += recursiveEvaluate(functions,var,increment);
            }
            Calc1ParamFunctionEvaluator evaluator = (Calc1ParamFunctionEvaluator)functionHeader.evaluator;
            CalcDouble calc = new CalcDouble(result);
            CalcFunction holder = new CalcFunction(functionHeader,calc);
            return ((CalcDouble)evaluator.evaluate(holder)).doubleValue();
        }
    }
    public  ArrayList<Double> derivateCalculator(){
        ArrayList<Double> result = new ArrayList<Double> ();
        HashMap<String,Variable> clone = new HashMap<String,Variable>(variables);
        double uncertainity = 0;
        for(String var : clone.keySet()){
            double value = partialDerivate(var);
            result.add(value*clone.get(var).getUncertainty());
        }
        return  result;
    }


    private Double partialDerivate(String var) {
       double h = 0.1;
        double error = Double.MAX_VALUE;

        double old = approximate(var,h);
        double tol = Math.pow(10,-10);

        while(error > tol){
            h = h/2;
            double f1 = old;
            double f2 = approximate(var,h);
            double fnew = 4/3*f2-1/3*f1;
            error = Math.abs(fnew-old)/fnew;
            old = fnew;
        }
        return old;
        /*double h = Math.pow(10,-4);
        double term1 = recursiveEvaluate(function,var,h);
        double term2 = recursiveEvaluate(function,var,-h );
        double dif = term1 - term2;
        double result = dif/(2*h);
        return result;*/
    }

    private Double approximate(String var, double h)
    {
        Variable update = variables.get(var);
        double old = update.getValue();
        double term1 = recursiveEvaluate(function,var, h);
        double term2 =  recursiveEvaluate(function,var,-h);
        double term3=recursiveEvaluate(function,var,2*h);
        double term4 = recursiveEvaluate(function,var,-2*h);
      return (8*term1 - 8*term2 - term3 + term4)/(12*h);
    }
}
