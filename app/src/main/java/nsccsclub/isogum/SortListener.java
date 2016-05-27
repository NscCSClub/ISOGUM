package nsccsclub.isogum;

/**
 * This is an interface to allow the function and variable activities to be able to sort their
 * content created new file to avoid cyclic inheritence compiler error.
 * Created by Collin on 5/26/2016.
 */
public interface SortListener{
    public void refreshListByName();
    public void refreshListByDate();
}
