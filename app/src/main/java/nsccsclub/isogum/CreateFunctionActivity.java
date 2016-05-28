package nsccsclub.isogum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The creation screen for an uncertainty object, currently being implemented. Contains a function
 * fragment and a dialog to support naming custom variables, some lifecycle callback methods still
 * need to be implemented
 */
public class CreateFunctionActivity extends AppCompatActivity implements
        FragmentCreateFunction.OnFunctionFragmentInteractionListener, DialogNameTextVariable.NameDialogListener {

    /**
     * The log code for useing the log class and logcat
     */
    public final String LOG_CODE = "CreateFunctionActivity";

    /**
     * The name of the function that we are creating
     */
    private String name;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_function);

        //setting the name of the function and header bar
        Intent intent = getIntent();
        name = intent.getStringExtra(FunctionActivity.EXTRA_NAME);
        try{
            value = intent.getStringExtra(FunctionActivity.EXTRA_VALUE);
            FragmentCreateFunction fragment = (FragmentCreateFunction) getSupportFragmentManager().
                    findFragmentById(R.id.function_fragment);

            fragment.setText(value);

        }catch (Exception e){

        }

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
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
        DialogNameTextVariable dialog = new DialogNameTextVariable();
        dialog.show(getSupportFragmentManager(), "name");
    }

    /**
     * checks the function for valididty, displays an error message if not valid, if valid it
     * stores it in the database, then returns the activity to th last screen
     */
    @Override
    public void saveFunction(EditText editText) {
        FunctionParser functionParser = new FunctionParser();
        DBHandler dbHandler = new DBHandler(this.getApplicationContext());
        //checks if valid data
        functionParser.setFunction(editText.getText().toString());
        if (functionParser.isValid()){
            //if valid

            Function function = new Function(name,functionParser.getFunction());
            if(dbHandler.isDuplicateFunction(function)){
                //we are updating and existing variable
                //find the id and store it
                function.setId(dbHandler.findFunctionByName(function.getName()));
                dbHandler.updateFunction(function);
                this.finish();
            }
            else {
                //we are creating a new function we are good to go
                dbHandler.createFunction(function);
                this.finish();
            }
        }
        else {
            //something is wrong with the user defined function show popup
            Toast.makeText(getApplicationContext(),"Invalid Function",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * cancel function, returns to the last activity.
     */
    @Override
    public void cancelFunction() {
        this.finish();

    }


    @Override
    public boolean isTextValid(String name) {
        if (name.equals("")){
            Toast.makeText(this, "Please enter a name.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.length()>40){
            Toast.makeText(this, "Names must be shorter than 40 characters.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.equals("e")||name.equals("pi")){
            Toast.makeText(this, "Cannot name a variable e or pi.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    //todo check this
    public void onPositiveClick(String name) {

        //get back to the function fragment
        FragmentCreateFunction fragmentCreateFunction =
            (FragmentCreateFunction)getSupportFragmentManager().
                findFragmentById(R.id.function_fragment);
            //add variable to the edit text windo
        fragmentCreateFunction.addVar(name);

    }
}
