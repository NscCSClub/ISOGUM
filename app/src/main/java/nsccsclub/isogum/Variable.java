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
    private long id;

    public Variable(String name, double value, double uncertainty){
        this.setName(name);
        this.setValue(value);
        this.setUncertainty(uncertainty);
        //id has not been set by database yet
        this.setId(-1);
    }

    public Variable(String name, double value, double uncertainty, long id){
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean equals(Variable variable){
        return this.name.compareTo(variable.getName())==0 &&
                (this.value <= variable.getValue()+.001 &&
                        this.value >= variable.getValue() - .001)&&
                (this.uncertainty <= variable.getUncertainty() + .001 &&
                this.uncertainty >= variable.uncertainty - .001);
    }
}
