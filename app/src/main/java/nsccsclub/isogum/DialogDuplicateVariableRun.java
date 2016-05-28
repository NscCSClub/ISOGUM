package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Used for chekcing for dupliacte variable names for the run screen.
 * Created by Collin on 5/27/2016.
 */
public class DialogDuplicateVariableRun extends DialogFragment{

    /**
     * The parent activity, must implement appropriate listener.
     */
    private DuplicateVariableListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.duplicate_variable_message);
        builder.setPositiveButton(R.string.save,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // yes we want to override, call run activity with this name.
                listener.overwriteVaribleRun();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // no we do not want to override, do nothing and dismiss
                DialogDuplicateVariableRun.this.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // makes sure that the parent activity implements the appropriate listener
        try{
            listener = (DuplicateVariableListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException("activity does not implement appropriate nested interface");
        }
    }

    public interface DuplicateVariableListener{
        /**
         * Launch the run  activity with this variable name, and yes we want to overwrite.
         */
        public void overwriteVaribleRun();
    }
}
