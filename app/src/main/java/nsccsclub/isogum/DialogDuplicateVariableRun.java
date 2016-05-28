package nsccsclub.isogum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Collin on 5/27/2016.
 */
public class DialogDuplicateVariableRun extends DialogFragment{

    private DuplicateVariableListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.duplicate_variable_message);
        builder.setPositiveButton(R.string.save,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.overwriteVaribleRun();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogDuplicateVariableRun.this.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (DuplicateVariableListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException("activity does not implement appropriate nested interface");
        }
    }

    public interface DuplicateVariableListener{
        public void overwriteVaribleRun();
    }
}
