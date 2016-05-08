package com.example.protrack;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 4/28/2016.
 */
public class ClosedListAdapter extends BaseAdapter {

        private final Context context;
        private final List<Task> items = new ArrayList<Task>();

        public ClosedListAdapter(Context context){
            this.context = context;
        }

        public void add(Task toAdd) {

            items.add(toAdd);
            notifyDataSetChanged();

        }

        // Clears the list adapter of all items.

        public void clear() {
            items.clear();
            notifyDataSetChanged();
        }


    public int getTotalHours(){
        int hours = 0;

        for(Task item: items){
            for(TaskLog log: item.getLog()){
                hours += log.getHours();
            }
        }

        return hours;
    }

    public String getSummary(){
        String taskSummary = "";

        int openTask = 0;
        int closeTask = 0;

        int oCriticalTask = 0, oMajorTask = 0, oMinorTask = 0, oTrivialTask = 0;
        int cCriticalTask = 0, cMajorTask = 0, cMinorTask = 0, cTrivialTask = 0;

        for(Task item: items){
            if(item.getStatus() == Task.Status.OPEN){
                openTask++;

                switch(item.getPriority()){
                    case CRITICAL:
                        oCriticalTask++;
                        break;
                    case MAJOR:
                        oMajorTask++;
                        break;
                    case MINOR:
                        oMinorTask++;
                        break;
                    case TRIVIAL:
                        oTrivialTask++;
                        break;
                    default:
                        break;
                }

            }else if(item.getStatus() == Task.Status.CLOSED){
                closeTask++;

                switch(item.getPriority()){
                    case CRITICAL:
                        cCriticalTask++;
                        break;
                    case MAJOR:
                        cMajorTask++;
                        break;
                    case MINOR:
                        cMinorTask++;
                        break;
                    case TRIVIAL:
                        cTrivialTask++;
                        break;
                    default:
                        break;
                }
            }
        }

        taskSummary += "--Close Tasks: " + closeTask + "\n"
                + "----Critical: " + cCriticalTask + "\n"
                + "----Major: " + cMajorTask + "\n"
                + "----Minor: " + cMinorTask + "\n"
                + "----Trivial: " + cTrivialTask + "\n"
        ;

        return taskSummary;
    }


    public void open(Task task){
        items.remove(task);
        MainActivity.closedTasks.remove(task);
        notifyDataSetChanged();

        MainActivity.listAdapter.add(task);
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
            final Task task = items.get(position);

            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout itemLayout = (RelativeLayout)mInflater.inflate(R.layout.task_list_view, parent, false);

            final TextView taskName = (TextView)itemLayout.findViewById(R.id.taskNameLabel);
            taskName.setText(task.getName());

            final TextView projectName = (TextView)itemLayout.findViewById(R.id.projectNameLabel);
            projectName.setText(task.getProject());

            final TextView urgency = (TextView)itemLayout.findViewById(R.id.urgencyLabel);

            if(task.getPriority().equals(com.example.protrack.Task.Priority.TRIVIAL)){
                urgency.setText("Trivial");
                urgency.setTextColor(Color.GREEN);
            } else if(task.getPriority().equals(com.example.protrack.Task.Priority.MINOR)){
                urgency.setText("Minor");
                urgency.setTextColor(Color.YELLOW);
            } else if(task.getPriority().equals(com.example.protrack.Task.Priority.MAJOR)){
                urgency.setText("MAJOR");
                urgency.setTextColor(0xFFF06D2F);
            } else if(task.getPriority().equals(com.example.protrack.Task.Priority.CRITICAL)){
                urgency.setText("CRITICAL!");
                urgency.setTextColor(Color.RED);
            }

            return itemLayout;
        }

}
