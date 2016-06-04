package nsccsclub.isogum;

/**
 * A variable to be passed into a function object for the ISO GUM calculator.
 * Created by csconway on 4/21/2016.
 */
public class Variable implements Comparable<Variable>{
    /**
     * The unique name of the variable.
     */
    private String name;
    /**
     * The value of the variable. Must be unique, whenever creating an object, use the isDuplicate
     * methods in DBHandler to test for name uniqueness.
     */
    private double value;
    /**
     * The measurement uncertianty of the variable. Automatically converted to a positive number.
     */
    private double uncertainty;
    /**
     * The database id of the variable.
     */
    private long id;

    /**
     * The constructor for variables that have not been stored in the database yet. Always check for
     * uniqueness of the name using the appropriated method in DBHandler before storing objects.
     * @param name The unique name of the variable.
     * @param value The value of the variable.
     * @param uncertainty The measurement uncertainty of the variable.
     */
    public Variable(String name, double value, double uncertainty){
        this.setName(name);
        this.setValue(value);
        this.setUncertainty(uncertainty);
        //id has not been set by database yet
        this.setId(-1);
    }

    /**
     * Constructor for pulling an existing variable from the database, in most contexts the use of
     * DBHandlers get variable will do the job better, and be more clear.
     * @param name The unique name of the variable
     * @param value The value of the variable.
     * @param uncertainty The measurement uncertainty of the varaible.
     * @param id The unique database id of the variable.
     */
    public Variable(String name, double value, double uncertainty, long id){
        this.setName(name);
        this.setValue(value);
        this.setUncertainty(uncertainty);
        this.setId(id);
    }

    /**
     * Gives the unique name of the variable.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gives the varaible a new unique name. Use the isDuplicate method in DBHandler to test for
     * uniqueness.
     * @param name The new unique name of the variable.
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Gives the value of a variable.
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets a new value of the variable.
     * @param value The new value.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gives the measurement uncertainty of the variable.
     * @return The measurement uncertainty of the variable.
     */
    public double getUncertainty() {
        return uncertainty;
    }

    /**
     * Sets the uncertianty of the variable, automatically converted to a positive number.
     * @param uncertainty The new uncertainty of the variable.
     */
    public void setUncertainty(double uncertainty) {
        this.uncertainty = Math.abs(uncertainty);
    }

    /**
     * Gives the database id of the variable, -1 if the value has not been set yet, or properly
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the database id of the variable, should never be called outside of DBHandler.
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Tests for content equality of two vairables with a tolerance of .000001 for the double
     * values. Does not check for databse id.
     * @param variable The variable to test against.
     * @return True for equal, false for not equal.
     */
    public boolean equals(Variable variable){
        return this.name.compareTo(variable.getName())==0 &&
                (this.value <= variable.getValue()+.00001 &&
                        this.value >= variable.getValue() - .00001)&&
                (this.uncertainty <= variable.getUncertainty() + .00001 &&
                this.uncertainty >= variable.uncertainty - .00001);
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * TESTS BASED ON LEXOGRAPHIC NAME
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Variable another) {
        return this.getName().compareTo(another.getName());
    }
}
