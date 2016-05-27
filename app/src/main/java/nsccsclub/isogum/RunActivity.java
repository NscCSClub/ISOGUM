package nsccsclub.isogum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
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
        function = null;
        dbHandler = new DBHandler(this.getApplicationContext());
        try {
            name = intent.getStringExtra(FunctionActivity.EXTRA_NAME);
            function = dbHandler.getFunction(dbHandler.findFunctionByName(name));
        } catch (Exception e){
            Toast.makeText(this,"Error retrieving function.", Toast.LENGTH_SHORT).show();
            this.finish();
        }


        variableList = function.getVariableNames();
        Collections.sort(variableList);
        RunListAdapter adapter = new RunListAdapter(this,variableList, dbHandler.getAllVariables());
        ListView listView = (ListView) this.findViewById(R.id.variable_selector);
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
                //todo hook up run and save algortihm
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
