package nsccsclub.isogum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateVariableActivity extends AppCompatActivity {

    private InputKeyboard inputKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_variable);
        inputKeyboard = new InputKeyboard(this,R.id.keyboardview_variable,R.layout.keyboard_layout);
        inputKeyboard.registerEditText(R.id.create_variable_name_bar);
        inputKeyboard.registerEditText(R.id.create_variable_value_bar);
        inputKeyboard.registerEditText(R.id.create_variable_uncertainty_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
