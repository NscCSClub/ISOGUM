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
 * Fragment to pick the name of the output of the run screen, prompts user if desired variable name
 * will override and existing database resource.
 * Created by csconway on 5/26/2016.
 */
public class DialogOutputVariable extends DialogFragment {

    /**
     * host activity must, it must implement the interface.
     */
    OutputVariableListener listener;
    /**
     * The name of the output variable.
     */
    String outputVariableName;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //these are the buttons for the dialog
        //listeners are set to null here then set up later, so the dialog does not automatically
        //dismiss on button click
        builder.setView(inflater.inflate(R.layout.dialog_output_variable, null)).
                setPositiveButton(R.string.save, null).
                setNegativeButton(R.string.cancel,null);
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                outputVariableName = ((EditText)((AlertDialog) dialog).
                                        findViewById(R.id.varaible_name_field)).getText().toString();
                                //checks to see if the output varaible name is valid
                                if(listener.isNameValid(outputVariableName)){
                                    // checks if its a duplicate
                                    if(listener.isDuplicate(outputVariableName)){
                                        //if its a duplicate prompt user if they still want to use
                                        //that name and save over the variable
                                        DialogDuplicateVariableRun duplicateVariable =
                                                new DialogDuplicateVariableRun();
                                        duplicateVariable.show(getFragmentManager(),"isduplicate");
                                        dialog.dismiss();
                                    }
                                    else {
                                        //its not a duplicate launch run screen
                                        listener.launchRun(outputVariableName);
                                        dialog.dismiss();
                                    }
                                }
                            }
                        }
                );
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
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

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //makes sure that host activity implements the interface
        try {
            listener = (OutputVariableListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException("Parent Activity does not implement interface." +
                    context.toString());
        }
    }

    public interface OutputVariableListener{
        /**
         * Checks output varible name for validity.
         * <div>
         *     <h3>Rules:</h3>
         *     <ul>
         *         <li>Names must be shorter than 40 characters</li>
         *         <li>Names must be unique</li>
         *         <li>Cannot name an object e or pi</li>
         *     </ul>
         * </div>
         * @param name The name of the output variable
         * @return true for valid, false for invalid.
         */
        public boolean isNameValid(String name);

        /**
         * Checks to see if the desired output variable name has any database conflicts
         * @param name The name of the output variable.
         * @return True for duplicate, false for unique.
         */
        public boolean isDuplicate(String name);

        /**
         * Launches the run activity with a given output variable name.
         * @param namevariable The name of the variable to save data into.
         */
        public void launchRun(String namevariable);
    }
}
