package com.example.protrack;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends TabActivity {

    public static ArrayList<String> projects;
    public static TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Creating the TabHost that will contain the app's tabs
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

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

        TabHost.TabSpec exportTab = tabHost.newTabSpec("Export View");
        exportTab.setIndicator("Export");
        exportTab.setContent(new Intent(this, ExportActivity.class));
        tabHost.addTab(exportTab);

        tabHost.setCurrentTab(2);

        projects = new ArrayList<String>();


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
