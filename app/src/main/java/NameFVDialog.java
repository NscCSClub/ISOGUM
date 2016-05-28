import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import nsccsclub.isogum.NameVariableDialog;
import nsccsclub.isogum.R;


/**
 * Created by Rohan on 5/13/16.
 */
public class NameFVDialog extends DialogFragment {
    private NameFVDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //inflate the layout
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.dialog_name_f_v_layout,null))
                //se the listeners
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.createFV(NameFVDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NameFVDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
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
        public void createFV (NameFVDialog dialog);

    }
}