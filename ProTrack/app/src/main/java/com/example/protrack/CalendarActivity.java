package com.example.protrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kevin on 4/28/2016.
 */
public class CalendarActivity extends Activity {
    private static final int TASK_DETAIL_REQUEST=10;
    private Date selectedDate = new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        //params.gravity = Gravity.;

        //setContentView(R.layout.activity_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LayoutInflater inflater = (LayoutInflater) getParent().getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.calendar_view);
        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(null);

        LinearLayout main = (LinearLayout) findViewById(R.id.linear);
        main.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        /*for (int i = 0; i<MainActivity.closedTasks.size(); i++){
            View view = inflater.inflate(R.layout.calendar_event_layout, main, false);
            TextView text = (TextView) view.findViewById(R.id.projectName);
            text.setText(MainActivity.closedTasks.get(i).getProject());
            main.addView(view);
        }*/

        //getTasks(selectedDate);

    }

    @Override
    public void onResume(){
        super.onResume();
        //getTasks(selectedDate);
    }

    public void getTasks(Date date){
        selectedDate=date;
        LayoutInflater inflater = (LayoutInflater) getParent().getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout main = (LinearLayout) findViewById(R.id.tasks);
        //main.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        //Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),  date.getMonth() + " " + date.getDay() + " " + date.getYear(), Toast.LENGTH_LONG).show();

        main.removeAllViewsInLayout();

        for (int i = 0; i<MainActivity.closedTasks.size(); i++){

            final Task temp = MainActivity.closedTasks.get(i);

            if(toInt(date) >= temp.getStart().toInt() && toInt(date) <= temp.getEnd().toInt() ) {
                View view = inflater.inflate(R.layout.calendar_event_layout, main, false);
                RelativeLayout box = (RelativeLayout) view.findViewById(R.id.box);
                TextView project = (TextView) view.findViewById(R.id.projectName);
                TextView task = (TextView) view.findViewById(R.id.taskName);
                TextView start = (TextView) view.findViewById(R.id.startTime);
                TextView end = (TextView) view.findViewById(R.id.endTime);
                project.setText(temp.getProject());
                task.setText(temp.getName());
                start.setText(monthIntToString(temp.getStart().getMonth()) + " " + temp.getStart().getDay()+ "-");
                end.setText(monthIntToString(temp.getEnd().getMonth()) + " " + temp.getEnd().getDay());

                main.addView(view);

            }
        }

        for (int i = 0; i<MainActivity.openTasks.size(); i++){

            final Task temp = MainActivity.openTasks.get(i);

            if(toInt(date) >= temp.getStart().toInt() && toInt(date) <= temp.getEnd().toInt() ) {
                View view = inflater.inflate(R.layout.calendar_event_layout, main, false);
                RelativeLayout box = (RelativeLayout) view.findViewById(R.id.box);
                view.findViewById(R.id.box).setBackgroundColor(Color.argb(126, 77, 226, 97));
                TextView project = (TextView) view.findViewById(R.id.projectName);
                TextView task = (TextView) view.findViewById(R.id.taskName);
                TextView start = (TextView) view.findViewById(R.id.startTime);
                TextView end = (TextView) view.findViewById(R.id.endTime);
                project.setText(temp.getProject());
                task.setText(temp.getName());
                start.setText(monthIntToString(temp.getStart().getMonth()) + " " + temp.getStart().getDay()+ "-");
                end.setText(monthIntToString(temp.getEnd().getMonth()) + " " + temp.getEnd().getDay());

                box.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent data = new Intent(getApplicationContext(), TaskDetail.class);
                        data.putExtra("taskName", temp.getName());
                        data.putExtra("projectName", temp.getProject());
                        data.putExtra("status", temp.getStatus());
                        data.putExtra("priority", temp.getPriority());
                        data.putExtra("startMonth", temp.getStart().getMonth());
                        data.putExtra("startDay", temp.getStart().getDay());
                        data.putExtra("startYear", temp.getStart().getYear());
                        data.putExtra("endMonth", temp.getEnd().getMonth());
                        data.putExtra("endDay", temp.getEnd().getDay());
                        data.putExtra("endYear", temp.getEnd().getYear());
                        startActivityForResult(data, TASK_DETAIL_REQUEST);

                    }
                });
                main.addView(view);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == TASK_DETAIL_REQUEST) {
            switch (resultCode){
                case Activity.RESULT_CANCELED:
                    break;

                case Activity.RESULT_OK:
                    break;
            }
        }
    }

    public String monthIntToString(int month){
        if (month==1){
            return "Jan";
        } else if(month==2){
            return "Feb";
        }  else if(month==3){
            return "Mar";
        }  else if(month==4){
            return "Apr";
        }  else if(month==5){
            return "May";
        }  else if(month==6){
            return "Jun";
        }  else if(month==7){
            return "Jul";
        }  else if(month==8){
            return "Aug";
        }  else if(month==9){
            return "Sep";
        }  else if(month==10){
            return "Oct";
        }  else if(month==11){
            return "Nov";
        }  else if(month==12){
            return "Dec";
        } else {
            return "";
        }
    }

    public int toInt(Date date){
        //Get the Day Month and Year of the selected Date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //Toast.makeText(getApplicationContext(),  month + " " + day + " " + year, Toast.LENGTH_LONG).show();
        return year*10000+(month+1)*100+day;
    }



}
