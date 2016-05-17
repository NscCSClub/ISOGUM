package nsccsclub.isogum;

import java.util.Comparator;

/**
 * Sorts functions based on database id, E.G time added.
 * Created by csconway on 5/17/2016.
 */
public class FunctionIDSorter implements Comparator<Function> {
    @Override
    public int compare(Function f1, Function f2){
        return (int)(f1.getId()-f2.getId());
    }
}
