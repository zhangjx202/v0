package edu.umd.jun.protrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jun on 4/27/2016.
 */
public class Transfer extends Activity {
    private static final String TAG = "ProTrack-Transfer";

    public final static String IMPORT = "IMPORT";

    private EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        mName = (EditText) findViewById(R.id.edtName);

        final Button cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED);

                finish();
            }
        });

        final Button importButton = (Button) findViewById(R.id.btnIm);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Package ToDoItem data into an Intent
                load(mName.getText().toString());
                setResult(RESULT_OK);

                Intent data = new Intent();
                data.putExtra(IMPORT, data);

                finish();
            }
        });

        final Button exportButton = (Button) findViewById(R.id.btnEx);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dump(mName.getText().toString())) {
                    setResult(RESULT_OK);
                    finish();
                }else{
                    setResult(RESULT_CANCELED);
                }
            }
        });
    }

    private boolean load(String name){
        return true;
    }

    private boolean dump(String name){

        return true;
    }
}
