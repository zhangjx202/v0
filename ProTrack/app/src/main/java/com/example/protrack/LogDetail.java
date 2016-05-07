package com.example.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kevin on 5/7/2016.
 */
public class LogDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_detail);

        // TODO - implement the Activity
        Intent data = getIntent();
        final String overview = data.getStringExtra("overview");
        final String comments = data.getStringExtra("comments");
        final int hours = data.getIntExtra("hours", 1);

        TextView overviewView = (TextView) findViewById(R.id.overviewName2);
        overviewView.setText(overview);

        TextView commentsView = (TextView) findViewById(R.id.commentsVal2);
        commentsView.setText(comments);

        TextView hoursView = (TextView) findViewById(R.id.numHours2);
        hoursView.setText(hours + "");

        // Set up OnClickListener for the Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.cancelButtonDetail2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
