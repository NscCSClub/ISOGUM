package nsccsclub.isogum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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
            Toast.makeText(this,"Error retrieving function.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        dbHandler = new DBHandler(this.getApplicationContext());
        function = dbHandler.getFunction(dbHandler.findFunctionByName(name));
        variableList = function.getVariableNames();
        RunListAdapter adapter = new RunListAdapter(this,variableList, dbHandler.getAllVariables());
        ListView listView = (ListView) this.findViewById(R.id.variable_selector);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.emptyElement));


        //hookup save and cancel buttons here
    }
}
