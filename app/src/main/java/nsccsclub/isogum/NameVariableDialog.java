package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Dialog for storing custom variable names.
 * Created by csconway on 4/28/2016.
 */
public class NameVariableDialog extends android.support.v4.app.DialogFragment {
    /**
     * listner to handle key events from host activity. The host activity must implement the
     * listener interface and effectively acts as the listener for this fragment.
     */
    public NameDialogListener listener;
    @Override
    /**
     * Created the dialog for storing custom variable names.
     */
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //inflate the layout
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.dialog_name_variable_layout,null))
            //se the listeners
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onPositiveClick(NameVariableDialog.this);
                }
            })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NameVariableDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    /**
     * method for initializing fragment host communication, called before on create, initializes
     * fields, and the listener(the host activity).
     */
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NameDialogListener) {
            listener = (NameDialogListener) context;

            throw new RuntimeException(context.toString()
                    + " must implement NameDialogListnerf");
        }
    }

    public interface NameDialogListener{
        public void onPositiveClick(DialogFragment dialog);
    }
}
