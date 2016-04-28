package edu.umd.jun.protrack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashSet;

public class MainActivity extends ActionBarActivity {
    //CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        MyCalendarView cv = ((MyCalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(null);

       /* calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });*/
    }
}
