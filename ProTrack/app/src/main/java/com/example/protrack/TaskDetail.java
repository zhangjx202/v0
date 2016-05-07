package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Kevin on 5/6/2016.
 */
public class TaskDetail extends Activity {

    private static final int ADD_LOG_REQUEST = 10;
    private static final int LOG_DETAIL_REQUEST = 20;
    DetailAdapter adapter;
    ListView listView;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.task_detail);

        adapter = new DetailAdapter(getApplicationContext());
        MainActivity.detailAdapter = adapter;

        // TODO - implement the Activity
        Intent data = getIntent();
        final String taskName = data.getStringExtra("taskName");
        String projectName = data.getStringExtra("projectName");
        Task.Status status = (Task.Status)data.getSerializableExtra("status");
        Task.Priority priority = (Task.Priority)data.getSerializableExtra("priority");
        int startM = data.getIntExtra("startMonth", 1);
        int startD = data.getIntExtra("startDay", 1);
        int startY = data.getIntExtra("startYear", 1);
        int endM = data.getIntExtra("endMonth", 1);
        int endD = data.getIntExtra("endDay", 1);
        int endY = data.getIntExtra("endYear", 1);

        for(Task task1 : MainActivity.openTasks){
            if(task1.getName().equals(taskName)){
                task = task1;
                break;
            }
        }

        TextView taskNameView = (TextView) findViewById(R.id.taskNameDetail);
        taskNameView.setText(taskName);

        TextView projectNameView = (TextView) findViewById(R.id.projectNameDetail);
        projectNameView.setText(projectName);

        TextView startDate = (TextView) findViewById(R.id.startDateDetail);
        startDate.setText(startM + " - " + startD + " - " + startY);

        TextView endDate = (TextView) findViewById(R.id.endDateDetail);
        endDate.setText(endM + " - " + endD + " - " + endY);

        TextView urgency = (TextView) findViewById(R.id.priorityDetail);
        if(priority.equals(com.example.protrack.Task.Priority.TRIVIAL)){
            urgency.setText("Trivial");
            urgency.setTextColor(Color.GREEN);
        } else if(priority.equals(com.example.protrack.Task.Priority.MINOR)) {
            urgency.setText("Minor");
            urgency.setTextColor(Color.YELLOW);
        } else if(priority.equals(com.example.protrack.Task.Priority.MAJOR)){
            urgency.setText("MAJOR");
            urgency.setTextColor(0xFFF06D2F);
        } else if(priority.equals(com.example.protrack.Task.Priority.CRITICAL)){
            urgency.setText("CRITICAL!");
            urgency.setTextColor(Color.RED);
        }

        listView = (ListView) findViewById(R.id.listViewDetail);

        // Inflate footerView for footer_view.xml file
        // Add footerView to ListView
        LinearLayout footerView = (LinearLayout) getLayoutInflater().inflate(R.layout.footer_view_detail, null);
        listView.addFooterView(footerView);

        // Attach Listener to addTaskView
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForNewTask = new Intent(getBaseContext(), AddLog.class);
                intentForNewTask.putExtra("taskName", taskName);
                startActivityForResult(intentForNewTask, ADD_LOG_REQUEST);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent data = new Intent(getApplicationContext(), LogDetail.class);
                TaskLog log = (TaskLog)adapter.getItem(position);
                data.putExtra("overview", log.overview);
                data.putExtra("comments", log.comments);
                data.putExtra("hours", log.hours);
                startActivityForResult(data, LOG_DETAIL_REQUEST);

            }
        });

        // Set up OnClickListener for the Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.cancelButtonDetail);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        TextView closeView = (TextView) findViewById(R.id.statusDetail);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.listAdapter.close(task);
                setResult(RESULT_OK);
                finish();
            }
        });

        // Attach the adapter to this ListActivity's ListView
        listView.setAdapter(adapter);

        Log.i("TaskDetail", "Num of Logs: " + task.taskLog.size());

        for(TaskLog log : task.taskLog){
            adapter.add(log);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("ListActivity", "Entered onActivityResult()");

        if(requestCode == ADD_LOG_REQUEST){
            switch (resultCode){
                case Activity.RESULT_CANCELED:
                    break;

                case Activity.RESULT_OK:
                    String overview = data.getStringExtra("overview");
                    String comments = data.getStringExtra("comments");
                    int hours = data.getIntExtra("hours", 1);

                    Log.i("TaskDetail", "Overview: " + overview);
                    Log.i("TaskDetail", "Comments: " + comments);
                    Log.i("TaskDetail", "Hours: " + hours);

                    TaskLog log = new TaskLog(hours, overview, comments);
                    task.taskLog.add(log);
                    adapter.add(log);

            }
        } else {

        }

    }

}
