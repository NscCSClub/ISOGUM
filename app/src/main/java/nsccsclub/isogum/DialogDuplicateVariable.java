package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Used to alert the user of a duplicate variable name in the database, used as an entry point to
 * the create variable activity.
 * Created by Collin on 5/26/2016.
 */
public class DialogDuplicateVariable extends DialogFragment {

    private DuplicateVariableListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.duplicate_variable_message);
        builder.setPositiveButton(R.string.save,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //tells host activity to go ahead and overwrite the variable
                listener.overwriteVarible();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogDuplicateVariable.this.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // makes sure the host activity has appropriate interface callbacks
        try{
            listener = (DuplicateVariableListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException("activity does not implement appropriate nested interface");
        }
    }

    public interface DuplicateVariableListener{
        /**
         * calls the create variable activity to edit and save over an existing variable.
         */
        public void overwriteVarible();
    }
}
