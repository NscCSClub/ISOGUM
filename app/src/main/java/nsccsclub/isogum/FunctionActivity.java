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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FunctionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FunctionExpandableListAdapter.FunctionListListener ,
        DialogOutputVariable.OutputVariableListener, AdapterView.OnItemSelectedListener ,
        DialogDuplicateVariable.DuplicateVariableListener, DialogNameFunctionVariable.NameFVDialogListener ,
        DialogDuplicateFunction.DialogDuplicateFunctionListener,
        DialogDuplicateVariableRun.DuplicateVariableListener{


    /**
     * the name of a created fucntion or variable.
     */
    public static final String EXTRA_NAME = "name";
    /**
     * Name of the output variable for running an activity
     */
    public static final String EXTRA_OUTPUT_NAME = "outputName";
    /**
     * The value of a variable or function for editing
     */
    public static final String EXTRA_VALUE = "value";
    /**
     * The uncertainty of a variable for editing
     */
    public static final String EXTRA_UNCERTAINTY = "uncertainty";

    /**
     * Expandable list view
     */
    ExpandableListView expandableListView;

    /**
     * adapter for populating the expandable list view
     */
    FunctionExpandableListAdapter adapter;

    /**
     * List of function objects that are stored in the database to be displayed in an expandable list
     */
    List<String> functionNameList;
    /**
     * The value and database id of a function for functions in an expandable list
     */
    HashMap<String,List<String>> functionValueMap;

    /**
     * The database handler for the application, must be called using getApplicationContext()
     */
    DBHandler dbHandler;

    /**
     * Name of a function for editing, used to make interface callbacks work with dialogs
     */
    private String functionName;
    /**
     * Name of funcition or variable to overwrite, used to make interface callbacks for dialogs work
     */
    private String duplicationVariable;


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
                /*
                Shows a dialog that prompts the user for the type of object they want to create,
                the name of the object, checks for duplicates, then calls the appropriate
                edit/create activity
                 */
                DialogNameFunctionVariable dialog = new DialogNameFunctionVariable();
                dialog.show(getSupportFragmentManager(),"Duplicate");
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

        //spinner for choosing how to sort the expandable list
        //at this point, by name and date added are the only two sorting options
        Spinner spinner = (Spinner)findViewById( R.id.sort_bar_picker);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sort_choices,R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        //this activity acts as the listener for this spinner
        spinner.setOnItemSelectedListener(this);

        //sets up the expandable list of function objects
        expandableListView = (ExpandableListView) this.findViewById(R.id.function_list);
        dbHandler  = new DBHandler(this.getApplicationContext());
        //todo delete this for debugging
        //sets up a reasonably sized list of objects for gui debugging
        debugAddFunctions();
        //data is sorted and set up here
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            /*makes it so only one item is expanded at a time, and keeps track of the last expanded
            * option for efficiency
            * */
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        //this is the text view that shows up when the list is empty
        expandableListView.setEmptyView(this.findViewById(R.id.emptyElement));
        //NOTE: all data for the expandable list view is sorted and prepared in the onResume method

        //these are used for interface callbacks for the dialogs only,
        // do not use in any other context
        functionName = "not set";
        duplicationVariable = "not set";
    }

    /**
     * Creates 20 variable objects and 20 function objects for gui debugging
     */
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

    /**
     * Sets up data for the functionNameList and functionValueMap for use in an expandable list
     * adapter. This method sorts data alphabetically by unique name.
     */
    private void prepareListDataName() {
        //get all functions
        List<Function> functionList = dbHandler.getAllFunctions();
        //sort by name
        Collections.sort(functionList);
        Function f = null;
        List<String> valueList = null;
        Iterator<Function> iterator = functionList.iterator();
        while (iterator.hasNext()){
            //iterate through, and
            //add data to appropriate structure
            f = iterator.next();
            functionNameList.add(f.getName());
            valueList = new ArrayList<>();
            valueList.add(f.getFunction());
            functionValueMap.put(f.getName(), valueList);
        }
    }

    /**
     * Sets up data for the functionNameList and functionValueMap for use in an expandable list
     * adapter. This method sorts data alphabetically by unique name.
     */
    private void prepareListDataDate() {
        //get all functions
        List<Function> functionList = dbHandler.getAllFunctions();
        //sort by date added
        Collections.sort(functionList,new FunctionIDSorter());
        Function f = null;
        List<String> valueList = null;
        Iterator<Function> iterator = functionList.iterator();
        while (iterator.hasNext()){
            //iterate through data and add to appropriate structure
            f = iterator.next();
            functionNameList.add(f.getName());
            valueList = new ArrayList<>();
            valueList.add(f.getFunction());
            functionValueMap.put(f.getName(), valueList);
        }
    }



    @Override
    public void onBackPressed() {
        // close the activity drawer if its open, if not finish this activity
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finish();
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
            //we are in the function activity no need to go anywhere
        } else if (id == R.id.nav_variable) {
            Intent intent = new Intent(this, VariableActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(this,HelpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            //todo decide if we need a settings activity
            // not sure if we want to hook this up
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    /**
     * Listener for the row of three buttons on an expanded function object in the GUI.
     * Allows a user to run, edit, or delete a function
     */
    public void clickListener(String name, FunctionExpandableListAdapter.Action action) {
        functionName = name;
        if (action == FunctionExpandableListAdapter.Action.DELETE){
            dbHandler.deleteFunction(dbHandler.getFunction(dbHandler.findFunctionByName(name)));
            //data will be refreshed in the on resume method
        }
        Intent intent = null;
        if (action == FunctionExpandableListAdapter.Action.EDIT) {
            //start create function activity and pass in extra arguments for editing
            intent = new Intent(this,CreateFunctionActivity.class);
            intent.putExtra(EXTRA_NAME,name);
            intent.putExtra(EXTRA_VALUE,
                    dbHandler.getFunction(dbHandler.findFunctionByName(name)).getFunction());
            startActivity(intent);
        }
        if (action == FunctionExpandableListAdapter.Action.RUN){
            /*
            Creates a dialog to prompt the user for an appropriate output variable name, checks for
            duplicates and starts the run activity
             */
            DialogOutputVariable dialaog = new DialogOutputVariable();
            dialaog.show(getSupportFragmentManager(),"get_name");
        }
    }

    @Override
    public void onResume() {
        /*
        Main purpose here is to set up all of the data for the expandable list view, and refresh
        if neccesary.
         */
        //todo set up animation here
        super.onResume();
        functionNameList=new ArrayList<String>();
        functionValueMap=new HashMap<String, List<String>>();
        //checks spinner for soritng option and prepares the data
        Spinner spinner = (Spinner)findViewById(R.id.sort_bar_picker);
        if(spinner.getSelectedItem().toString().equals("Name")){
            this.prepareListDataName();
        }
        if(spinner.getSelectedItem().toString().equals("Date Added")){
            this.prepareListDataDate();
        }
        //feeds sorted data to the expandable list view
        adapter = new FunctionExpandableListAdapter(this, functionNameList,functionValueMap);
        expandableListView.setAdapter(adapter);
    }

    @Override
    /**
     * Checks the name of function or variable for validity. Displays a toast if the name is not
     * valid.
     * <div>
     *     <h3>Rules:</h3>
     *     <ul>
     *         <li>Names must be shorter than 40 characters</li>
     *         <li>Names must be unique</li>
     *         <li>Cannot name an object e or pi</li>
     *     </ul>
     * </div>
     */
    public boolean isNameValid(String name) {
        if (name.equals("")){
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
    /**
     * Wrapper method for an interface callback for name dialog. Checks the name of the gui object
     * for validity and displays a toast to the user if it is not valid.
     * <div>
     *     <h3>Rules:</h3>
     *     <ul>
     *         <li>Names must be shorter than 40 characters</li>
     *         <li>Names must be unique</li>
     *         <li>Cannot name an object e or pi</li>
     *     </ul>
     * </div>
     */
    public boolean isValid(String name) {
        return this.isNameValid(name);
    }

    @Override
    /**
     * Interface callback to check if a given function name has a duplicate entry in the database
     */
    public boolean isDuplicateFunction(String name) {
        if(dbHandler.isDuplicateFunction(new Function(name, ""))){
            duplicationVariable = name;
            return true;
        }
        return  false;
    }

    @Override
    /**
     * interface callback to determine if a variable name has a duplicate entry in the database
     */
    public boolean isDuplicateVariable(String name) {
        if(dbHandler.isDuplicateVariable(new Variable(name, 0,0))){
            duplicationVariable = name;
            return true;
        }
        return  false;
    }

    @Override
    /**
     * Interface callback to determine if a variable name has a duplicate entry in the database
     */
    public boolean isDuplicate(String name) {
        if(dbHandler.isDuplicateVariable(new Variable(name, 0,0))){
            duplicationVariable = name;
            return true;
        }
        return  false;
    }

    @Override
    /**
     * Calls the appropriate create activity for a function or variable, and passes in the name.
     */
    public void createNewFV(String name, DialogNameFunctionVariable.FV type) {
        Intent intent = null;
        if (type.equals(DialogNameFunctionVariable.FV.FUNCTION)){
            intent = new Intent(this, CreateFunctionActivity.class);
            intent.putExtra(EXTRA_NAME,name);
            startActivity(intent);
        }else if (type.equals(DialogNameFunctionVariable.FV.VARIABLE)){
            intent = new Intent(this, CreateVariableActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            startActivity(intent);
        }



    }

    @Override
    /**
     * Launches the run activity for a given function with the appropriate arguments.
     */
    public void launchRun(String outputName) {
        Intent intent = new Intent(this,RunActivity.class);
        intent.putExtra(EXTRA_NAME, functionName);
        intent.putExtra(EXTRA_OUTPUT_NAME, outputName);
        startActivity(intent);
    }

    @Override
    /**
     * Listener option for the expandable list item, refreshes the data for all list objects
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.onResume();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Utility method for a dialog interface callback for overwriting funcitons and variables.
     * @param s
     */
    public void setDuplicationVariable(String s){
        duplicationVariable = s;
    }

    @Override
    /**
     * Calls the create function activity to overwrite and edit an existing function with the
     * appropriate arguments.
     */
    public void overwriteFunction() {
        Intent intent = new Intent(this,CreateFunctionActivity.class);
        intent.putExtra(EXTRA_NAME,duplicationVariable);
        intent.putExtra(EXTRA_VALUE,
                dbHandler.getFunction(dbHandler.findFunctionByName(duplicationVariable)).getFunction());
        startActivity(intent);
    }

    @Override
    /**
     * Calls the create variable activity to overwrite and edit an existing variable with the
     * appropriate arguments.
     */
    public void overwriteVarible() {
        Intent intent = new Intent(this,CreateVariableActivity.class);
        intent.putExtra(EXTRA_NAME,duplicationVariable);
        intent.putExtra(EXTRA_VALUE,
                dbHandler.getVariable(dbHandler.findVariableByName(duplicationVariable)).getValue());
        intent.putExtra(EXTRA_UNCERTAINTY,
                dbHandler.getVariable(dbHandler.findVariableByName(duplicationVariable)).getUncertainty());
        startActivity(intent);

    }

    @Override
    /**
     * Launches the run activity, and has it set up to overwrite output data into an existing variable
     */
    public void overwriteVaribleRun() {
        this.launchRun(duplicationVariable);
    }
}
