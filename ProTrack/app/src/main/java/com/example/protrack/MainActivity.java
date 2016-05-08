package com.example.protrack;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

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

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.contains("com.example.ProTrack.PROJECTS")){
            Gson gson = new Gson();
            String json = sharedPref.getString("com.example.ProTrack.PROJECTS", "");
            projects = gson.fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
        }

        if(MainActivity.sharedPref.contains("com.example.ProTrack.OPENTASKS")){
            Gson gson = new Gson();
            String json = MainActivity.sharedPref.getString("com.example.ProTrack.OPENTASKS", "");
            openTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {}.getType());
        }

        if(MainActivity.sharedPref.contains("com.example.ProTrack.CLOSEDTASKS")){
            Gson gson = new Gson();
            String json = MainActivity.sharedPref.getString("com.example.ProTrack.CLOSEDTASKS", "");
            closedTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {}.getType());
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

}

    @Override
    protected void onPause() {
        super.onPause();

        // Save
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(projects);
        editor.putString("com.example.ProTrack.PROJECTS", json);


        json = gson.toJson(openTasks);
        editor.putString("com.example.ProTrack.OPENTASKS", json);

        json = gson.toJson(closedTasks);
        editor.putString("com.example.ProTrack.CLOSEDTASKS", json);


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
