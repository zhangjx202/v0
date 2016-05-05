package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kevin on 4/27/2016.
 */
public class AddProject extends Activity {

    private EditText projectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project);

        projectText = (EditText) findViewById(R.id.projectName);

        // Set up OnClickListener for the Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // Set up OnClickListener for the Reset Button
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                projectText.setText("");
            }
        });

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectName = projectText.getText().toString();

                // Package ToDoItem data into an Intent
                Intent data = new Intent();
                data.putExtra("Project Name", projectName);

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }


}
