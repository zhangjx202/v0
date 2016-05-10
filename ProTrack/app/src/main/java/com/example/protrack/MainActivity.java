package com.example.protrack;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends TabActivity {

    public static ArrayList<String> projects;
    public static ArrayList<Task> openTasks;
    public static ArrayList<Task> closedTasks;
    public static TabHost tabHost;
    public static SharedPreferences sharedPref;
    public static ListAdapter listAdapter;
    public static DetailAdapter detailAdapter;
    public static ClosedListAdapter closedAdapter;


    //Keys for saving application state
    final String WORK_PROJECTS = "com.example.ProTrack.WORK_PROJECTS";
    final String WORK_OPEN = "com.example.ProTrack.WORK_OPENTASKS";
    final String WORK_CLOSED = "com.example.ProTrack.WORK_CLOSEDTASKS";

    final String HOME_PROJECTS = "com.example.ProTrack.HOME_PROJECTS";
    final String HOME_OPEN = "com.example.ProTrack.HOME_OPENTASKS";
    final String HOME_CLOSED = "com.example.ProTrack.HOME_CLOSEDTASKS";

    final String LAST_WORKSPACE = "com.example.ProTrack.LAST_WORKSPACE";

    String CURR_WORKSPACE = "Work";

    boolean saveOther = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Creating the TabHost that will contain the app's tabs
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        projects = new ArrayList<String>();
        openTasks = new ArrayList<Task>();
        closedTasks = new ArrayList<Task>();

        listAdapter = new ListAdapter(getApplicationContext());
        detailAdapter = new DetailAdapter(getApplicationContext());
        closedAdapter = new ClosedListAdapter(getApplicationContext());

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        TextView workspace = (TextView) findViewById(R.id.workspace);

        if(sharedPref.contains(LAST_WORKSPACE)){
            String lastWork = sharedPref.getString(LAST_WORKSPACE, "Work");

            if(lastWork.equals("Work")){
                Log.i("MainActivity", "Last Workspace = Work");
                CURR_WORKSPACE = "Work";
                workspace.setText("Work");
                if(sharedPref.contains(WORK_PROJECTS)){
                    Gson gson = new Gson();
                    String json = sharedPref.getString(WORK_PROJECTS, "");
                    projects = gson.fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
                }

                if(MainActivity.sharedPref.contains(WORK_OPEN)){
                    Gson gson = new Gson();
                    String json = MainActivity.sharedPref.getString(WORK_OPEN, "");
                    openTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {}.getType());
                }

                if(MainActivity.sharedPref.contains(WORK_CLOSED)){
                    Gson gson = new Gson();
                    String json = MainActivity.sharedPref.getString(WORK_CLOSED, "");
                    closedTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {}.getType());
                }
            } else if(lastWork.equals("Home")){
                Log.i("MainActivity", "Last Workspace = Home");
                CURR_WORKSPACE = "Home";
                workspace.setText("Home");
                if(sharedPref.contains(HOME_PROJECTS)){
                    Gson gson = new Gson();
                    String json = sharedPref.getString(HOME_PROJECTS, "");
                    projects = gson.fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
                }

                if(MainActivity.sharedPref.contains(HOME_OPEN)){
                    Gson gson = new Gson();
                    String json = MainActivity.sharedPref.getString(HOME_OPEN, "");
                    openTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {}.getType());
                }

                if(MainActivity.sharedPref.contains(HOME_CLOSED)){
                    Gson gson = new Gson();
                    String json = MainActivity.sharedPref.getString(HOME_CLOSED, "");
                    closedTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {}.getType());
                }
            }
        } else {
            CURR_WORKSPACE = "Work";
        }


        Log.i("MainActivity", "AllTasks Size: " + openTasks.size() + "");

        TabHost.TabSpec listTab = tabHost.newTabSpec("Ongoing");
        listTab.setIndicator("Ongoing");
        listTab.setContent(new Intent(this, ListActivity.class));
        tabHost.addTab(listTab);

        TabHost.TabSpec closedTab = tabHost.newTabSpec("Closed");
        closedTab.setIndicator("Closed");
        closedTab.setContent(new Intent(this, ClosedActivity.class));
        tabHost.addTab(closedTab);

        TabHost.TabSpec calendarTab = tabHost.newTabSpec("Calendar View");
        calendarTab.setIndicator("Calendar");
        calendarTab.setContent(new Intent(this, CalendarActivity.class));
        tabHost.addTab(calendarTab);

        TabHost.TabSpec reportTab = tabHost.newTabSpec("Report View");
        reportTab.setIndicator("Report");
        Intent reportIntent = new Intent(this, ReportActivity.class);
        reportTab.setContent(reportIntent);
        tabHost.addTab(reportTab);

        tabHost.setCurrentTab(2);

        for(int x = 0; x < tabHost.getTabWidget().getChildCount(); x++){
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(x).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
        }

        workspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String curr = CURR_WORKSPACE;

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        if(CURR_WORKSPACE.equals("Home")){
                            alert.setTitle("Switch to Work");
                            alert.setMessage("Are you sure you want to switch to your Work workspace?");
                            CURR_WORKSPACE = "Work";
                        } else {
                            alert.setTitle("Switch to Home");
                            alert.setMessage("Are you sure you want to switch to your Home workspace?");
                            CURR_WORKSPACE = "Home";
                        }

                alert.setPositiveButton("Switch", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Gson gson = new Gson();

                        editor.putString(LAST_WORKSPACE, CURR_WORKSPACE);
                        editor.commit();

                        Log.i("MainActivity", "To Switch = " + CURR_WORKSPACE);

                        saveOther = true;

                        Intent restartIntent = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restartIntent);
                    }
                });
                alert.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CURR_WORKSPACE = curr;
                    }
                });
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        editor.putString(LAST_WORKSPACE, CURR_WORKSPACE);

        if(saveOther == true){
            if(CURR_WORKSPACE.equals("Work")){
                String json = gson.toJson(projects);
                editor.putString(HOME_PROJECTS, json);


                json = gson.toJson(openTasks);
                editor.putString(HOME_OPEN, json);

                json = gson.toJson(closedTasks);
                editor.putString(HOME_CLOSED, json);
            } else {
                String json = gson.toJson(projects);
                editor.putString(WORK_PROJECTS, json);


                json = gson.toJson(openTasks);
                editor.putString(WORK_OPEN, json);

                json = gson.toJson(closedTasks);
                editor.putString(WORK_CLOSED, json);
            }
        } else {
            if(CURR_WORKSPACE.equals("Work")){
                String json = gson.toJson(projects);
                editor.putString(WORK_PROJECTS, json);


                json = gson.toJson(openTasks);
                editor.putString(WORK_OPEN, json);

                json = gson.toJson(closedTasks);
                editor.putString(WORK_CLOSED, json);
            } else {
                String json = gson.toJson(projects);
                editor.putString(HOME_PROJECTS, json);


                json = gson.toJson(openTasks);
                editor.putString(HOME_OPEN, json);

                json = gson.toJson(closedTasks);
                editor.putString(HOME_CLOSED, json);
            }
        }


        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
