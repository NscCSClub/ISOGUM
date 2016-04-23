package nsccsclub.isogum;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;

/**
 * Created by csconway on 4/23/2016.
 */
public class DBHandlerTest extends AndroidTestCase {

    private DBHandler db;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new DBHandler(context);

    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testOnCreate() throws Exception {

    }

    public void testOnUpgrade() throws Exception {

    }

    public void testCreateFunction() throws Exception {
        Function function = new Function("test","f(x)");
        db.createFunction(function);
        long id = db.findFunctionByName("test");
        assertTrue(function.equals(db.getFunction(id)));

    }

    public void testGetFunction() throws Exception {
        Function function = new Function("test1","f(x)");
        db.createFunction(function);
        long id = db.findFunctionByName("test1");
        assertTrue(function.equals(db.getFunction(id)));
    }

    public void testGetAllFunctions() throws Exception {
        Function function2 = new Function("test2","f(x)");
        db.createFunction(function2);
        Function function3 = new Function("test3","f(x)");
        db.createFunction(function3);
        Function function4 = new Function("test4","f(x)");
        db.createFunction(function4);
        List<Function> list = db.getAllFunctions();

        Iterator<Function> iterator = list.listIterator();
        Function test;
        while (iterator.hasNext()){
            test = iterator.next();
            if(!test.equals(function2)||
                    !test.equals(function3)||
                    !test.equals(function4)){
                fail();
            }
        }
    }

    public void testUpdateFunction() throws Exception {
        Function function5 = new Function("test5", "f(x)");
        db.createFunction(function5);
        function5.setFunction("f(y)");
        function5.setId(db.findFunctionByName(function5.getName()));
        db.updateFunction(function5);
        assertTrue(function5.equals(db.getFunction(function5.getId())));
    }

    public void testDeleteFunction() throws Exception {

    }

    public void testIsDuplicateFunction() throws Exception {

    }

    public void testFindFunctionByName() throws Exception {

    }

    public void testCreateVariable() throws Exception {

    }

    public void testGetVariable() throws Exception {

    }

    public void testGetAllVariables() throws Exception {

    }

    public void testUpdateVariable() throws Exception {

    }

    public void testDeleteVariable() throws Exception {

    }

    public void testIsDuplicateVariable() throws Exception {

    }

    public void testFindVariableByName() throws Exception {

    }
}