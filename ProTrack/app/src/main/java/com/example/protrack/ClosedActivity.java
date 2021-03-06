package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 4/28/2016.
 */
public class ClosedActivity extends Activity {

    private static final int WIPE_TASKS_REQUEST = 10;
    private static final int EXPORT_TASKS_REQUEST = 20;
    private static final int TASK_DETAIL_REQUEST = 30;
    ClosedListAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.closed_list_view);
        listView = (ListView) findViewById(R.id.listViewClosed);

        // Create a new TodoListAdapter for this ListActivity's ListView
        //adapter = new ClosedListAdapter(getApplicationContext());
        //MainActivity.closedAdapter = adapter;

        adapter = MainActivity.closedAdapter;

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true);

        // Inflate footerView for footer_view.xml file
        // Add footerView to ListView
        LinearLayout footerView = (LinearLayout) getLayoutInflater().inflate(R.layout.closed_footer_view, null);
        listView.addFooterView(footerView);

        TextView wipeTasks = (TextView) findViewById(R.id.wipeTasks);

        TextView exportTasks = (TextView) findViewById(R.id.exportTasks);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent data = new Intent(getApplicationContext(), TaskDetail.class);
                Task curr = (Task) adapter.getItem(position);
                data.putExtra("taskName", curr.getName());
                data.putExtra("projectName", curr.getProject());
                data.putExtra("status", curr.getStatus());
                data.putExtra("priority", curr.getPriority());
                data.putExtra("startMonth", curr.getStart().getMonth());
                data.putExtra("startDay", curr.getStart().getDay());
                data.putExtra("startYear", curr.getStart().getYear());
                data.putExtra("endMonth", curr.getEnd().getMonth());
                data.putExtra("endDay", curr.getEnd().getDay());
                data.putExtra("endYear", curr.getEnd().getYear());
                startActivityForResult(data, TASK_DETAIL_REQUEST);

            }
        });


        // Attach Listener to addTaskView
        wipeTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                MainActivity.closedTasks.clear();
            }
        });

        // Attach Listener to addProjectView
        exportTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tabHost.setCurrentTab(3);
                //Intent intentToExportTasks = new Intent(getBaseContext(), ExportActivity.class);
                //startActivityForResult(intentToExportTasks, EXPORT_TASKS_REQUEST);
            }
        });

        // Attach the adapter to this ListActivity's ListView
        listView.setAdapter(adapter);

        for(Task task : MainActivity.closedTasks){
            Log.i("ClosedActivity", "Adding Task to ListView!");
            adapter.add(task);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("ListActivity", "Entered onActivityResult()");

        if(requestCode == WIPE_TASKS_REQUEST){
            switch (resultCode){
                case Activity.RESULT_CANCELED:
                    break;

                case Activity.RESULT_OK:
                    String projectName = data.getStringExtra("Project Name");
                    boolean dupe = false;

                    for(String name : MainActivity.projects){
                        if(projectName.equals(name)){
                            dupe = true;
                            Toast.makeText(getBaseContext(), "You already added this Project!", Toast.LENGTH_LONG).show();
                        }
                    }

                    if(dupe){
                        break;
                    } else {
                        MainActivity.projects.add(projectName);
                        break;
                    }
            }
        } else {
            return;
        }

    }
}
