package nsccsclub.isogum;

import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Collin on 5/18/2016.
 */
public class FunctionTest extends AndroidTestCase{
    public static final String LOG_CODE = "FunctionTest";

    @Test
    public void testTranslator() throws Exception {
        Function function = new Function("test","[x]^2+3.55555-cos([thisis])");
        //cannot debug exactly because of order of returned items in a set
        //use log codes and verify
//        Log.d(LOG_CODE,"function : "+ function.getFunction()+ "\n"+
//        "translation : " + function.translator());

    }

    @Test
    public void testNumVariables() throws Exception {

        Function function = new Function("test","[x]^2+3.55555-cos([thisis])");
        Assert.assertTrue(function.getNumVariables() == 2);

    }
}