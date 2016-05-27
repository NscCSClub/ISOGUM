package nsccsclub.isogum;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;

/**
 * tests for crud methods in the databsase
 * most recently downgraded tonot include symbolic derivatives
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
            if(!(test.equals(function2)||
                    test.equals(function3)||
                    test.equals(function4))){
                fail();
            }
        }
    }

    public void testUpdateFunction() throws Exception {
        Function function5 = new Function("test5", "f(x)");
        db.createFunction(function5);
        function5.setFunction("f(y)");
//        function5.setDerivative("f'(y)");
        function5.setId(db.findFunctionByName(function5.getName()));
        db.updateFunction(function5);
        assertTrue(function5.equals(db.getFunction(function5.getId())));
    }

    public void testDeleteFunction() throws Exception {
        Function function = new Function("Delete", "me");
        db.createFunction(function);
        function.setId(db.findFunctionByName(function.getName()));
        db.deleteFunction(function);
        List<Function> list = db.getAllFunctions();
        if (list.size()!= 0){
            fail();
        }
    }

    public void testIsDuplicateFunction() throws Exception {
        Function function = new Function("Am I", "a duplicate?");
        db.createFunction(function);
        assertTrue(db.isDuplicateFunction(function));

        Function function1 = new Function("I", "am not");
        assertFalse(db.isDuplicateFunction(function1));
    }

    public void testFindFunctionByName() throws Exception {
        Function function = new Function("find", "me");
        Function function1 = new Function("Dont", "find me");
        db.createFunction(function);
        db.createFunction(function1);
        Function function2 = db.getFunction(db.findFunctionByName(function.getName()));

        assertTrue(function2.equals(function));
        assertFalse(function2.equals(function1));
    }

    public void testCreateVariable() throws Exception {
        Variable variable = new Variable("hello",3,4);
        db.createVariable(variable);
        Variable variable1 = db.getVariable(db.findVariableByName(variable.getName()));
        assertTrue(variable.equals(variable1));
    }

    public void testGetVariable() throws Exception {
        Variable variable = new Variable("hello", 1,2);
        Variable variable1 = new Variable("hi there", 1, 2);
        Variable variable2 = new Variable("how ya doin", 3, 4);
        db.createVariable(variable);
        db.createVariable(variable1);
        db.createVariable(variable2);

        Variable variable3 = db.getVariable(db.findVariableByName(variable1.getName()));

        assertTrue(variable3.equals(variable1));

    }

    public void testGetAllVariables() throws Exception {
        Variable variable = new Variable("hello", 1,2);
        Variable variable1 = new Variable("hi there", 1, 2);
        Variable variable2 = new Variable("how ya doin", 3, 4);
        db.createVariable(variable);
        db.createVariable(variable1);
        db.createVariable(variable2);

        List<Variable> list = db.getAllVariables();
        Iterator<Variable> iterator = list.listIterator();

        Variable test;
        while (iterator.hasNext()){
            test = iterator.next();
            if (!(test.equals(variable) ||
            test.equals(variable1) || test.equals(variable2))){
                fail();
            }
        }
    }

    public void testUpdateVariable() throws Exception {
        Variable variable = new Variable("hello", 1,2);
        Variable variable1 = new Variable("hi there", 1, 2);
        Variable variable2 = new Variable("how ya doin", 3, 4);
        db.createVariable(variable);
        db.createVariable(variable1);
        db.createVariable(variable2);

        variable.setId(db.findVariableByName(variable.getName()));
        variable.setValue(-1);
        db.updateVariable(variable);

        assertTrue(variable.equals(db.getVariable(variable.getId())));
    }

    public void testDeleteVariable() throws Exception {
        Variable variable = new Variable("hello", 1,2);
        Variable variable1 = new Variable("hi there", 1, 2);
        Variable variable2 = new Variable("how ya doin", 3, 4);
        db.createVariable(variable);
        db.createVariable(variable1);
        db.createVariable(variable2);
        variable.setId(db.findVariableByName(variable.getName()));
        db.deleteVariable(variable);

        assertTrue(-1 == db.findVariableByName(variable.getName()));

    }

    public void testIsDuplicateVariable() throws Exception {
        Variable variable = new Variable("hello", 1,2);
        Variable variable1 = new Variable("hi there", 1, 2);
        Variable variable2 = new Variable("how ya doin", 3, 4);
        db.createVariable(variable);
        db.createVariable(variable1);
        db.createVariable(variable2);

        Variable variable3 = new Variable("I am not in the database", 1,2);

        assertTrue(db.isDuplicateVariable(variable));
        assertFalse(db.isDuplicateVariable(variable3));

    }

    public void testFindVariableByName() throws Exception {
        Variable variable = new Variable("hello", 1,2);
        Variable variable1 = new Variable("hi there", 1, 2);
        Variable variable2 = new Variable("how ya doin", 3, 4);
        db.createVariable(variable);
        db.createVariable(variable1);
        db.createVariable(variable2);

        assertTrue(variable.equals(db.getVariable(db.findVariableByName(variable.getName()))));
        assertTrue(variable1.equals(db.getVariable(db.findVariableByName(variable1.getName()))));
        assertTrue(variable2.equals(db.getVariable(db.findVariableByName(variable2.getName()))));

    }
}