package nsccsclub.isogum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * Dialog that is an entry point to the create screen prompts if we want to create a function or
 * variable. Gets the name of the thing we want to save, and checks it for validity, then also
 * handles database conflicts and duplicates.
 * Created by Rohan on 5/13/16.
 */
public class DialogNameFunctionVariable extends DialogFragment {
    private NameFVDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //inflate the layout
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.dialog_name_f_v_layout,null))
                //the listeners are set to null here, in order to avoid having them auto dismiss
                //see the setOnShowListener, that is where they are hooked up.
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(R.string.cancel, null);
        Dialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                final AlertDialog fvDialog = ((AlertDialog) dialog);
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //get the active radio buton text
                        RadioGroup radioGroup = (RadioGroup) fvDialog.findViewById(R.id.radioGroup);
                        RadioButton radioButton = ((RadioButton)((AlertDialog) dialog).
                                findViewById(radioGroup.getCheckedRadioButtonId()));
                        EditText editText = (EditText) fvDialog.findViewById(R.id.enterText);
                        String name = editText.getText().toString();
                        if(listener.isValid(name)) {
                            //checks to see if the name is valid
                            if (radioButton.getText().toString().equals(
                                    getString(R.string.function_radio_name))) {
                                //check to see if we are dealing with a duplicate function or variable
                                if(listener.isDuplicateFunction(name)){
                                    // we have a duplicate function launch a dialog to see what the
                                    // user wants to do
                                    DialogDuplicateFunction duplicateVariable =
                                            new DialogDuplicateFunction();
                                    duplicateVariable.show(getFragmentManager(),"isduplicate");
                                    dialog.dismiss();
                                }
                                else {
                                    //no duplicates go ahead and create the function
                                    listener.createNewFV(name, FV.FUNCTION);
                                    dialog.dismiss();
                                }
                            }
                            else if (radioButton.getText().toString().equals(
                                    getString(R.string.variable_radio_name))) {
                                if(listener.isDuplicateVariable(name)){
                                    // we have a duplicate variable go ahead and prompt the user for
                                    // next course of action
                                    DialogDuplicateVariable duplicateVariable =
                                            new DialogDuplicateVariable();
                                    duplicateVariable.show(getFragmentManager(),"isduplicate");
                                    dialog.dismiss();
                                }
                                else{
                                    // no duplicates here, go ahead and make a new variable
                                    listener.createNewFV(name, FV.VARIABLE);
                                    fvDialog.dismiss();
                                }
                            }

                        }
                    }
                });
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
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
    public void onAttach(Context context) {
        super.onAttach(context);
        // makes sure the host activity implements the appropriate interface.
        if (context instanceof NameFVDialogListener) {
            listener = (NameFVDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NameDialogListnerf");
        }
    }

    public interface NameFVDialogListener {
        /**
         * Checkes the given object name for validity.
         * @param name The name of the function or variable to check
         * @return True for valid, false for invalid.
         */
        public boolean isValid(String name) ;

        /**
         * Checks to see if a given function name has any database conflicts
         * @param name The name of the desired function.
         * @return True if name is a duplicate, false if unique.
         */
        public boolean isDuplicateFunction(String name);

        /**
         * Checks if a given variable name has any database conflicts.
         * @param name The name of the desired variable.
         * @return True if duplicate, false if unique.
         */
        public boolean isDuplicateVariable(String name);

        /**
         * Create a new function or variable objects, under a given name.
         * @param name The unique name of the object we are creating
         * @param type The type of object we are creating
         */
        public void createNewFV (String name,DialogNameFunctionVariable.FV type);

    }

    /**
     * Enum for distinguishing between functions and variables in interface callbacks.
     */
    public enum FV{
        FUNCTION,VARIABLE;
    }
}