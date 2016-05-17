package nsccsclub.isogum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateVariableActivity extends AppCompatActivity implements
        CreateVariableFragment.OnVariableFragmentInteractionListener{

    /**
     * The log code for useing the log class and logcat
     */
    public final String LOG_CODE = "CreateVariableActivity";
    /**
     * The name of the variable that we are creating
     */
    public String name;

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
        //todo implement on restore instance state

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //todo implement on save instance state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed(){ this.finish(); }

    /**
     * checks the variable for valididty, displays an error message if not valid, if valid it
     * stores it in the database, then returns the activity to th last screen
     *
     * @param editText
     */
    @Override
    public void saveVariable(EditText editText) {
        //todo implment this
    }

    /**
     * cancel variable, returns to the last activity.
     */
    @Override
    public void cancelVariable() {
        //todo implement this
    }
}
