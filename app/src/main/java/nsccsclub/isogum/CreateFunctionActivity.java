package nsccsclub.isogum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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


    private InputKeyboard inputKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_function);
        inputKeyboard = new InputKeyboard(this,R.id.keyboardview_function,R.layout.keyboard_layout);
        inputKeyboard.registerEditText(R.id.create_function_name_bar);
        inputKeyboard.registerEditText(R.id.create_function_value_bar);
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
