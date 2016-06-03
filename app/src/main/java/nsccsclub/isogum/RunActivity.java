package nsccsclub.isogum;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface and ui for allowing the user to run functions with a selected group of variables,
 * and calculate the  total uncertainty
 */
public class RunActivity extends AppCompatActivity {
    public static final String LOG_CODE = "RunActivity";

    DBHandler dbHandler;
    Function function;
    List<String> variableList;
    String outputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Intent intent = getIntent();
        String name = null;
        function = null;
        dbHandler = new DBHandler(this.getApplicationContext());
        try {
            name = intent.getStringExtra(FunctionActivity.EXTRA_NAME);
            function = dbHandler.getFunction(dbHandler.findFunctionByName(name));
            outputName = intent.getStringExtra(FunctionActivity.EXTRA_OUTPUT_NAME);

        } catch (Exception e){
            Toast.makeText(this,"Error retrieving function.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        //set the title of the visual elements on the screen
        TextView title = (TextView) findViewById(R.id.function_value_title);
        title.setText(name);
        title = (TextView) findViewById(R.id.function_value);
        title.setText(function.getFunction());
        variableList = function.getVariableNames();
        Collections.sort(variableList);
        RunListAdapter adapter = new RunListAdapter(this,variableList, dbHandler.getAllVariables());
        final ListView listView = (ListView) this.findViewById(R.id.variable_selector);
        listView.setAdapter(adapter);
//        listView.setEmptyView(findViewById(R.id.emptyElement));


        getSupportActionBar().setTitle(R.string.function_run);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //hookup save and cancel buttons here

        Button run = (Button) this.findViewById(R.id.run_button);
        Button cancel = (Button) this.findViewById(R.id.cancel_button);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get all created spinners
                Map<String,String> spinnerMap =((RunListAdapter)listView.getAdapter()).getSpinnerMap();
                Map<String,Variable> variableMap = new HashMap<String, Variable>();
                Variable variable = null;
                long id = -10;
                for (String var : variableList){
                    id = dbHandler.findVariableByName(spinnerMap.get(var));
                    if (id != -1) {
                        variable = dbHandler.getVariable(id);
                        variableMap.put(var.toUpperCase(), variable);
                    }else{
                        Toast.makeText(v.getContext(),
                                getResources().getString(R.string.not_chosen_error),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Variable output = function.runFunction(outputName, variableMap);
                    if (dbHandler.isDuplicateVariable(output)){
                        dbHandler.updateVariable(output);
                    }else {
                        dbHandler.createVariable(variable);
                    }
                    displayOutput(output);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayOutput(Variable output) {

    }


}
