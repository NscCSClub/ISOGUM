package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;


import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Prompts the user if they want to edit and save over an existing function. Used as an entry point
 * to the create function activity.
 * Created by Rohan on 5/27/16.
 */
public class DialogDuplicateFunction extends DialogFragment {

    /**
     * The parent activity, must implement interface.
     */
    private DialogDuplicateFunctionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.duplicate_function_message);
        builder.setPositiveButton(R.string.save,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //go ahead and save over the function
                listener.overwriteFunction();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancel and do nothing
                DialogDuplicateFunction.this.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // makes sure the host activity implements the interface
        try{
            listener = (DialogDuplicateFunctionListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException("activity does not implement appropriate nested interface");
        }
    }

    public interface DialogDuplicateFunctionListener{
        /**
         * Call the create function activity to edit and save over an existing function.
         */
        public void overwriteFunction();
    }
}