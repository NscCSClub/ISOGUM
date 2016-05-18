package nsccsclub.isogum;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class CreateVariableActivity extends AppCompatActivity implements
        CreateVariableFragment.OnVariableFragmentInteractionListener{

    /**
     * The log code for useing the log class and logcat
     */
    public final String LOG_CODE = "CreateVariableActivity";
    /**
     * The name of the variable that we are creating
     */
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_variable);

        //setting the name in the header bar
        Intent intent = getIntent();
        name = intent.getStringExtra(FunctionActivity.EXTRA_NAME);
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
    public void onBackPressed(){ this.finish(); }

    /**
     * checks the variable for valididty, displays an error message if not valid, if valid it
     * stores it in the database, then returns the activity to th last screen.
     *
     * If uncertainty is left blank it is set to zero
     *
     * @param value the edit text field containing the current value.
     * @param uncertainty The edit text field containging the current uncertainty.
     */
    @Override
    public void saveVariable(String value, String uncertainty) {
        //make sure there is a valid data here
        double v=0, u=0;
        try{
            v = Double.parseDouble(value);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    "The value is not a valid number.", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            u = Double.parseDouble(uncertainty);
        }catch (Exception e){
            if(uncertainty.compareTo("")!=0){
                Toast.makeText(getApplicationContext(),
                    "The uncertainty is not a valid number", Toast.LENGTH_SHORT).show();
                return;
            }else{
                u = 0;
            }
        }
        //check for duplicates
        DBHandler dbHandler = new DBHandler(this.getApplicationContext());

        Variable variable = new Variable(name, v, u);
        if(dbHandler.isDuplicateVariable(variable)){
            variable.setId(dbHandler.findVariableByName(variable.getName()));
            dbHandler.updateVariable(variable);
        }
        else{
            dbHandler.createVariable(variable);
        }
        this.finish();
    }

    /**
     * cancel variable, returns to the last activity.
     */
    @Override
    public void cancelVariable() {
        this.finish();
    }
}
