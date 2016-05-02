package nsccsclub.isogum;

import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by csconway on 5/1/2016.
 */
public class FunctionParserTest extends TestCase {
    public final String LOG_CODE = "FunctionParserTest";


    public void testGetNext() throws Exception {
        FunctionParser parser = new FunctionParser("sin");
//        Log.d(LOG_CODE, parser.getNext().getType().toString());
        assertTrue(parser.getNext().getType() == FunctionParser.Type.SIN);
        parser.setFunction("cos");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.COS);
        parser.setFunction("tan");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.TAN);
        parser.setFunction("aSin");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.ASIN);
        parser.setFunction("aCos");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.ACOS);
        parser.setFunction("aTan");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.ATAN);
        parser.setFunction("log");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LOG);
        parser.setFunction("ln");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LN);
        parser.setFunction("(");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LEFT_PAREN);
        parser.setFunction(")");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.RIGHT_PAREN);
        parser.setFunction("[pi]");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.VARIABLE);
        parser.setFunction("hi");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.UNDEFINED);
        parser.setFunction("33");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.NUMBER);
        parser.setFunction("42.6969");
//        Log.d(LOG_CODE,"testing double " +parser.getNext().getValue());
        assertTrue(parser.getNext().getType() == FunctionParser.Type.NUMBER);

        parser.setFunction("[e]^cos(34.8888+69)*(45^2)*tan(cos(aTan()))");
        assertTrue(parser.getNext().getType() == FunctionParser.Type.VARIABLE);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.OPERATOR);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.COS);

        assertTrue(parser.getNext().getType() == FunctionParser.Type.LEFT_PAREN);

        assertTrue(parser.getNext().getType() == FunctionParser.Type.NUMBER);


        assertTrue(parser.getNext().getType() == FunctionParser.Type.OPERATOR);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.NUMBER);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.RIGHT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.OPERATOR);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LEFT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.NUMBER);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.OPERATOR);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.NUMBER);

        assertTrue(parser.getNext().getType() == FunctionParser.Type.RIGHT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.OPERATOR);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.TAN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LEFT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.COS);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LEFT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.ATAN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.LEFT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.RIGHT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.RIGHT_PAREN);
        assertTrue(parser.getNext().getType() == FunctionParser.Type.RIGHT_PAREN);
        parser.setFunction("36sin");
        while (parser.hasNext()){
            Log.d(LOG_CODE,"hi there! "+parser.getNext().toString());
        }



    }



    public void testHasNext() throws Exception {
        FunctionParser parser = new FunctionParser();

        parser.setFunction("[e]^cos(34.8888+69)*(45^2)*tan(cos(aTan()))");

        while(parser.hasNext()){

            Log.d(LOG_CODE, parser.getNext().toString());
        }


    }

    public void testSetFunction() throws Exception {

    }

    public void testIsValid() throws Exception {
        FunctionParser parser = new FunctionParser();
        parser.setFunction("sin");
        assertFalse(parser.isValid());
        parser.setFunction("cos");
        assertFalse(parser.isValid());
        parser.setFunction("tan");
        assertFalse(parser.isValid());
        parser.setFunction("aSin");
        assertFalse(parser.isValid());
        parser.setFunction("aCos");
        assertFalse(parser.isValid());
        parser.setFunction("aTan");
        assertFalse(parser.isValid());
        parser.setFunction("log");
        assertFalse(parser.isValid());
        parser.setFunction("ln");
        assertFalse(parser.isValid());
        parser.setFunction("sin(");
        assertFalse(parser.isValid());
        parser.setFunction("sin()");
        assertFalse(parser.isValid());
        parser.setFunction("36sin");
        while (parser.hasNext()){
            Log.d(LOG_CODE,"testing123 " + parser.getNext().toString());
        }
        parser.setFunction("36sin");
        assertFalse(parser.isValid());
        parser.setFunction("42sin(42)");
        assertFalse(parser.isValid());
        parser.setFunction("sin)");
        assertFalse(parser.isValid());
        parser.setFunction("(");
        assertFalse(parser.isValid());
        parser.setFunction(")");
        assertFalse(parser.isValid());
        parser.setFunction("()");
        assertFalse(parser.isValid());
        parser.setFunction("*");
        assertFalse(parser.isValid());
        parser.setFunction("-");
        assertFalse(parser.isValid());
        parser.setFunction("+");
        assertFalse(parser.isValid());
        parser.setFunction("/");
        assertFalse(parser.isValid());
        parser.setFunction("^");
        assertFalse(parser.isValid());
        parser.setFunction("[hello]");
        assertTrue(parser.isValid());
        parser.setFunction("[h]");
        assertTrue(parser.isValid());
        parser.setFunction("3");
        assertTrue(parser.isValid());
        parser.setFunction("33.33");
        assertTrue(parser.isValid());
        parser.setFunction("[hello]*33");
        while(parser.hasNext()){
            Log.d(LOG_CODE, "deb"+ parser.getNext().toString());
        }

        parser.setFunction("[hello]*33");
        assertTrue(parser.isValid());

        parser.setFunction("[hello]*33*cos(36)");
        assertTrue(parser.isValid());
        parser.setFunction("[hello]*33*tan(47)");
        assertTrue(parser.isValid());
        parser.setFunction("[e]^cos(34.8888+69)*(45^2)*tan(cos(aTan()))");
        assertTrue(parser.isValid());







    }
}