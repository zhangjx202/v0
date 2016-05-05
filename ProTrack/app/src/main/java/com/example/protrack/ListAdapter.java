package com.example.protrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 4/27/2016.
 */
public class ListAdapter extends BaseAdapter {

    private final Context context;
    private final List<Task> items = new ArrayList<Task>();

    public ListAdapter(Context context){
        this.context = context;
    }

    public void add(Task toAdd) {

        for(Task Task : items){
            if(Task.place.equals(toAdd.place)){
                Toast.makeText(context, "You already have this location Task!", Toast.LENGTH_LONG).show();
                return;
            }
        }

        items.add(toAdd);
        notifyDataSetChanged();

    }

    // Clears the list adapter of all items.

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param pos Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param pos The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int pos) {
        return pos;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task Task = items.get(position);

        /*LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout itemLayout = (RelativeLayout)mInflater.inflate(R.layout.Task, parent, false);

        final TextView place = (TextView)itemLayout.findViewById(R.id.place);
        place.setText("Place: " + Task.place);

        final TextView country = (TextView)itemLayout.findViewById(R.id.country);
        country.setText("Country: " + Task.country);

        final ImageView flag = (ImageView)itemLayout.findViewById(R.id.image);
        flag.setImageBitmap(Task.flag);

        return itemLayout;*/
        return null;
    }
}
