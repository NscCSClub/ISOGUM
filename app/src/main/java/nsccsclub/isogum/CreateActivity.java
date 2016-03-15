package nsccsclub.isogum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        //TODO delete for debugging
        TextView textView =  new TextView(this);
        textView.setText(type);
        textView.setTextSize(40);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.debugging_content);
        layout.addView(textView);

    }
}
