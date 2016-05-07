package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Kevin on 5/7/2016.
 */
public class AddLog extends Activity {

    EditText overviewEdit;
    EditText commentsEdit;
    String overview;
    String comments;
    Spinner hoursSpinner;
    int hours;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_log);

        overviewEdit = (EditText) findViewById(R.id.overviewName);

        commentsEdit = (EditText) findViewById(R.id.commentsVal);

        hoursSpinner = (Spinner) findViewById(R.id.numHours);

        // Set up OnClickListener for the Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.cancelButtonDetail);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // Set up OnClickListener for the Reset Button
        final Button resetButton = (Button) findViewById(R.id.resetButtonDetail);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overviewEdit.setText("");
                commentsEdit.setText("");
                hoursSpinner.setSelection(0);
            }
        });

        // Set up OnClickListener for the Submit Button
        final Button submitButton = (Button) findViewById(R.id.submitButtonDetail);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overview = overviewEdit.getText().toString();
                comments = commentsEdit.getText().toString();
                hours = hoursSpinner.getSelectedItemPosition() + 1;

                Intent data = new Intent();
                data.putExtra("overview", overview);
                data.putExtra("comments", comments);
                data.putExtra("hours", hours);

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
