package javacalculus;

import javacalculus.core.CalculusEngine;

/**
 * Created by anjeo on 2/12/2016.
 */
public class uncertainityCalculuator {
    protected CalculusEngine engine;
    public static void main(String [] args){
      /* CalculusEngine engine = new CalculusEngine();
        // CalcFunction function1 = engine.derivative("DIFF(ln(x),x)");
        CalcFunction function2 = engine.derivative("DIFF(SIN(x)*y,y)");
        // String result = engine.executae(QUERY);
       // CalcFunctionEvaluator evaluate= y.getEvaluator();
        System.out.println(function2);
            Scanner y = new Scanner(System.in);
           // CalcFunction f = engine.derivative("DIFF(SIN(x*y),x)");
        System.out.println("what is the function name?");
            String name = y.nextLine();
            uncertainityCalculuator test = new uncertainityCalculuator();
           derivateCalc function = new derivateCalc(name,null);
           // System.out.println("The derivative is:");
            HashMap<String,Variable> vars = function.getVariables();
           HashMap<String,Variable> temp = new HashMap<String,Variable>();
          for(String value : vars.keySet()){
                System.out.println("What is the value of "+ value);
                Double next = y.nextDouble();
                temp.put(value,new Variable(next));
           }
           function.setVariables(temp);
        ArrayList<Double>  numerical = function.derivateCalculator();
        int i = 0;
        for(String value : vars.keySet()){
               // System.out.println("the derivative with respect to: "+value+" is "+vars.get(value).derivativeName);
            //double exact = function.recursiveEvaluate(vars.get(value).derivative);
           // System.out.println("Evaluated at the given points is: "+exact);
            System.out.println("Numerical Derivative evaluted with respect to: "+value+" is "+numerical.get(i));
           // System.out.println("Numerical Derivative Vs Exact "+value+" is "+(numerical.get(i)-exact)*100/exact + "%");
            System.out.println("Evaluated at the given points is: "+function.recursiveEvaluate(function.function));

            i++;
            }*/


        }


    }




