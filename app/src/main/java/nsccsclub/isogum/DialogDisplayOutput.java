package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Dialog to display output from the run activity
 * Created by csconway on 6/3/2016.
 */
public class DialogDisplayOutput extends DialogFragment{
    DialogDisplayOutputListener listener;
    private TextView name;
    private TextView value;
    private TextView uncertainty;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_display_output_variable,null);
        name = (TextView) view.findViewById(R.id.dialog_display_output_variable_name_field);
        value = (TextView) view.findViewById(R.id.dialog_display_output_variable_value_field);
        uncertainty = (TextView) view.findViewById(R.id.dialog_display_output_variable_uncertainty_field);
        displayText();
        builder.setView(inflater.inflate(R.layout.dialog_display_output_variable,null))
                .setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.done();
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (DialogDisplayOutputListener) activity;
        }
        catch (Exception e){
            throw new ClassCastException(activity.toString()+" does not implement " +
                    "the appropriate interface");
        }

    }

    public void displayText(){
        Variable v = listener.getOutVar();
        name.setText(v.getName());
        value.setText(String.valueOf(v.getValue()));
        uncertainty.setText(String.valueOf(v.getUncertainty()));

    }


    public interface DialogDisplayOutputListener{
        public void done();
        public Variable getOutVar();
    }
}
