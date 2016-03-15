package nsccsclub.isogum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

/**
 * The home screen for the ISO GUM Uncertainty calculator. It contains a list of all previously
 * defined user functions as well as a floating create button. This button links to the create
 * activity and prompts the user to create a function or variable uncertainty object, then
 *
 */
public class FunctionActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //designates the type of item to create in create activity
    public final static String EXTRA_TYPE= "com.nsccsclub.isogum.TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup( findViewById(R.id.fab));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_function, menu);
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

    /**
     * Creates a popup menu for the create screen, and prepares the xml code. Anchored to the create
     * button.
     * <div>
     *     <h4>Items currently supported<h4/>
     *     <ul>
     *         <li>Create new function<li/>
     *         <li>Create new variable</li>
     *         <ul/>
     *     <div/>
     * @param v The view containing the create button.
     */
    public void showPopup(View v) {
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
        Intent intent = new Intent(this,CreateActivity.class);

        //starts create activity with proper message
        switch(item.getItemId()) {
            case R.id.new_function:
                intent.putExtra(EXTRA_TYPE,"FUNCTION");
                startActivity(intent);
                return true;
            case R.id.new_variable:
                intent.putExtra(EXTRA_TYPE,"VARIABLE");
                startActivity(intent);
                return true;
            default:
                //unrecognizable input try again
                return false;
        }
    }



}