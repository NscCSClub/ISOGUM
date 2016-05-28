package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private NameDialogListener listener;
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
                .setNegativeButton(R.string.cancel,null);
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHandler dbHandler = new DBHandler(((Activity) listener).
                                getApplicationContext());
                        String name = ((EditText) (((AlertDialog) dialog).findViewById(R.id.enterText))).getText().toString();
                        if (listener.isValid(name)) {

                            FragmentManager fragmentManager = null;
                            AlertDialog alertDialog = ((AlertDialog) dialog);
                            RadioButton radioButton = (RadioButton) alertDialog.
                                    findViewById(((RadioGroup) alertDialog.findViewById(R.id.radioGroup)).
                                            getCheckedRadioButtonId());
                            if (radioButton.getText().equals("Variable")) {
                                if (listener.isDuplicateVariable(name)) {
                                    DialogDuplicateVariable duplicateVariable =
                                            new DialogDuplicateVariable();

                                    duplicateVariable.show(getFragmentManager(), "isduplicate");
                                    dialog.dismiss();
                                }
                            } else if (radioButton.getText().equals("Function")) {
                                if (listener.isDuplicateFunction(name)) {
                                    DialogDuplicateFunction duplicateFunction =
                                            new DialogDuplicateFunction();
                                    duplicateFunction.show(getFragmentManager(), "isDuplicate");
                                    dialog.dismiss();
                                }
                            } else {
                                listener.createNewFV(name);
                                dialog.dismiss();
                            }
                        }

                    }
                });

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
        if (context instanceof NameVariableDialog.NameDialogListener) {
            listener = (NameDialogListener) context;

        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement NameDialogListnerf");
        }
    }

    public interface NameDialogListener{
        public boolean isValid(String name);
        public boolean isDuplicateFunction(String name);
        public boolean isDuplicateVariable(String name);
        public void createNewFV(String name);
    }
}
