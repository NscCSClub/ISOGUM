package nsccsclub.isogum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * The creation screen for an uncertainty object, currently being implemented
 * <p/>
 * <div>
 * <h4>Items currently supported<h4/>
 * <ul>
 * <li>Create new function<li/>
 * <li>Create new variable<li/>
 * <ul/>
 * <div/>
 */
public class CreateFunctionActivity extends AppCompatActivity {

    //class constants used for storing and retrieving temporary data
    static final String TEMP_NAME = "function_name";
    static final String TEMP_FUNCTION = "function_function";

    private InputKeyboard inputKeyboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_function);
        inputKeyboard = new InputKeyboard(this,R.id.keyboardview_function,R.layout.keyboard_layout);
        inputKeyboard.registerEditText(R.id.create_function_name_bar);
        inputKeyboard.registerEditText(R.id.create_function_value_bar);

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        ((EditText)findViewById(R.id.create_function_name_bar)).setText(
                savedInstanceState.getString(TEMP_NAME));
        ((EditText)findViewById(R.id.create_function_value_bar)).setText(
                savedInstanceState.getString(TEMP_FUNCTION));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current state of all text fields
        savedInstanceState.putString(TEMP_NAME,
                ((EditText)findViewById(R.id.create_function_name_bar)).getText().toString());
        savedInstanceState.putString(TEMP_FUNCTION,
                ((EditText) findViewById(R.id.create_function_value_bar)).getText().toString());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed(){
        // closes the keyboard if it is visible, or it closes the activity
        if (inputKeyboard.isKeyboardVisible()){
            inputKeyboard.hideInputKeyboard();
        }
        else {
            this.finish();
        }
    }

}
