package nsccsclub.isogum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


/**
 * Dialog for storing custom variable names.
 * Created by csconway on 4/28/2016.
 */
public class DialogNameTextVariable extends android.support.v4.app.DialogFragment {
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
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(R.string.cancel, null);
        Dialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                final AlertDialog alertDialog = (AlertDialog)dialog;
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = ((EditText)alertDialog.
                                        findViewById(R.id.varaible_name_field)).
                                        getText().toString();
                                if(listener.isTextValid(name)){
                                    listener.onPositiveClick(name);
                                    dialog.dismiss();
                                }
                            }
                        }
                );
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        }
                );
            }
        });
        return dialog;
    }

    @Override
    /**
     * method for initializing fragment host communication, called before on create, initializes
     * fields, and the listener(the host activity).
     */
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogNameTextVariable.NameDialogListener) {
            listener = (NameDialogListener) context;

        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement NameDialogListnerf");
        }
    }

    public interface NameDialogListener{
        /**
         * Check to see if a given variable name is valid
         * <div>
         *     <h3>Rules:</h3>
         *     <ul>
         *         <li>Names must be shorter than 40 characters</li>
         *         <li>Names must be unique</li>
         *         <li>Cannot name an object e or pi</li>
         *     </ul>
         * </div>
         * @param name The name to check.
         * @return True for valid, false for invalid
         */
        boolean isTextValid(String name);

        /**
         * Inserts a given string into the create funciton edit text field as a variable placeholder.
         * @param name The string name of the placeholder to insert.
         */
        void onPositiveClick(String name);
    }
}
