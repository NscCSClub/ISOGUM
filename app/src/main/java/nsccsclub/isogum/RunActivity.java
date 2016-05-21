package nsccsclub.isogum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class RunActivity extends AppCompatActivity {
    DBHandler dbHandler;
    Function function;
    List<String> variableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Intent intent = getIntent();
        String name = null;
        try {
            name = intent.getStringExtra(FunctionActivity.EXTRA_NAME);
        } catch (Exception e){
            Toast.makeText(this,"Error retrieving function.", Toast.LENGTH_SHORT);
            this.finish();
        }
        dbHandler = new DBHandler(this.getApplicationContext());
        function = dbHandler.getFunction(dbHandler.findFunctionByName(name));
        variableList = function.getVariableNames();
    }
}
