package com.example.protrack;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;

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
    private TextView mCommon;
    private TextView mCount;

    private ClosedListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.report_view);

        mHours = (TextView) findViewById(R.id.hoursText);
        mCount = (TextView) findViewById(R.id.countText);
        mCommon = (TextView) findViewById(R.id.commonText);

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
        spinner.setSelection(0);
        updateReport(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    private void updateReport(int state){

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
                Toast.makeText(ReportActivity.this, "" + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ReportActivity.this, "" + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        //draw points
        //series2.setDrawDataPoints(true);
        //series2.setDataPointsRadius(10);

        //fill background
        //series2.setDrawBackground(true);
        //series2.setBackgroundColor(Color.GREEN);
        //line color


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

        //top words use in overview and comments
        HashMap<String, Integer> allWords = new HashMap<String, Integer>();
        HashMap<String, Integer> openWords = new HashMap<String, Integer>();
        HashMap<String, Integer> closedWords = new HashMap<String, Integer>();

        for(Task item: MainActivity.listAdapter.items){
            for(TaskLog log: item.taskLog){
                for(String s: log.overview.split(" ")){
                    if(allWords.containsKey(s)){
                        allWords.put(s, allWords.get(s).intValue() + 1);
                        openWords.put(s, openWords.get(s).intValue() + 1);
                    }else{
                        allWords.put(s, 1);
                        openWords.put(s, 1);
                        closedWords.put(s, 0);
                    }
                }
                for(String s: log.comments.split(" ")){
                    if(allWords.containsKey(s)){
                        allWords.put(s, allWords.get(s).intValue() + 1);
                        openWords.put(s, openWords.get(s).intValue() + 1);
                    }else{
                        allWords.put(s, 1);
                        openWords.put(s, 1);
                        closedWords.put(s, 0);
                    }
                }
            }
        }

        for(Task item: MainActivity.closedAdapter.items){
            for(TaskLog log: item.taskLog){
                for(String s: log.overview.split(" ")){
                    if(allWords.containsKey(s)){
                        allWords.put(s, allWords.get(s).intValue() + 1);
                        closedWords.put(s, closedWords.get(s).intValue() + 1);
                    }else{
                        allWords.put(s, 1);
                        closedWords.put(s, 1);
                        openWords.put(s, 0);
                    }
                }
                for(String s: log.comments.split(" ")){
                    if(allWords.containsKey(s)){
                        allWords.put(s, allWords.get(s).intValue() + 1);
                        closedWords.put(s, closedWords.get(s).intValue() + 1);
                    }else{
                        allWords.put(s, 1);
                        closedWords.put(s, 1);
                        openWords.put(s, 0);
                    }
                }
            }
        }



        //add to graph and set messages
        if(state == ALL){
            mHours.setText("" + (MainActivity.closedAdapter.getTotalHours()
                    + MainActivity.listAdapter.getTotalHours()));
            mCount.setText("Total Tasks: "
                    + (MainActivity.listAdapter.getCount()
                    + MainActivity.closedAdapter.getCount()));

            //Figure out the top words
            ArrayList<String> words = new ArrayList<String>();
            ArrayList<Integer> counts = new ArrayList<Integer>();

            while(words.size() < 10){
                String word = "";
                Integer count = 0;

                Log.i("Size of Map", ""+allWords.size());

                for(String s: allWords.keySet()){
                    if(allWords.get(s).intValue() > count.intValue()){
                        word = s;
                        count = allWords.get(s);
                    }
                }

                words.add(word);
                counts.add(count);
                allWords.remove(word);
            }

            String msg = "";
            for(int i = 0; i < 10; i++){
                msg += words.get(i) + " = " + counts.get(i).toString() + "\n";
            }
            mCommon.setText(msg);

            // legend
            series1.setTitle("Open Tasks");
            series2.setTitle("Closed Tasks");
            graph.getLegendRenderer().setVisible(true);
            //line_graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            graph.getLegendRenderer().setFixedPosition(0, 0);

            graph.addSeries(series1);
            graph.addSeries(series2);
        }else if(state == OPEN){
            mHours.setText("" + MainActivity.listAdapter.getTotalHours());
            mCount.setText("Open Tasks: " + MainActivity.listAdapter.getCount());

            //Figure out the top words
            ArrayList<String> words = new ArrayList<String>();
            ArrayList<Integer> counts = new ArrayList<Integer>();

            while(words.size() < 10){
                String word = "";
                Integer count = 0;

                for(String s: openWords.keySet()){
                    if(openWords.get(s).intValue() > count.intValue()){
                        word = s;
                        count = openWords.get(s);
                    }
                }

                words.add(word);
                counts.add(count);
                openWords.remove(word);
            }

            String msg = "";
            for(int i = 0; i < 10; i++){
                msg += words.get(i) + " = " + counts.get(i).toString() + "\n";
            }
            mCommon.setText(msg);

            graph.getLegendRenderer().setVisible(false);

            graph.addSeries(series1);

        }else if(state == CLOSED){
            mHours.setText("" + MainActivity.closedAdapter.getTotalHours());
            mCount.setText("Closed Tasks: " + MainActivity.closedAdapter.getCount());

            //Figure out the top words
            ArrayList<String> words = new ArrayList<String>();
            ArrayList<Integer> counts = new ArrayList<Integer>();

            while(words.size() < 10){
                String word = "";
                Integer count = 0;

                for(String s: closedWords.keySet()){
                    if(closedWords.get(s).intValue() > count.intValue()){
                        word = s;
                        count = closedWords.get(s);
                    }
                }

                words.add(word);
                counts.add(count);
                closedWords.remove(word);
            }

            String msg = "";
            for(int i = 0; i < 10; i++){
                msg += words.get(i) + " = " + counts.get(i).toString() + "\n";
            }
            mCommon.setText(msg);

            graph.getLegendRenderer().setVisible(false);

            graph.addSeries(series2);
        }

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
