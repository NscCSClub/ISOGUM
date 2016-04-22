package nsccsclub.isogum;

/**
 * This is the bare shell for a funciton object I created it to test an SQL LITE data base
 * none of the methods have been checked debugged or commented this needs to be reworked
 * Created by csconway on 4/21/2016.
 */
public class Function {
    private String name;
    private String function;
    private int id;

    public Function(String name, String function, int id){
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
