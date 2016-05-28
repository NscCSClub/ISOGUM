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
                //se the listeners
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
                        RadioGroup radioGroup = (RadioGroup) fvDialog.findViewById(R.id.radioGroup);

                        RadioButton radioButton = ((RadioButton)((AlertDialog) dialog).
                                findViewById(radioGroup.getCheckedRadioButtonId()));
                        EditText editText = (EditText) fvDialog.findViewById(R.id.enterText);
                        String name = editText.getText().toString();
                        if(listener.isValid(name)) {
                            if (radioButton.getText().toString().equals(
                                    getString(R.string.function_radio_name))) {
                                if(listener.isDuplicateFunction(name)){
                                    DialogDuplicateFunction duplicateVariable =
                                            new DialogDuplicateFunction();
                                    duplicateVariable.show(getFragmentManager(),"isduplicate");
                                    dialog.dismiss();


                                }
                                else {
                                    listener.createNewFV(name, FV.FUNCTION);
                                    dialog.dismiss();
                                }
                            }
                            else if (radioButton.getText().toString().equals(
                                    getString(R.string.variable_radio_name))) {
                                if(listener.isDuplicateVariable(name)){
                                    DialogDuplicateVariable duplicateVariable =
                                            new DialogDuplicateVariable();
                                    duplicateVariable.show(getFragmentManager(),"isduplicate");
                                    dialog.dismiss();

                                }
                                else{
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
        if (context instanceof NameFVDialogListener) {
            listener = (NameFVDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NameDialogListnerf");
        }
    }

    public interface NameFVDialogListener {
        public boolean isValid(String name) ;
        public boolean isDuplicateFunction(String name);
        public boolean isDuplicateVariable(String name);
        public void createNewFV (String name,DialogNameFunctionVariable.FV type);

    }
    public enum FV{
        FUNCTION,VARIABLE;
    }
}