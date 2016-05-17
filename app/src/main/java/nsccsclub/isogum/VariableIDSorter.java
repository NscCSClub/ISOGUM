package nsccsclub.isogum;

import java.util.Comparator;

/**
 * Compares variables by database id, eg by time added
 * Created by csconway on 5/17/2016.
 */
public class VariableIDSorter implements Comparator<Variable> {
    public int compare(Variable v1, Variable v2){
        return (int)(v1.getId()-v2.getId());
    }
}
