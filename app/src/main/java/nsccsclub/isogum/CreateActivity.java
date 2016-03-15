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
public class CreateActivity extends AppCompatActivity {

    /**
     * The type of uncertainty object we are creating
     */
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //get the intent that invokes this activity
        Intent intent= getIntent();
        type = intent.getStringExtra(FunctionActivity.EXTRA_TYPE);


    }
}
