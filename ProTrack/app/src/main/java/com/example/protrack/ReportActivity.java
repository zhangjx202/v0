package com.example.protrack;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;

/**
 * Created by Jun on 5/7/2016.
 */
public class ReportActivity extends Activity {

    private static final String TAG = "ProTrack-Report";

    public final static int ALL = 0;
    public final static int OPEN = 1;
    public final static int CLOSED = 2;
    private String[] items = new String[]{"All Tasks", "Open Tasks", "Close Tasks"};

    private Spinner spinner;
    private TextView mHours;
    private TextView mOpenSummary;
    private TextView mCloseSummary;
    private TextView mCount;

    private ClosedListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.report_view);

        mOpenSummary = (TextView) findViewById(R.id.summaryOpenText);
        mCloseSummary = (TextView) findViewById(R.id.summaryCloseText);
        mHours = (TextView) findViewById(R.id.hoursText);
        mCount = (TextView) findViewById(R.id.countText);

        if(MainActivity.closedAdapter == null){
            adapter = new ClosedListAdapter(getApplicationContext());
            MainActivity.closedAdapter = adapter;

            for(Task task : MainActivity.closedTasks){
                adapter.add(task);
            }

        }

        spinner = (Spinner)findViewById(R.id.task_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_dropdown_item
                , items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateReport(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        updateReport(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    private void updateReport(int state){
        if(state == ALL){
            mOpenSummary.setText("Open Tasks: " + MainActivity.listAdapter.getCount());
            mCloseSummary.setText("Closed Tasks: " + MainActivity.closedAdapter.getCount());
            mHours.setText("" + (MainActivity.closedAdapter.getTotalHours()
                    + MainActivity.listAdapter.getTotalHours()));
            mCount.setText("" + (MainActivity.listAdapter.getCount()
                    + MainActivity.closedAdapter.getCount()));
        }else if(state == OPEN){
            mOpenSummary.setText("Open Tasks: " + MainActivity.listAdapter.getCount());
            mCloseSummary.setText("");
            mHours.setText("" + MainActivity.listAdapter.getTotalHours());
            mCount.setText("");

        }else if(state == CLOSED){
            mOpenSummary.setText("");
            mCloseSummary.setText("Closed Tasks: " + MainActivity.closedAdapter.getCount());
            mHours.setText("" + MainActivity.closedAdapter.getTotalHours());
            mCount.setText("");
        }


        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();

        //find max of data
        int[] data = new int[8];
        data[0] = getData((ArrayList<Task>)MainActivity.listAdapter.items
                , Task.Priority.CRITICAL);
        data[1] = getData((ArrayList<Task>)MainActivity.listAdapter.items
                , Task.Priority.MAJOR);
        data[2] = getData((ArrayList<Task>)MainActivity.listAdapter.items
                , Task.Priority.MINOR);
        data[3] = getData((ArrayList<Task>)MainActivity.listAdapter.items
                , Task.Priority.TRIVIAL);
        data[4] = getData((ArrayList<Task>)MainActivity.closedAdapter.items
                , Task.Priority.CRITICAL);
        data[5] = getData((ArrayList<Task>)MainActivity.closedAdapter.items
                , Task.Priority.MAJOR);
        data[6] = getData((ArrayList<Task>)MainActivity.closedAdapter.items
                , Task.Priority.MINOR);
        data[7] = getData((ArrayList<Task>)MainActivity.closedAdapter.items
                , Task.Priority.TRIVIAL);

        int max = data[0];
        for(int d: data){
            max = Math.max(max, d);
        }

        //first graph
        BarGraphSeries<DataPoint> series1 =
                new BarGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, data[0]),
                        new DataPoint(1, data[1]),
                        new DataPoint(2, data[2]),
                        new DataPoint(3, data[3])
                });

        //second graph
        BarGraphSeries<DataPoint> series2 =
                new BarGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, data[4]),
                        new DataPoint(1, data[5]),
                        new DataPoint(2, data[6]),
                        new DataPoint(3, data[7]),
                });

        //set line color
        series1.setColor(Color.RED);
        series2.setColor(Color.GREEN);

        //set tap behavior
        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ReportActivity.this, "Open Remain: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ReportActivity.this, "Close Completed: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        //draw points
        //series2.setDrawDataPoints(true);
        //series2.setDataPointsRadius(10);

        //fill background
        //series2.setDrawBackground(true);
        //series2.setBackgroundColor(Color.GREEN);
        //line color


        // legend
        series1.setTitle("Open Tasks");
        series2.setTitle("Closed Tasks");
        graph.getLegendRenderer().setVisible(true);
        //line_graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setFixedPosition(0, 0);

        // bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(max * 1.5);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-0.25);
        graph.getViewport().setMaxX(3.25);

        //label
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Critical", "Major", "Minor", "Trivial"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        //staticLabelsFormatter.setVerticalLabels(new String[]{"Jan", "Feb", "March"});

        //set spacing
        series1.setSpacing(50);
        series2.setSpacing(50);

        //show value
        series1.setDrawValuesOnTop(true);
        series2.setDrawValuesOnTop(true);
        series1.setValuesOnTopColor(Color.RED);
        series2.setValuesOnTopColor(Color.GREEN);

        //background color
        graph.getViewport().setBackgroundColor(Color.WHITE);

        //add to graph
        graph.addSeries(series1);
        graph.addSeries(series2);
    }

    private int getData(ArrayList<Task> items, Task.Priority priority){
        int data = 0;
        for(Task item: items){
            if(item.getPriority() == priority){
                data++;
            }
        }
        return data;
    }
}
