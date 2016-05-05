package com.example.protrack;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Kevin on 4/28/2016.
 */
public class CalendarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setContentView (R.layout.calendar_view);
        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(null);
    }
}
