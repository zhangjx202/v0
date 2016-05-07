package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 4/28/2016.
 */
public class ListActivity extends Activity {

    private static final int ADD_TASK_REQUEST = 10;
    private static final int ADD_PROJECT_REQUEST = 20;
    private static final int TASK_DETAIL_REQUEST = 30;
    public static ListAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);
        listView = (ListView) findViewById(R.id.listView);

        // Create a new TodoListAdapter for this ListActivity's ListView
        adapter = new ListAdapter(getApplicationContext());
        MainActivity.listAdapter = adapter;

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true);

        // Inflate footerView for footer_view.xml file
        // Add footerView to ListView
        LinearLayout footerView = (LinearLayout) getLayoutInflater().inflate(R.layout.footer_view, null);
        listView.addFooterView(footerView);

        TextView addTaskView = (TextView) findViewById(R.id.addNewTask);

        TextView addProjectView = (TextView) findViewById(R.id.addNewProject);

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
        addTaskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForNewTask = new Intent(getBaseContext(), AddTask.class);
                startActivityForResult(intentForNewTask, ADD_TASK_REQUEST);
            }
        });

        // Attach Listener to addProjectView
        addProjectView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForNewProject = new Intent(getBaseContext(), AddProject.class);
                startActivityForResult(intentForNewProject, ADD_PROJECT_REQUEST);
            }
        });

        // Attach the adapter to this ListActivity's ListView
        listView.setAdapter(adapter);



        for(Task task : MainActivity.openTasks){
            Log.i("ListActivity", "Adding Task to ListView!");
            adapter.add(task);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("ListActivity", "Entered onActivityResult()");

        if(requestCode == ADD_PROJECT_REQUEST){
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
        } else if(requestCode == ADD_TASK_REQUEST) {
            switch (resultCode){
                case Activity.RESULT_CANCELED:
                    break;

                case Activity.RESULT_OK:
                    String taskName = data.getStringExtra("taskName");
                    String projectName = data.getStringExtra("projectName");
                    Task.Status status = (Task.Status)data.getSerializableExtra("status");
                    Task.Priority priority = (Task.Priority)data.getSerializableExtra("priority");
                    int startM = data.getIntExtra("startMonth", 1);
                    int startD = data.getIntExtra("startDay", 1);
                    int startY = data.getIntExtra("startYear", 1);
                    int endM = data.getIntExtra("endMonth", 1);
                    int endD = data.getIntExtra("endDay", 1);
                    int endY = data.getIntExtra("endYear", 1);

                    Task task = new Task(taskName, projectName, priority, status, startM, startD,
                            startY, endM, endD, endY);
                    adapter.add(task);
                    break;
            }
        } else if(requestCode == TASK_DETAIL_REQUEST) {
            switch (resultCode){
                case Activity.RESULT_CANCELED:
                    break;

                case Activity.RESULT_OK:
                    break;
            }
        }

    }
}
