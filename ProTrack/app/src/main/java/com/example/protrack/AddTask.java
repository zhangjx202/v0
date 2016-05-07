package com.example.protrack;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Kevin on 4/27/2016.
 */
public class AddTask extends Activity {

    Spinner projectSpinner;
    Spinner statusSpinner;
    Spinner prioritySpinner;
    EditText taskName;
    static TextView startDateText;
    static TextView endDateText;
    static String startDateString;
    static String endDateString;

    static int startMonth;
    static int startDay;
    static int startYear;

    static int endMonth;
    static int endDay;
    static int endYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        taskName = (EditText) findViewById(R.id.taskName);

        projectSpinner = (Spinner)findViewById(R.id.projectGroup);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MainActivity.projects); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectSpinner.setAdapter(spinnerArrayAdapter);

        statusSpinner = (Spinner) findViewById(R.id.statusGroup);
        prioritySpinner = (Spinner) findViewById(R.id.priorityGroup);

        startDateText = (TextView) findViewById(R.id.startDateText);
        endDateText = (TextView) findViewById(R.id.endDateText);

        // Set up OnClickListener for the Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // Set up OnClickListener for the Reset Button
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName.setText("");
                projectSpinner.setSelection(0);
                statusSpinner.setSelection(0);
                prioritySpinner.setSelection(0);
                startDateText.setText("MM - DD - YYYY");
                endDateText.setText("MM - DD - YYYY");
            }
        });

        // Set up OnClickListener for the Submit Button
        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName1 = taskName.getText().toString();
                String projectName1 = MainActivity.projects.get(projectSpinner.getSelectedItemPosition());

                Task.Status status = null;
                int statusInt = statusSpinner.getSelectedItemPosition();
                if(statusInt == 0){
                    status = Task.Status.OPEN;
                } else {
                    status = Task.Status.CLOSED;
                }

                Task.Priority priority = null;
                int priorityInt = prioritySpinner.getSelectedItemPosition();
                if(priorityInt == 0){
                    priority = Task.Priority.TRIVIAL;
                } else if(priorityInt == 1){
                    priority = Task.Priority.MINOR;
                } else if(priorityInt == 2){
                    priority = Task.Priority.MAJOR;
                } else {
                    priority = Task.Priority.CRITICAL;
                }


                Intent data = new Intent();
                data.putExtra("taskName", taskName1);
                data.putExtra("projectName", projectName1);
                data.putExtra("status", status);
                data.putExtra("priority", priority);
                data.putExtra("startMonth", startMonth);
                data.putExtra("startDay", startDay);
                data.putExtra("startYear", startYear);
                data.putExtra("endMonth", endMonth);
                data.putExtra("endDay", endDay);
                data.putExtra("endYear", endYear);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        final Button startDateButton = (Button) findViewById(R.id.startDateButton);
        startDateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialogStart();
            }
        });

        final Button endDateButton = (Button) findViewById(R.id.endDateButton);
        endDateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialogEnd();
            }
        });
    }

    private void showDatePickerDialogStart() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showDatePickerDialogEnd() {
        DialogFragment newFragment = new DatePickerFragmentEnd();
        newFragment.show(getFragmentManager(), "datePickerEnd");
    }

    private static void setStartDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        startDateString = mon + " - " + day + " - " + year;

        startMonth = monthOfYear;
        startDay = dayOfMonth;
        startYear = year;
    }

    private static void setEndDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        endDateString = mon + " - " + day + " - " + year;

        endMonth = monthOfYear;
        endDay = dayOfMonth;
        endYear = year;
    }



    // DialogFragment used to pick an AddTask start date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setStartDateString(year, monthOfYear, dayOfMonth);

            startDateText.setText(startDateString);
        }

    }


    // DialogFragment used to pick an AddTask start date

    public static class DatePickerFragmentEnd extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setEndDateString(year, monthOfYear, dayOfMonth);

            endDateText.setText(endDateString);
        }

    }
}
