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

    OutputVariableListener listener;
    String functionName;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //these are the buttons for the dialog
        //the listners are hooked up later to fix a dismissal bug
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
                                functionName = ((EditText)((AlertDialog) dialog).
                                        findViewById(R.id.varaible_name_field)).getText().toString();
                                if(listener.isNameValid(functionName)){
                                    if(listener.isDuplicate(functionName)){
                                        DialogDuplicateVariableRun duplicateVariable =
                                                new DialogDuplicateVariableRun();
                                        duplicateVariable.show(getFragmentManager(),"isduplicate");
                                        dialog.dismiss();
                                    }
                                    else {
                                        listener.launchRun(functionName);
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
        try {
            listener = (OutputVariableListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException("Parent Activity does not implement interface." +
                    context.toString());
        }
    }

    public interface OutputVariableListener{
        public boolean isNameValid(String name);
        public boolean isDuplicate(String name);
        public void launchRun(String namevariable);
    }
}
