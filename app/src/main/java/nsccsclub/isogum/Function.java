package nsccsclub.isogum;

import android.util.Log;

/**
 * This is the bare shell for a funciton object I created it to test an SQL LITE data base
 * none of the methods have been checked debugged or commented this needs to be reworked
 * Created by csconway on 4/21/2016.
 */
public class Function {
    private String name;
    private String function;
    private long id;

    public Function(String name, String function){
        this.setName(name);
        this.setFunction(function);
        // id has not been set by database yet
        this.setId(-1);
    }
    public Function(String name, String function, long id){
        this.setName(name);
        this.setFunction(function);
        this.setId(id);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean equals(Function function){
        boolean nameTest = this.name.compareTo(function.getName())==0;
        boolean functionTest = this.function.compareTo(function.getFunction())==0;
//        Log.d("Function", String.valueOf(nameTest));
//        Log.d("Function", String.valueOf(functionTest));
        return nameTest && functionTest;
    }
}
