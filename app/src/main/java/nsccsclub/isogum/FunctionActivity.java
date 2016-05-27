package nsccsclub.isogum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FunctionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PopupMenu.OnMenuItemClickListener, FunctionExpandableListAdapter.FunctionListListener ,
        OutputVariableDialaog.OutputVariableListener{


    /**
     * the name of a created fucntion or variable.
     */
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_OUTPUT_NAME = "outputName";
    public static final String EXTRA_VALUE = "value";


    /**
     * Expandable list view
     */
    ExpandableListView expandableListView;

    /**
     * adapter for populating the expandable list view
     */
    FunctionExpandableListAdapter adapter;

    /**
     * List of function objects that are stored in the database to be displayed on the screen
     */
    List<String> functionNameList;
    HashMap<String,List<String>> functionValueMap;

    DBHandler dbHandler;

    private String functionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
       
        
        //sets up a toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //sets up a floating action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(findViewById(R.id.fab));
            }
        });
        //sets up an activity drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //sets up the expandable list
        expandableListView = (ExpandableListView) this.findViewById(R.id.function_list);
        functionNameList=new ArrayList<String>();
        functionValueMap=new HashMap<String, List<String>>();
        dbHandler  = new DBHandler(this.getApplicationContext());

        //todo delete this for debuggin

        debugAddFunctions();



        //set up data here
        prepareListData();
        adapter = new FunctionExpandableListAdapter(this, functionNameList,functionValueMap);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        expandableListView.setEmptyView(this.findViewById(R.id.emptyElement));

        functionName = "not set";
    }

    private void debugAddFunctions() {
        ArrayList<Function> tempFuncArray = new ArrayList<Function>();
        ArrayList<Variable> tempVarArray = new ArrayList<Variable>();
        Variable v = null;
        Function f = null;

        for (int i = 0; i < 20; i++){
            f = new Function("function" + i, "[x]^2+[y]*6+3*[z]+[a]+[b]+[c]");
            if (!dbHandler.isDuplicateFunction(f)){
                dbHandler.createFunction(f);
            }
        }
        for (int i = 0; i < 20; i++){
            v = new Variable("variable" + i , (double)i, (double)i);
            if (!dbHandler.isDuplicateVariable(v)){
                dbHandler.createVariable(v);
            }
        }
    }

    private void prepareListData() {
        List<Function> functionList = dbHandler.getAllFunctions();
        Function f = null;
        List<String> valueList = null;
        Iterator<Function> iterator = functionList.iterator();
        while (iterator.hasNext()){
            f = iterator.next();
            functionNameList.add(f.getName());
            valueList = new ArrayList<>();
            valueList.add(f.getFunction());
            functionValueMap.put(f.getName(), valueList);
        }
    }

    private void showPopup(View v) {
        //creates the popup object
        PopupMenu popup = new PopupMenu(this,v);
        //create an inflater to reference defined xml menu resource
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_create_popup, popup.getMenu());

        //registers an action listener
        popup.setOnMenuItemClickListener(this);
        //makes visible in activity
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        /*
        gets the menu option selected and passes in to the create activity
         */

        //creates an intent to start create activity
        Intent intent;

        //starts create activity with proper message
        switch(item.getItemId()) {
            case R.id.new_function:
                intent = new Intent(this,CreateFunctionActivity.class);
                intent.putExtra(EXTRA_NAME,"INSERT_NAME_HERE");
                startActivity(intent);
                return true;
            case R.id.new_variable:
                intent = new Intent(this,CreateVariableActivity.class);
                intent.putExtra(EXTRA_NAME,"INSERT_V_NAME_HERE");
                startActivity(intent);
                return true;
            default:
                //unrecognizable input try again
                return false;
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.functions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_function) {

        } else if (id == R.id.nav_variable) {
            Intent intent = new Intent(this, VariableActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(this,HelpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickListener(String name, FunctionExpandableListAdapter.Action action) {
        functionName = name;
        if (action == FunctionExpandableListAdapter.Action.DELETE){
            dbHandler.deleteFunction(dbHandler.getFunction(dbHandler.findFunctionByName(name)));
        }
        Intent intent = null;
        if (action == FunctionExpandableListAdapter.Action.EDIT){
            intent = new Intent(this,CreateFunctionActivity.class);
            intent.putExtra(EXTRA_NAME,"INSERT_NAME_HERE");
            intent.putExtra(EXTRA_VALUE,
                    dbHandler.getFunction(dbHandler.findFunctionByName(name)).getFunction());
            startActivity(intent);
        }
        if (action == FunctionExpandableListAdapter.Action.RUN){
            //hook up run activity

            OutputVariableDialaog dialaog = new OutputVariableDialaog();
            dialaog.show(getSupportFragmentManager(),"get_name");

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        functionNameList=new ArrayList<String>();
        functionValueMap=new HashMap<String, List<String>>();
        //set up data here
        prepareListData();
        adapter = new FunctionExpandableListAdapter(this, functionNameList,functionValueMap);
        expandableListView.setAdapter(adapter);

    }

    @Override
    public boolean isNameValid(String name) {
        if (name==""){
            Toast.makeText(this, "Please enter a name.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.length()>40){
            Toast.makeText(this, "Names must be shorter than 40 characters.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.equals("e")||name.equals("pi")){
            Toast.makeText(this, "Cannot name a variable e or pi.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean isDuplicate(String name) {
        return dbHandler.isDuplicateVariable(new Variable(name, 0,0));
    }

    @Override
    public void launchRun(String outputName) {
        Intent intent = new Intent(this,RunActivity.class);
        intent.putExtra(EXTRA_NAME, functionName);
        intent.putExtra(EXTRA_OUTPUT_NAME, outputName);
        startActivity(intent);
    }
}
