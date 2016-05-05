package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
    ListAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);
        listView = (ListView) findViewById(R.id.listView);

        // Create a new TodoListAdapter for this ListActivity's ListView
        adapter = new ListAdapter(getApplicationContext());

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true);

        // Inflate footerView for footer_view.xml file
        // Add footerView to ListView
        LinearLayout footerView = (LinearLayout) getLayoutInflater().inflate(R.layout.footer_view, null);
        listView.addFooterView(footerView);

        TextView addTaskView = (TextView) findViewById(R.id.addNewTask);

        TextView addProjectView = (TextView) findViewById(R.id.addNewProject);


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
        } else {
            return;
        }

    }
}
