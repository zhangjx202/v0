package edu.umd.jun.protrack;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Matt on 4/27/2016.
 */
public class MyCalendarView extends LinearLayout {
    private LinearLayout header;
    private ImageView previousButton;
    private ImageView nextButton;
    private TextView textDate;
    private GridView grid;

    private static final int DAYS_COUNT = 42;

    private static final String DATE_FORMAT = "MMMM yyyy";

    private Calendar currentDate = Calendar.getInstance();



    public MyCalendarView(Context context){
        super(context);
        init(context);
    }

    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.calendar_layout, this);
        header = (LinearLayout) findViewById(R.id.calendar_header);
        previousButton = (ImageView) findViewById(R.id.calendar_prev_button);
        nextButton = (ImageView) findViewById(R.id.calendar_next_button);
        textDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);

        nextButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentDate.add(Calendar.MONTH, 1);
                        updateCalendar();
                    }
                }
        );


        previousButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentDate.add(Calendar.MONTH, -1);
                        updateCalendar();
                    }
                }
        );

        grid.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> view, View cell, int position, long id) {
                        for(int i=0; i<view.getChildCount();i++){
                            view.getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                        cell.findViewById(R.id.day).setBackgroundColor(Color.parseColor("#E1E1E1"));
                    }
                }
        );
    }

    public void updateCalendar(){
        updateCalendar(null);
    }

    public void updateCalendar(HashSet<Date> events){
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events, currentDate));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        textDate.setText(sdf.format(currentDate.getTime()));
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashSet<Date> eventDays;
        private Date present;
        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, Calendar current) {
            super(context, R.layout.calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
            present = current.getTime();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.calendar_day, parent, false);

            // if this day has an event, specify event image
            view.setBackgroundResource(0);
            if (eventDays != null)
            {
                for (Date eventDate : eventDays)
                {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year)
                    {
                        // mark this day for event
                        view.setBackgroundResource(R.drawable.task_indicator);
                        break;
                    }
                }
            }

            // clear styling
            ((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView)view).setTextColor(Color.BLACK);

            if (month != present.getMonth() || year != present.getYear())
            {
                // if this day is outside current month, grey it out
                ((TextView)view).setTextColor(Color.GRAY);
            }
            else if (day == today.getDate() && month == today.getMonth() && year==today.getYear())
            {
                // if it is today, set it to blue/bold
                ((TextView)view).setTypeface(null, Typeface.BOLD);
                ((TextView)view).setTextColor(Color.BLUE);
            }

            // set text
            ((TextView)view).setText(String.valueOf(date.getDate()));

            return view;
        }


    }

}
