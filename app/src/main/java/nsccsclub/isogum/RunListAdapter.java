package nsccsclub.isogum;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Collin on 5/20/2016.
 */
public class RunListAdapter extends ArrayAdapter<String> {
    // View lookup cache
    private static class ViewHolder {
        String name;

    }

    public RunListAdapter(Context context, ArrayList<String> names) {
        super(context, R.layout.list_variable_picker, names);
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
            viewHolder.name = ((TextView)convertView.findViewById(R.id.variable_name)).toString();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        // Populate the data into the template view using the data object
//        viewHolder.name.setText(user.name);
//        viewHolder.home.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }

}
