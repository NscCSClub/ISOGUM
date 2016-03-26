package nsccsclub.isogum;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
public class CreateActivity extends AppCompatActivity {

    /**
     * The type of uncertainty object we are creating
     */
    private String type;

    private InputKeyboard inputKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //get the intent that invokes this activity
        Intent intent= getIntent();
        type = intent.getStringExtra(FunctionActivity.EXTRA_TYPE);

        inputKeyboard = new InputKeyboard(this,R.id.keyboardview,R.layout.keyboard_layout);
        inputKeyboard.registerEditText(R.id.create_value_bar);




        //TODO delete this
        //tests linkages between button and the edit text window
        //TODO check in tutorial
//        Button testLink = (Button) findViewById(R.id.test_button);
        //TODO look up proper listener
//        testLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


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
