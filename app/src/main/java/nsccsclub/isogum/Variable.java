package nsccsclub.isogum;

/**
 * This is the bare shell for a funciton object I created it to test an SQL LITE data base
 * none of the methods have been checked debugged or commented this needs to be reworked
 * Created by csconway on 4/21/2016.
 */
public class Variable {
    private String name;
    private double value;
    private double uncertainty;
    private int id;

    public Variable(String name, double value, double uncertainty, int id){
        this.setName(name);
        this.setValue(value);
        this.setUncertainty(uncertainty);
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getUncertainty() {
        return uncertainty;
    }

    public void setUncertainty(double uncertainty) {
        this.uncertainty = uncertainty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
