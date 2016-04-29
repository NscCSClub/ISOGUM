package nsccsclub.isogum;

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
 * Created by csconway on 4/28/2016.
 */
public class NameVariableDialog extends android.support.v4.app.DialogFragment {
    public NameDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        builder.setView(layoutInflater.inflate(R.layout.dialog_name_variable_layout,null))
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NameDialogListener) {
            listener = (NameDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NameDialogListnerf");
        }
    }

    public interface NameDialogListener{
        public void onPositiveClick(DialogFragment dialog);
    }
}
