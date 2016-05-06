package nsccsclub.isogum;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

/**
 * The creation screen for an uncertainty object, currently being implemented. Contains a function
 * fragment and a dialog to support naming custom variables, some lifecycle callback methods still
 * need to be implemented
 */
public class CreateFunctionActivity extends AppCompatActivity implements
        CreateFunctionFragment.OnFragmentInteractionListener,NameVariableDialog.NameDialogListener{

    //class constants used for storing and retrieving temporary data
    static final String TEMP_NAME = "function_name";
    static final String TEMP_FUNCTION = "function_function";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_function);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        //todo implement on restore instance state

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //todo implement on save instance state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }


    @Override
    /**
     * Creates a name varible dialog
     */
    public void nameVariable() {
        NameVariableDialog dialog = new NameVariableDialog();
        dialog.show(getSupportFragmentManager(),"name");
    }

    @Override
    /**
     * checks a variable name for valididty and adds it to the edit text window
     */
    public void onPositiveClick(DialogFragment dialog) {
        EditText editText = (EditText)dialog.getDialog().findViewById(R.id.varaible_name_field);
        String content = editText.getText().toString();
        if (content.compareTo("e")==0 || content.compareTo("pi")==0){
            //e and pi are not valid variable names
            Toast.makeText(getApplicationContext(),"Cannot name e or pi",Toast.LENGTH_SHORT).show();
        }
        else{
            //get back to the function fragment
            CreateFunctionFragment createFunctionFragment =
                    (CreateFunctionFragment)getSupportFragmentManager().
                            findFragmentById(R.id.function_fragment);
            //add variable to the edit text window.
            createFunctionFragment.addVar(content);
        }
    }
}
