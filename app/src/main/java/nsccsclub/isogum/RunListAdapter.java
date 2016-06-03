package nsccsclub.isogum;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Collin on 5/20/2016.
 */
public class RunListAdapter extends ArrayAdapter<String> {
    ArrayList<String> variableNames;
    List<String> uniqueNames;
    HashMap<String,String> spinnerMap;

    // View lookup cache
    private static class ViewHolder {
        String name;
        Spinner spinner;

    }

    public RunListAdapter(Context context, List<String> names, List<Variable> variables) {
        super(context, R.layout.list_variable_picker, names);
        uniqueNames =names;
        variableNames = new ArrayList<String>();
        variableNames.add(context.getResources().getString(R.string.select));
        for(Variable v:variables){
            variableNames.add(v.getName());
        }
        spinnerMap = new HashMap<>();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String name = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_variable_picker,
                    parent, false);
            final TextView textView= (TextView)convertView.findViewById(R.id.variable_name);
            textView.setText(name);
            final Spinner spinner = (Spinner) convertView.findViewById(R.id.variable_spinner);

            ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(spinner.getContext(),
                    R.layout.spinner_variable_name, variableNames);
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_variable_name);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = ((Spinner) parent).getSelectedItem().toString();
                    spinnerMap.put(textView.getText().toString(), item);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            viewHolder.name = (textView).getText().toString();
            viewHolder.spinner = spinner;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            TextView textView= (TextView)convertView.findViewById(R.id.variable_name);
            textView.setText(name);
            viewHolder.name = (textView).getText().toString();
            convertView.setTag(viewHolder);
        }
//
        // Return the completed view to render on screen
        return convertView;
    }

    public HashMap<String,String> getSpinnerMap(){
        return spinnerMap;
    }



}
